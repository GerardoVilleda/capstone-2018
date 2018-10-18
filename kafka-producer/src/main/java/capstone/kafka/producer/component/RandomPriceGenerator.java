package capstone.kafka.producer.component;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import capstone.kafka.producer.model.PriceChange;
import capstone.kafka.producer.serde.PriceChangeSerde;

@Component
public class RandomPriceGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomPriceGenerator.class);

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Value("${price.topic:PriceChangeTopic}")
    private String heartbeatTopic;

    @Scheduled(fixedRate = 10000)
    public void produce() {
        ListenableFuture<SendResult<String, byte[]>> future;
        Random random = new Random();
        PriceChange priceChange = new PriceChange("APPL", random.nextDouble());
        String json = PriceChangeSerde.INSTANCE.serialize(priceChange);
        future = kafkaTemplate.send(heartbeatTopic, json.getBytes(Charset.forName("UTF-8")));
        try {
            SendResult<String, byte[]> result = future.get();
            LOGGER.info("Successfully added to offset: " + result.getRecordMetadata().offset());
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Unable to process heartbeat.", e);
        }
    }

}
