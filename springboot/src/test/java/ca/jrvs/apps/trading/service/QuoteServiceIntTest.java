package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

    @Autowired
    private QuoteService quoteService;
    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;
    private Quote quote;
    @Before
    public void setup(){
        quoteDao.deleteAll();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setId("AAPL");
        savedQuote.setLastPrice(10.1d);
        quote.setAskPrice(12d);
        quote.setAskSize(10);
        quote.setBidPrice(12.2d);
        quote.setBidSize(10);
        quote.setId("FB");
        quote.setLastPrice(12.1d);
        quoteDao.save(quote);
    }

    @Test
    public void findIexQuoteByTicker(){
        IexQuote iexQuote = quoteService.findIexQuoteByTicker("FB");
        assertNotNull(iexQuote);
    }

    @Test
    public void updateMarketData(){
        quoteService.updateMarketData();
        assertNotEquals(0,quoteDao.count());
        assertTrue(quoteDao.existsById("FB"));
    }

    @Test
    public void saveQuotes(){
        List<String> tickers = new ArrayList<>();
        tickers.add("AAPL");
        tickers.add("APA");
        quoteService.saveQuotes(tickers);
        assertEquals(3,quoteDao.count());
    }

    @Test
    public void saveQuote(){
        quoteService.saveQuote(savedQuote);
        assertEquals(2,quoteDao.count());
    }

    @Test
    public void findAllQuotes(){
        List<Quote> quotes = quoteService.findAllQuotes();
        assertEquals(1,quoteDao.count());
    }

}