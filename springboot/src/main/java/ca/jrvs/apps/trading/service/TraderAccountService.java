package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {
    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired

    public TraderAccountService(TraderDao traderDao, AccountDao accountDao, PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /*
     * Create a new trader and initialize a new account with 0 amount
     * validate user input, create a trader, create an account, create, setup, and return a new traderAccount view
     */
    public TraderAccountView createTraderAndAccount(Trader trader){

    }

    /* A trader can be deleted iff it has no open position and 0 cash balance
     * validate traderID, get trader account by traderId AND CHECK account balance
     * get positions by accountID and check positions
     * delete all securityOrders, account, trader
     */
    public void deleteTraderById(Integer traderId){}


    /*
     * deposit a fund to an account by traderId
     * validate user input, account = accountDao.findByTraderId, accountDao.updateAmountById
     *
     */
    public Account deposit(Integer traderId, Double fund){

    }

    /*
     * withdraw a fund to an account by traderId
     * -validate userinput,
     */
    public Account withdraw(Integer traderId, Double found){}
}
