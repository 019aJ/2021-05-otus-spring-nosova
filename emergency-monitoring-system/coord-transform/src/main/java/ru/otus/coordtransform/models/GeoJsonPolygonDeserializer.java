package ru.otus.coordtransform.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonPolygonDeserializer extends JsonDeserializer<CalculationResult> {
    @Override
    public CalculationResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        JsonNode posNode = node.get("geometry");
        List<Polygon> polygons = new ArrayList<>();
        if (posNode.isArray()) {
            for (final JsonNode objNode : posNode) {
                JsonNode pointsNode = objNode.get("points");
                List<Point> points = new ArrayList<>();
                if (pointsNode.isArray()) {
                    for (final JsonNode objPointNode : pointsNode) {
                        String x = objPointNode.get("x").asText();
                        String y = objPointNode.get("y").asText();
                        Point p = new Point(Double.parseDouble(x), Double.parseDouble(y));
                        points.add(p);
                    }
                }
                polygons.add(new Polygon(points));
            }
        }
        return new CalculationResult(polygons);
    }
}