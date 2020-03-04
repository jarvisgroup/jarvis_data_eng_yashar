package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder,Integer>{

    private static final String TABLE_NAME = "security_order";
    private static final String ID_COLUMN_NAME = "id";

    private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN_NAME);
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
    Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }

    @Override
    protected int updateOne(SecurityOrder entity) {
        String updateSql = "UPDATE ticker SET last_price=?, bid_price=?,"
                + "bid_size=?, ask_price=?,ask_size=? WHERE ticker=?";
        return getJdbcTemplate().update(updateSql,makeUpdateValues(entity));
    }

    //    helper method that makes sql update values objects
    private Object[] makeUpdateValues(SecurityOrder securityOrder) {
        List<Object> values = new ArrayList<>();
        values.add(securityOrder.getId());
        values.add(securityOrder.getNote());
        values.add(securityOrder.getPrice());
        values.add(securityOrder.getSize());
        values.add(securityOrder.getAccountId());
        values.add(securityOrder.getStatus());
        values.add(securityOrder.getTicker());
        return values.toArray();
    }
}
