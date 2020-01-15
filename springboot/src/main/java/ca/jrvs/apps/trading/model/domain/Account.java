package ca.jrvs.apps.trading.model.domain;

public class Account{

    private Double amount;
    private Integer traderId;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }
}
