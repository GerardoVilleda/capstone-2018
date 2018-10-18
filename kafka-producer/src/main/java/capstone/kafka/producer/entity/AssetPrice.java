package capstone.kafka.producer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AssetPrice {
    @Id
    private String ticker;

    @Column(name = "price")
    private Double price;

    public AssetPrice() {

    }

    public AssetPrice(String ticker, Double price) {
        this.ticker = ticker;
        this.price = price;
    }
}
