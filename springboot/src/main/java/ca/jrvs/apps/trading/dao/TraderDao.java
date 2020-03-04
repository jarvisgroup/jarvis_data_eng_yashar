package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
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
public class TraderDao extends JdbcCrudDao<Trader,Integer> {
    private static final String TABLE_NAME = "trader";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public TraderDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                                    .usingGeneratedKeyColumns(ID_COLUMN_NAME);
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
    Class<Trader> getEntityClass() {
        return Trader.class;
    }

    @Override
    protected int updateOne(Trader entity) {
        String updateSql = "UPDATE ticker SET last_price=?, bid_price=?,"
                            + "bid_size=?, ask_price=?,ask_size=? WHERE ticker=?";
        return getJdbcTemplate().update(updateSql,makeUpdateValues(entity));
    }


    //    helper method that makes sql update values objects
    private Object[] makeUpdateValues(Trader trader) {
        List<Object> values = new ArrayList<>();
        values.add(trader.getId());
        values.add(trader.getFirst_name());
        values.add(trader.getLast_name());
        values.add(trader.getCountry());
        values.add(trader.getDob());
        values.add(trader.getEmail());
        return values.toArray();
    }



}
