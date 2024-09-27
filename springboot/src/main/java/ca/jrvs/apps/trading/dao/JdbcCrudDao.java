package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JdbcCrudDao<T extends Entity,ID> implements CrudRepository<T,ID> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();
    abstract public String getIdColumnName();
    abstract Class<T> getEntityClass();

    @Override
    public <S extends T> S save(S entity) {
        if(existsById((ID) entity.getId())){
            if(updateOne(entity) != 1){
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        }else{
            addOne(entity);
        }
        return entity;
    }

    protected  <S extends T> void addOne(S entity){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(newId.intValue());
    }

    protected abstract int updateOne(T entity);

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> allEntities = new ArrayList<>();
        for(S entity : entities){
            allEntities.add((S) save(entity));
        }
        return allEntities;
    }

    @Override
    public Optional<T> findById(ID id) {
        Optional<T> entity = Optional.empty();
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
        try{
            entity = Optional.ofNullable((T) getJdbcTemplate().queryForObject(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()),id));
        }catch (EmptyResultDataAccessException e){
            logger.debug("Cannot find trader id: " + id, e);
        }
        return entity;
    }

    @Override
    public boolean existsById(ID id) {
        if(id == null){
            throw new IllegalArgumentException("Cannot find id");
        }
        String countSql = "SELECT COUNT(*) FROM " + getTableName() +
                " WHERE " + getIdColumnName() + "=?";
        return getJdbcTemplate().queryForObject(countSql,Integer.class,id) == 1;
    }

    @Override
    public List<T> findAll() {
        String selectSql = "SELECT * FROM " + getTableName();
        return getJdbcTemplate().query(selectSql,BeanPropertyRowMapper.newInstance(getEntityClass()));
    }

    @Override
    public List<T> findAllById(Iterable<ID> integers) {
        return null;
    }

    @Override
    public long count() {
        String countSql = "SELECT COUNT(*) FROM " + getTableName();
        return getJdbcTemplate().queryForObject(countSql,Long.class);
    }

    @Override
    public void deleteById(ID id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        String deleteSql = "DELETE FROM " + getTableName() + "WHERE ticker=?";
        getJdbcTemplate().update(deleteSql,id);
    }

    @Override
    public void delete(T entity) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM " + getTableName();
        getJdbcTemplate().update(deleteSql);
    }

}
