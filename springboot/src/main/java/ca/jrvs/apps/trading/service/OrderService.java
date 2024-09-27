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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /*
     * Execute a market order
     * validate order, create a securityOrder
     * Handle buy/sell
     *      buy : check account balance
     *      sell : check position
     *       update securityOrder.status
     * Save and return securityOrder
     */
     public SecurityOrder executeMarketOrder(MarketOrderDto orderDto){
            if(orderDto.getSize() == 0 || orderDto.getTicker() == null){
                throw new IllegalArgumentException("All field shouldn't be null");
            }
            Quote quote = quoteDao.findById(orderDto.getTicker()).orElseThrow(
                    ()->new IllegalArgumentException("invalid ticker"));
            Account account = accountDao.findById(orderDto.getAccoudId()).orElseThrow(
                    ()-> new IllegalArgumentException("invalid account id"));
            SecurityOrder securityOrder = new SecurityOrder();
            securityOrder.setSize(orderDto.getSize());
            securityOrder.setTicker(orderDto.getTicker());
            securityOrder.setAccountId(orderDto.getAccoudId());
            securityOrderDao.save(securityOrder);

            if(securityOrder.getSize() > 0){   // buy
                securityOrder.setPrice(quote.getAskPrice());
                handleBuyMartketOrder(orderDto,securityOrder,account);
            }else{  // sell
                securityOrder.setPrice(quote.getBidPrice());
                handleSellMarketOrder(orderDto,securityOrder,account);
            }
            return securityOrder;
     }

    //Helper method that execute a buy order
    protected void handleBuyMartketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account){
        if(account.getAmount() >= securityOrder.getPrice() * securityOrder.getSize()){
            account.setAmount(account.getAmount() - securityOrder.getPrice() * securityOrder.getSize());
            accountDao.save(account);
            securityOrder.setStatus("FILLED");
        }else{
            securityOrder.setStatus("REJECTED");
            securityOrder.setNote("No enough amount in this account");
        }
    }

    //Helper method that execute a sell order
    protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account){
        List<Position> positions = positionDao.findByAccountId(account.getId());
        if(positions == null){
            throw new IllegalArgumentException("Order size is larger than position");
        }
        for(Position p : positions){
            if(p.getTicker().equals(securityOrder.getTicker()) && p.getPosition() >= securityOrder.getSize()){
                account.setAmount(account.getAmount() + securityOrder.getPrice() * securityOrder.getSize());
                securityOrder.setStatus("FILLED");
                accountDao.save(account);
            }else{
                securityOrder.setStatus("REJECTED");
                securityOrder.setNote("No position to sell");
            }
        }
    }
}
