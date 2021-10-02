package ru.otus.librarymigration.batch.readers;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.util.json.ParameterBindingDocumentCodec;
import org.springframework.data.mongodb.util.json.ParameterBindingJsonReader;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class MongoDistinctItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> implements InitializingBean {
    private MongoOperations template;
    private Query query;
    private String queryString;
    private Class<?> entityClass;
    private Class<? extends T> resultClass;

    private Sort sort;
    private String hint;
    private String fields;
    private String collection;
    private List<Object> parameterValues = new ArrayList<>();
    private Object lock = new Object();
    protected Iterator<T> results;

    public MongoDistinctItemReader() {
        setName(ClassUtils.getShortName(MongoDistinctItemReader.class));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.state(this.template != null, "An implementation of MongoOperations is required.");
        Assert.state(this.resultClass != null, "A type to convert the input into is required.");
        Assert.state(this.queryString != null || this.query != null, "A query is required.");
        if (this.queryString != null) {
            Assert.state(this.sort != null, "A sort is required.");
        }

    }

    public void setSort(Map<String, Sort.Direction> sorts) {
        this.sort = this.convertToSort(sorts);
    }

    private String replacePlaceholders(String input, List<Object> values) {
        ParameterBindingJsonReader reader = new ParameterBindingJsonReader(input, values.toArray());
        DecoderContext decoderContext = DecoderContext.builder().build();
        Document document = (new ParameterBindingDocumentCodec()).decode(reader, decoderContext);
        return document.toJson();
    }

    private Sort convertToSort(Map<String, Sort.Direction> sorts) {
        List<Sort.Order> sortValues = new ArrayList<>();
        Iterator iterator = sorts.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Sort.Direction> curSort = (Map.Entry) iterator.next();
            sortValues.add(new Sort.Order(curSort.getValue(), curSort.getKey()));
        }
        return Sort.by(sortValues);
    }

    @Override
    protected void doOpen() throws Exception {
    }

    @Override
    protected void doClose() throws Exception {
    }

    @Override
    @Nullable
    protected T doRead() throws Exception {
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

    protected Iterator<T> doReadAll() {
        if (this.queryString != null) {
            String populatedQuery = this.replacePlaceholders(this.queryString, this.parameterValues);
            BasicQuery mongoQuery;

            mongoQuery = new BasicQuery(populatedQuery);

            if (StringUtils.hasText(this.hint)) {
                mongoQuery.withHint(this.hint);
            }

            if (StringUtils.hasText(this.collection)) {
                return (Iterator<T>) this.template.findDistinct(mongoQuery, fields, this.collection, entityClass, resultClass).iterator();
            } else {
                return (Iterator<T>) this.template.findDistinct(mongoQuery, fields, entityClass, resultClass).iterator();
            }
        } else {
            if (StringUtils.hasText(this.collection)) {
                return (Iterator<T>) this.template.findDistinct(query, fields, this.collection, entityClass, resultClass).iterator();
            } else {
                return (Iterator<T>) this.template.findDistinct(query, fields, entityClass, resultClass).iterator();
            }
        }
    }
}
