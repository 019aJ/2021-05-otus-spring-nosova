package ru.otus.emergencymonitoringsystem.repositories;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@AllArgsConstructor
public class EmergencyMonitoringRepositoryCustomImpl implements EmergencyMonitoringRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public EmergencyMonitoring findByIdWithLastPrediction(Long id) {
        Aggregation aggregation = newAggregation(addFields().
                addField("m_id").withValue(ConvertOperators.ToString.toString("$_id")).build()
        );
        aggregation.getPipeline().add(lookup("ContaminationAreas", "m_id", "emergency_monitoring_id", "contamination_area"))
                .add(unwind("contamination_area"))
                .add(sort(Sort.Direction.DESC, "contamination_area.create_date"))
                .add(limit(1));
        AggregationResults<EmergencyMonitoring> result = mongoTemplate.
                aggregate(aggregation, EmergencyMonitoring.class, EmergencyMonitoring.class);
        return result.getMappedResults().get(0);
    }

    @Override
    public List<EmergencyMonitoring> activeWithLastPrediction() {
        Aggregation aggregation = newAggregation(addFields().
                addField("m_id").withValue(ConvertOperators.ToString.toString("$_id")).build()
        );

        aggregation.getPipeline()
                .add(match(Criteria.where("is_active").is(true)))
                .add(lookup("ContaminationAreas", "m_id", "emergency_monitoring_id", "contamination_area"))
                .add(addFields().addField("maxAreasDate").withValue(AccumulatorOperators.Max.maxOf("$contamination_area.create_date")).build())
                .add(unwind("contamination_area"))
                .add(addFields().addField("areaDate").withValue("$contamination_area.create_date").build())
                .add(matchExprOperation());
        AggregationResults<EmergencyMonitoring> result = mongoTemplate.
                aggregate(aggregation, EmergencyMonitoring.class, EmergencyMonitoring.class);
        //Тут так, потому что getMappedResults возвращает unmodifiableCollection, а для работы acl надо modifiable
        return new ArrayList<>(result.getMappedResults());
    }

    public FieldsExposingAggregationOperation matchExprOperation() {
        return
                new FieldsExposingAggregationOperation() {

                    @Override
                    public Document toDocument(AggregationOperationContext context) {
                        return new Document("$match",
                                new Document("$expr",
                                        new Document("$eq", Arrays.asList("$areaDate", "$maxAreasDate"))));
                    }

                    @Override
                    public ExposedFields getFields() {
                        Field f = Fields.field("newField");
                        return ExposedFields.synthetic(Fields.from(f));
                    }

                    @Override
                    public boolean inheritsFields() {
                        return true;
                    }
                };
    }

}
