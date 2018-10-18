package capstone.kafka.producer.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import capstone.kafka.producer.entity.AssetPrice;
import capstone.kafka.producer.model.PriceChange;
import capstone.kafka.producer.repository.AssetPriceRepository;
import capstone.kafka.producer.serde.PriceChangeSerde;

@Component
public class PriceChangeConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceChangeConsumer.class);

    private AssetPriceRepository assetPriceRepository;

    @Autowired
    public PriceChangeConsumer(AssetPriceRepository assetPriceRepository) {
        this.assetPriceRepository = assetPriceRepository;
    }

    @KafkaListener(topics = "PriceChangeTopic")
    public void receive(byte[] message) {
        PriceChange priceChange = PriceChangeSerde.INSTANCE.deserialize(message);
        LOGGER.info(priceChange.toString());

        AssetPrice assetPrice = new AssetPrice(priceChange.getTicker(), priceChange.getPrice());
        this.assetPriceRepository.save(assetPrice);
    }
}
