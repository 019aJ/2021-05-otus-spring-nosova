package ru.otus.librarymigration.batch.writers;

import lombok.Setter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class JdbcBatchItemOnlyNewWriter<T> extends JdbcBatchItemWriter<T> {
    private String checkQuery;

    @Override
    public void write(final List<? extends T> items) throws Exception {
        // если такой автор уже есть, мы не будем его вставлять опять
        List<? extends T> filteresItems = items.stream().filter(item -> {
            SqlParameterSource params = this.itemSqlParameterSourceProvider.createSqlParameterSource(item);
            var res = this.namedParameterJdbcTemplate.query(checkQuery, params, new BeanPropertyRowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    return "1";
                }
            });
            return res.size() == 0;
        }).collect(Collectors.toList());
        super.write(filteresItems);
    }
}
