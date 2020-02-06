package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {
    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private QuoteDao quoteDao;

    private Account account;
    private Trader trader;
    private Quote quote;
    private SecurityOrder securityOrder;

    @Before
    public void init(){
        trader = new Trader();
        trader.setCountry("Canada");
        trader.setEmail("asdf@gmail.com");
        trader.setLast_name("Devin");
        trader.setFirst_name("Booker");
        trader.setDob(new Date(1997,02,06));
        traderDao.save(trader);

        account = new Account();
        account.setAmount(1000d);
        account.setTraderId(trader.getId());

        quote = new Quote();
        quote.setLastPrice(110d);
        quote.setBidPrice(100d);
        quote.setBidSize(10);
        quote.setAskPrice(105d);
        quote.setAskSize(10);
        quote.setId("APPL");
        quoteDao.save(quote);

        securityOrder = new SecurityOrder();
        securityOrder.setAccountId(account.getId());
        securityOrder.setNote("New Order");
        securityOrder.setPrice(100d);
        securityOrder.setTicker(quote.getId());
        securityOrder.setStatus("No Status");
        securityOrder.setSize(100);
        securityOrderDao.save(securityOrder);
    }

    @Test
    public void findById(){
        List<SecurityOrder> testSecurityOrders = new ArrayList<>();
        testSecurityOrders = securityOrderDao.findAllById(Arrays.asList(securityOrder.getAccountId()));
        assertEquals(securityOrder.getNote(),testSecurityOrders.get(0).getNote());
        assertEquals(securityOrder.getPrice(),testSecurityOrders.get(0).getPrice());
        assertEquals(securityOrder.getTicker(),testSecurityOrders.get(0).getTicker());
        assertEquals(securityOrder.getStatus(),testSecurityOrders.get(0).getStatus());
        assertEquals(securityOrder.getSize(),testSecurityOrders.get(0).getSize());
    }

    @After
    public void delete(){
        securityOrderDao.deleteById(securityOrder.getAccountId());
        accountDao.deleteById(account.getId());
        quoteDao.deleteById(quote.getId());
        traderDao.deleteById(trader.getId());
    }
}