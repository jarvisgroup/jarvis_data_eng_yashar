package ca.jrvs.apps.trading.model.dto;

public class MarketOrderDto {
    private Integer accoudId;
    private Integer size;
    private String ticker;

    public Integer getAccoudId() {
        return accoudId;
    }

    public void setAccoudId(Integer accoudId) {
        this.accoudId = accoudId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
