package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Captor
    ArgumentCaptor<SecurityOrder> captorSecurityOrder;

    @Mock
    private AccountDao accountDao;

    @Mock
    private SecurityOrderDao securityOrderDao;

    @Mock
    private QuoteDao quoteDao;

    @Mock
    private PositionDao positionDao;

    @InjectMocks
    private OrderService orderService;

    private Quote quoteTest;
    private Account accountTest;
    private Position positionTest;
    @Before
    public void setup() {
        quoteTest.setAskSize(10);
        quoteTest.setAskPrice(100d);
        quoteTest.setBidSize(10);
        quoteTest.setBidPrice(101d);
        quoteTest.setLastPrice(102d);
        quoteTest.setId("APPL");
        when(quoteDao.findById(quoteTest.getId())).thenReturn(Optional.of(quoteTest));

        accountTest.setAmount(1000d);
        accountTest.setTraderId(100);
        accountTest.setId(10);
        when(accountDao.findById(any())).thenReturn(Optional.of(accountTest));

        List<Position> positions = new ArrayList<>();
        positionTest.setAccountId(10);
        positionTest.setTicker("APPL");
        positions.add(positionTest);
        when(positionDao.findByAccountId(any())).thenReturn(positions);
    }

    @Test
    public void buyOrder(){
        MarketOrderDto marketOrderDto = new MarketOrderDto();
        marketOrderDto.setAccoudId(10);
        marketOrderDto.setSize(10);
        marketOrderDto.setTicker("APPL");

        SecurityOrder securityOrder = orderService.executeMarketOrder(marketOrderDto);
        verify(securityOrderDao).save(captorSecurityOrder.capture());
        assertEquals("FILLED", captorSecurityOrder.getAllValues().get(1).getStatus());
    }

    @Test
    public void sellOrder(){
        MarketOrderDto marketOrderDto = new MarketOrderDto();
        marketOrderDto.setAccoudId(10);
        marketOrderDto.setSize(-10);
        marketOrderDto.setTicker("APPL");
        positionTest.setPosition(10);
        SecurityOrder securityOrder = orderService.executeMarketOrder(marketOrderDto);
        verify(securityOrderDao).save(captorSecurityOrder.capture());
        assertEquals("FILLED", captorSecurityOrder.getAllValues().get(1).getStatus());
    }
}

