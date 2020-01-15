package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class QuoteDao extends JdbcCrudDao<Quote> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;



    @Autowired
    public QuoteDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) dataSource).withTableName(TABLE_NAME);
    }


    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN_NAME;
    }

    @Override
    Class<Quote> getEntityClass() {
        return Quote.class;
    }

    // helper method to save one quote
    @Override
    protected void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if(row != 1){
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }
    // helper method to update one quote
    @Override
    public int updateOne(Quote quote) {
        String updateSql = "UPDATE quote SET last_price=?, bid_price=?,"
                        + "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
        return getJdbcTemplate().update(updateSql,makeUpdateValues(quote));
    }

    // helper method that makes sql update values objects
    private Object[] makeUpdateValues(Quote quote) {
        List<Object> values = new ArrayList<>();
        values.add(quote.getId());
        values.add(quote.getLastPrice());
        values.add(quote.getBidPrice());
        values.add(quote.getBidSize());
        values.add(quote.getAskPrice());
        values.add(quote.getAskSize());
        return values.toArray();
    }


    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
