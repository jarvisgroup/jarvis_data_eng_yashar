package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {
    @Autowired
    private QuoteDao quoteDao;

    private List<Quote> testQuotes = new ArrayList<>();

    @Before
    public void insertQuotes(){


        String[] tickers = {"APPL","GOOG"};
        for(String ticker : tickers){
            Quote quote = new Quote();
            quote.setId(ticker);
            quote.setAskSize(10);
            quote.setAskPrice(100d);
            quote.setBidSize(120);
            quote.setBidPrice(110d);
            quote.setLastPrice(120d);
            testQuotes.add(quote);
        }
        quoteDao.saveAll(testQuotes);
    }

    @Test
    public void findById(){
        String ticker = "APPL";
        Optional<Quote> quote = quoteDao.findById(ticker);
        assertEquals(quote.get().getAskPrice(),testQuotes.get(0).getAskPrice());
        assertEquals(quote.get().getAskSize(),testQuotes.get(0).getAskSize());
        assertEquals(quote.get().getBidPrice(),testQuotes.get(0).getBidPrice());
        assertEquals(quote.get().getBidSize(),testQuotes.get(0).getBidSize());
        assertEquals(quote.get().getLastPrice(),testQuotes.get(0).getLastPrice());
    }

    @Test
    public void existByID(){
        assertTrue(quoteDao.existsById("APPL"));
        assertTrue(quoteDao.existsById("GOOG"));
        assertFalse(quoteDao.existsById("FB"));

    }

    @After
    public void deleteById(){
        for(Quote quote : testQuotes){
            quoteDao.deleteById(quote.getId());
        }
        assertEquals(0,quoteDao.count());
    }

}