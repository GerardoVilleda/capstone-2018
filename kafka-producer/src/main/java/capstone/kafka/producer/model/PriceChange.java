package capstone.kafka.producer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceChange {

    @JsonProperty
    private String ticker;

    @JsonProperty
    private Double price;

    public PriceChange() {

    }

    public PriceChange(String ticker, Double price) {
        this.ticker = ticker;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "{ ticker: " + this.ticker + ", price: " + price + " }";
    }

}
