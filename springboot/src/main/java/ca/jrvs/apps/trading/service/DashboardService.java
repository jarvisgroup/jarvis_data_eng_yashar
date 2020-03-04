package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.SecurityRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.jrvs.apps.trading.model.view.TraderAccountView

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DashboardService {
    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QuoteDao quoteDao;

    @Autowired
    public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao, QuoteDao quoteDao) {
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }

    //Create and return a traderAccountView by traderId
    public TraderAccountView getTraderAccount(Integer traderId){
        if(traderId == null ) {
            throw new IllegalArgumentException("empty traderid");
        }
        Account account = accountDao.findById(traderId).orElseThrow(
                () -> new IllegalArgumentException("Invalid traderId"));
        Trader trader = traderDao.findById(traderId).orElseThrow(
                () -> new IllegalArgumentException("Invalid traderId"));
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setAccount(account);
        traderAccountView.setTrader(trader);
        return traderAccountView;
    }

    // Create and return portfolio view by traderId
    public PortfolioView getProfileViewByTraderId(Integer traderId) {
        if(traderId == null ) {
            throw new IllegalArgumentException("empty traderid");
        }
        Account account = accountDao.findById(traderId).orElseThrow(
                () -> new IllegalArgumentException("Invalid traderId"));
        List<Position> allPositions = positionDao.findByAccountId(account.getId());

        PortfolioView portfolioView = new PortfolioView();
        List<SecurityRow> securityRows = new ArrayList<>();

        String currentId;
        for(Position position : allPositions){
            SecurityRow sRow = new SecurityRow();
            currentId = position.getTicker();
            sRow.setPosition(position);
            sRow.setTicker(currentId);
            sRow.setQuote(quoteDao.findById(currentId));
            securityRows.add(sRow);
        }
        portfolioView.setSecurityRows(securityRows);
        return portfolioView;
    }

}
