package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
}

