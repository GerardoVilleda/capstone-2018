package capstone.kafka.producer.component;

import java.nio.charset.Charset;
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

@Component
public class ProducerHeartbeat {

    private static final Logger log = LoggerFactory.getLogger(ProducerHeartbeat.class);

    @Value("${heartbeat.topic:HeartbeatTopic}")
    private String heartbeatTopic;

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Scheduled(fixedRate = 10000)
    public void beat() {
        String message = "Sample message: tick-tock...";
        log.info(message);
        ListenableFuture<SendResult<String, byte[]>> future;
        future = kafkaTemplate.send(heartbeatTopic, message.getBytes(Charset.forName("UTF-8")));
        try {
            SendResult<String, byte[]> result = future.get();
            log.info("Successfully added to offset: " + result.getRecordMetadata().offset());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Unable to process heartbeat.", e);
        }
    }
}
