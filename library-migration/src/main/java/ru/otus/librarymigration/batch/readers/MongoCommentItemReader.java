package ru.otus.librarymigration.batch.readers;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.util.json.ParameterBindingDocumentCodec;
import org.springframework.data.mongodb.util.json.ParameterBindingJsonReader;
import org.springframework.lang.Nullable;
import ru.otus.librarymigration.model.nosql.MongoComment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Setter
@Getter
public class MongoCommentItemReader extends AbstractItemCountingItemStreamItemReader<MongoComment> implements InitializingBean {
    private MongoOperations template;
    private final Object lock = new Object();
    protected Iterator<MongoComment> results;

    public MongoCommentItemReader(MongoOperations template) {
        setName("mongoBookItemReader");
        this.template = template;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    private String replacePlaceholders(String input, List<Object> values) {
        ParameterBindingJsonReader reader = new ParameterBindingJsonReader(input, values.toArray());
        DecoderContext decoderContext = DecoderContext.builder().build();
        Document document = (new ParameterBindingDocumentCodec()).decode(reader, decoderContext);
        return document.toJson();
    }

    @Override
    protected void doOpen() throws Exception {
    }

    @Override
    protected void doClose() throws Exception {
    }

    @Override
    @Nullable
    protected MongoComment doRead() throws Exception {
        synchronized (this.lock) {
            if (this.results == null) {
                this.results = this.doReadAll();
                if (this.results == null || !this.results.hasNext()) {
                    return null;
                }
            }

            if (this.results.hasNext()) {
                return this.results.next();
            } else {
                return null;
            }
        }
    }

    protected Iterator<MongoComment> doReadAll() {
        Aggregation aggregation = newAggregation(addFields().
                addField("b_id").withValue(ConvertOperators.ToObjectId.toObjectId("$bookId")).build()
        );
        List<Sort.Order> sortValues = new ArrayList<>();
        sortValues.add(new Sort.Order(Sort.Direction.DESC, "title"));
        aggregation.getPipeline().add(lookup("Books", "b_id", "_id", "book")).add(sort(Sort.by(sortValues)));
        AggregationResults<MongoComment> result = template.
                aggregate(aggregation, MongoComment.class, MongoComment.class);
        return result.getMappedResults().iterator();
    }
}
