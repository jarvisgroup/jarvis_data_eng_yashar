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

import javax.activation.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class QuoteDao implements CrudRepository<Quote,String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public static String getIdColumnName() {
        return ID_COLUMN_NAME;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public QuoteDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public Quote save(Quote quote) {
        if(existsById(quote.getId())){
            int updatedRowNo = updateOne(quote);
            if(updatedRowNo != 1){
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        }else{
            addOne(quote);
        }
        return quote;
    }

    // helper method to save one quote
    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if(row != 1){
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    // helper method to update one quote
    private int updateOne(Quote quote) {
        String updateSql = "UPDATE quote SET last_price=?, bid_price=?,"
                        + "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
        return jdbcTemplate.update(updateSql,makeUpdateValues(quote));
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
    public <S extends Quote> Iterable<S> saveAll(Iterable<S> iterable) {
        List<Quote> allQuote = new ArrayList<>();
        for(Quote q : iterable){
            allQuote.add(save(q));
        }
        return (Iterable<S>) allQuote;
    }

    @Override
    public Optional<Quote> findById(String id) {
        Optional<Quote> quote = null;
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
        try{
            quote = Optional.ofNullable(getJdbcTemplate().queryForObject(selectSql, BeanPropertyRowMapper.newInstance(Quote.class),id));
        }catch (EmptyResultDataAccessException e){
            logger.debug("Cannot find trader id: " + id, e);
        }

//        if(quote == null){
//            throw new ResourceNotFoundException("Resource not found");
//        }

        return quote;
    }
    //Returns whether an entity with the given id exists.
    @Override
    public boolean existsById(String id) {
        if(id == null){
            throw new IllegalArgumentException("Cannot find id");
        }
        String countSql = "SELECT COUNT(*) FROM " + getTableName() +
                " WHERE " + getIdColumnName() + "=?";

    }

    @Override
    public Iterable<Quote> findAll() {
        return null;
    }


    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(String id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        String deleteSql = "DELETE FROM " + getTableName() + "WHERE ticker=?";
        jdbcTemplate.update(deleteSql,id);
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
