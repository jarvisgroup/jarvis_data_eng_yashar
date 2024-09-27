package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderDaoIntTest {
    @Autowired
    private TraderDao traderDao;

    private Trader trader;

    @Before
    public void init(){
        trader = new Trader();
        trader.setCountry("Canada");
        trader.setDob(new Date(1997,02,12));
        trader.setEmail("ahsfa@gmail.com");
        trader.setFirst_name("James");
        trader.setLast_name("Kobe");
        trader.setId(traderDao.save(trader).getId());
    }

    @Test
    public void findById(){
        List<Trader> testTrader = new ArrayList<>();
        testTrader = traderDao.findAllById(Arrays.asList(trader.getId()));
        assertEquals(trader.getCountry(),testTrader.get(0).getCountry());
        assertEquals(trader.getEmail(),testTrader.get(0).getEmail());
        assertEquals(trader.getDob(),testTrader.get(0).getDob());
        assertEquals(trader.getFirst_name(),testTrader.get(0).getFirst_name());
        assertEquals(trader.getLast_name(),testTrader.get(0).getLast_name());
    }

    @After
    public void delete(){
        traderDao.deleteById(trader.getId());
    }
}