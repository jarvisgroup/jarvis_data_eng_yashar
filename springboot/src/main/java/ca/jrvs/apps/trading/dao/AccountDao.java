package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDao extends JdbcCrudDao<Account,Integer>{

    private static final String TABLE_NAME = "account";
    private static final String ID_COLUMN_NAME = "id";

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AccountDao(DataSource dataSource){
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
    Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    protected int updateOne(Account entity) {
        String updateSql = "UPDATE ticker SET amount=? WHERE ticker=?";
        return getJdbcTemplate().update(updateSql,makeUpdateValues(entity));
    }


    //    helper method that makes sql update values objects
    private Object[] makeUpdateValues(Account account) {
        List<Object> values = new ArrayList<>();
        values.add(account.getId());
        values.add(account.getAmount());
        values.add(account.getTraderId());
        return values.toArray();
    }
}
