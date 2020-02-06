package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoIntTest {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TraderDao traderDao;

    private Account account;
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
        account = new Account();
        account.setTraderId(trader.getId());
        account.setAmount(1000d);
        account.setId(accountDao.save(account).getId());
    }

    @Test
    public void findAll(){
        List<Account> testAccounts = new ArrayList<>();
        testAccounts = accountDao.findAllById(Arrays.asList(account.getId()));
        assertEquals(account.getAmount(),testAccounts.get(0).getAmount());
        assertEquals(account.getTraderId(),testAccounts.get(0).getTraderId());;
    }

    @After
    public void delete(){
        accountDao.deleteById(account.getId());
        traderDao.deleteById(trader.getId());
    }


}