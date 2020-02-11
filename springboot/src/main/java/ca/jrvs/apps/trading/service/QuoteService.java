package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Transactional
@Service
public class QuoteService {
    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /* Find an IexQuote
     *@param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker){
        if(quoteDao.existsById(ticker) == false){
            throw new IllegalArgumentException("Invalid ticker");
        }
        return marketDataDao.findById(ticker).orElseThrow(()-> new IllegalArgumentException(ticker + "is invalid"));
    }

    /* Update quote table against IEX source
     * get all quotes from DB
     * for each ticker get iexQuote
     * convert iexQuote to quote entity
     * persist quote to db
     *  @throws IllegalArgumentException for invalid input
     *  @throws ResourceNotFoundException if ticker not found from IEX
     *  @throws DataAccessException if unable to retrieve data
     */
    public List<Quote> updateMarketData(){
        List<Quote> allQuotes = quoteDao.findAll();
        for(Quote eachQuote : allQuotes){
            IexQuote iexQuote = findIexQuoteByTicker(eachQuote.getId());
            quoteDao.save(buildQuoteFromIexQuote(iexQuote));
        }
        return allQuotes;
    }

    // Helper method to convert IexQuote to Quote entity
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
        Quote newQuote = new Quote();
        newQuote.setId(iexQuote.getSymbol());
        newQuote.setLastPrice(iexQuote.getLatestPrice());
        newQuote.setBidPrice(iexQuote.getIexBidPrice());
        newQuote.setBidSize(iexQuote.getIexBidSize());
        newQuote.setAskPrice(iexQuote.getIexAskPrice());
        newQuote.setAskSize(iexQuote.getIexAskSize());
        return newQuote;
    }

    /* Validate and save given tickers to quote table
     * get iexQuote, convert to Quote Entity, persist quote to db
     * @throws IllegalArgument exception if ticker is not found
     */
    public List<Quote> saveQuotes(List<String> tickers){
        List<Quote> quotes = new ArrayList<>();
        try{
            for(String s : tickers){
                quotes.add(saveQuote(s));
            }
        }catch (DataRetrievalFailureException e){
            throw new IllegalArgumentException("Ticker not found");
        }
        return quotes;
    }

    // Helper method
    public Quote saveQuote(String ticker){
        IexQuote iexQuote = findIexQuoteByTicker(ticker);
        return saveQuote(buildQuoteFromIexQuote(iexQuote));
    }

    public Quote saveQuote(Quote quote){return quoteDao.save(quote);}

    public List<Quote> findAllQuotes(){return quoteDao.findAll();}

}
