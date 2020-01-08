package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sun.net.www.http.HttpClient;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/*
 * MarketDao is responsible for getting Quotes form IEX
 */
@Repository
public class MarketDataDao implements CrudRepository<IexQuote,String> {
    private static final String IEX_BATH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
    private final String IEX_BATCH_URL;

    private Logger logger = (Logger) LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;
    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig){
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL =  marketDataConfig.getHost() + IEX_BATH_PATH + marketDataConfig.getToken();
    }

    // Get an IexQuote
    @Override
    public Optional<IexQuote> findById(String tickers) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = (List<IexQuote>) findAllById(Collections.singletonList(tickers));

        if(quotes.size() == 0){
            return Optional.empty();
        }else if (quotes.size() == 1){
            iexQuote = Optional.of(quotes.get(0));
        }else{
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }
        return iexQuote;
    }

    // Execute a get and return http entity as a string
    private Optional<String> executeHttpGet(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        CloseableHttpClient httpClient = getHttpClient();
        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        if(statusCode == 404){
            return Optional.empty();
        }else if(statusCode == 200){
            HttpEntity entity = response.getEntity();
            String entityString = EntityUtils.toString(entity,"UTF-8");
            return Optional.ofNullable(entityString);
        }else{
            throw new DataRetrievalFailureException("Unexpected status code : " + statusCode);
        }
    }

    // Borrow a Http client from the httpClientConnectionManager
    private CloseableHttpClient getHttpClient(){
        return HttpClients.custom().setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true).build();
    }
    @Override
    public <S extends IexQuote> S save(S s) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public Iterable<IexQuote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
