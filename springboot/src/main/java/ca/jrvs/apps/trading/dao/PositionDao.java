package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao {
    private static final String TABLE_NAME = "position";

    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    // return a list of position with given accountId
    public List<Position> findByAccountId(Integer accountId){
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE account_id=?";
        return getJdbcTemplate().query(selectSql, BeanPropertyRowMapper.newInstance(Position.class));
    }
}
