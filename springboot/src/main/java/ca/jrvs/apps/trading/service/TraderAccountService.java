package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * validate user input, create a trader, create an account
     * create, setup, and return a new traderAccount view
     */
    public TraderAccountView createTraderAndAccount(Trader trader){
        if(trader.getCountry() == null || trader.getDob() == null || trader.getEmail() == null || trader.getFirst_name() == null
            || trader.getLast_name() == null || trader.getId() != null){
            throw new IllegalArgumentException("Invalid user input, all field need to be not null");
        }
        Trader newTrader = traderDao.save(trader);
        Account newAccount = new Account();
        newAccount.setId(newTrader.getId());

        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setTrader(newTrader);
        traderAccountView.setAccount(newAccount);
        return traderAccountView;
    }


    /* A trader can be deleted iff it has no open position and 0 cash balance
     * validate traderID, get trader account by traderId AND CHECK account balance
     * get positions by accountID and check positions
     * delete all securityOrders, account, trader
     */
    public void deleteTraderById(Integer traderId){
        if(traderId == null ) {
            throw new IllegalArgumentException("empty traderid");
        }
        Account account = accountDao.findById(traderId).orElseThrow(() ->
                new IllegalArgumentException("cannot find trader account"));

        if(account.getAmount() != 0){
            throw new IllegalArgumentException("Still has fund in the account, unable to delete");
        }
        List<Position> allPosition = positionDao.findByAccountId(traderId);
        for(Position position : allPosition){
            if(position.getPosition() != 0){
                throw new IllegalArgumentException("trader has opened position");
            }
        }
        securityOrderDao.deleteById(traderId);
        accountDao.deleteById(traderId);
        traderDao.deleteById(traderId);
    }


    /*
     * deposit a fund to an account by traderId
     * validate user input, account = accountDao.findByTraderId, accountDao.updateAmountById
     *
     */
    public Account deposit(Integer traderId, Double fund){
        if(traderId == null ){
            throw new IllegalArgumentException("empty traderid");
        }else if(fund <= 0d){
            throw new IllegalArgumentException("fund cannot be zero/negative");
        }

        Account account = accountDao.findById(traderId).orElseThrow(() ->
                new IllegalArgumentException("cannot find trader account"));
        Double newBalance = account.getAmount() + fund;
        account.setAmount(newBalance);
        return account;
    }

    /*
     * withdraw a fund to an account by traderId
     * -validate userinput,
     */
    public Account withdraw(Integer traderId, Double fund){
        if(traderId == null ){
            throw new IllegalArgumentException("empty traderid");
        }else if(fund <= 0d){
            throw new IllegalArgumentException("fund cannot be zero/negative");
        }
        Account account = accountDao.findById(traderId).orElseThrow(() ->
                new IllegalArgumentException("cannot find trader account"));
        Double newBalance = account.getAmount() - fund;

        if(newBalance >= 0){
            account.setAmount(newBalance);
            return account;
        }else{
            throw new IllegalArgumentException("Insufficient fund");
        }

    }
}
