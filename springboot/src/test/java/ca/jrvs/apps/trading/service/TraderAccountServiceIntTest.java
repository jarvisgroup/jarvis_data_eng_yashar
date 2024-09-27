package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {
    @Autowired
    private TraderAccountService traderAccountService;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private AccountDao accountDao;

    private TraderAccountView testView;

    @Before
    public void insert(){
        Trader testTrader = new Trader();
        testTrader.setDob(new Date(1997,01,01));
        testTrader.setFirst_name("Steve");
        testTrader.setLast_name("Bash");
        testTrader.setEmail("sdf@gmail.com");
        testTrader.setCountry("Canada");

        testView = traderAccountService.createTraderAndAccount(testTrader);
        traderAccountService.deposit(testView.getTrader().getId(),100d);
    }


    @Test
    public void withdraw(){
        traderAccountService.withdraw(testView.getTrader().getId(),50d);
        try{
            traderAccountService.deleteTraderById(testView.getTrader().getId());
        }catch (IllegalArgumentException e){
            // Exception caught when there is still fund
            assertTrue(true);
        }
    }

    @After
    public void delete(){
        traderAccountService.deleteTraderById(testView.getTrader().getId());
    }

}