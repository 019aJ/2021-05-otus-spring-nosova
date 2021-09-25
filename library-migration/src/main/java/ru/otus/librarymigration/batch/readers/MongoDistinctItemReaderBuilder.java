package ru.otus.librarymigration.batch.readers;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MongoDistinctItemReaderBuilder<T> {
    private MongoOperations template;
    private String jsonQuery;
    private Class<? extends T> entityClass;
    private Class<? extends T> resultClass;

    private Map<String, Sort.Direction> sorts;
    private String hint;
    private String fields;
    private String collection;
    private List<Object> parameterValues = new ArrayList<>();
    private boolean saveState = true;
    private String name;
    private int maxItemCount = 2147483647;
    private int currentItemCount;
    private Query query;

    public MongoDistinctItemReaderBuilder() {

    }


    public MongoDistinctItemReaderBuilder<T> saveState(boolean saveState) {
        this.saveState = saveState;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> name(String name) {
        this.name = name;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> maxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> currentItemCount(int currentItemCount) {
        this.currentItemCount = currentItemCount;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> template(MongoOperations template) {
        this.template = template;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> jsonQuery(String query) {
        this.jsonQuery = query;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> entityClass(Class<? extends T> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> resultClass(Class<? extends T> resultClass) {
        this.resultClass = resultClass;
        return this;
    }


    public MongoDistinctItemReaderBuilder<T> parameterValues(List<Object> parameterValues) {
        this.parameterValues = parameterValues;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> parameterValues(Object... parameterValues) {
        return this.parameterValues(Arrays.asList(parameterValues));
    }

    public MongoDistinctItemReaderBuilder<T> fields(String fields) {
        this.fields = fields;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> sorts(Map<String, Sort.Direction> sorts) {
        this.sorts = sorts;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> collection(String collection) {
        this.collection = collection;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> hint(String hint) {
        this.hint = hint;
        return this;
    }

    public MongoDistinctItemReaderBuilder<T> query(Query query) {
        this.query = query;
        return this;
    }

    public MongoDistinctItemReader<T> build() {
        Assert.notNull(this.template, "template is required.");
        if (this.saveState) {
            Assert.hasText(this.name, "A name is required when saveState is set to true");
        }

        Assert.notNull(this.entityClass, "targetType is required.");
        Assert.state(StringUtils.hasText(this.jsonQuery) || this.query != null, "A query is required");
        if (StringUtils.hasText(this.jsonQuery)) {
            Assert.notNull(this.sorts, "sorts map is required.");
        }

        MongoDistinctItemReader<T> reader = new MongoDistinctItemReader<>();
        reader.setTemplate(this.template);
        reader.setEntityClass(this.entityClass);
        reader.setResultClass(this.resultClass);
        reader.setQueryString(this.jsonQuery);
        reader.setSort(this.sorts);
        reader.setHint(this.hint);
        reader.setFields(this.fields);
        reader.setCollection(this.collection);
        reader.setParameterValues(this.parameterValues);
        reader.setQuery(this.query);
        reader.setName(this.name);
        reader.setSaveState(this.saveState);
        reader.setCurrentItemCount(this.currentItemCount);
        reader.setMaxItemCount(this.maxItemCount);
        return reader;
    }
}
