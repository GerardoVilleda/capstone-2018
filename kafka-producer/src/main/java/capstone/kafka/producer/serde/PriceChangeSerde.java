package capstone.kafka.producer.serde;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import capstone.kafka.producer.exception.JSONException;
import capstone.kafka.producer.model.PriceChange;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum PriceChangeSerde {

    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceChangeSerde.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    PriceChangeSerde() {
        this.objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public PriceChange deserialize(final String message) {
        try {
            return objectMapper.readValue(message, PriceChange.class);
        } catch (IOException e) {
            LOGGER.error("Failed to deserialize PriceChange message.", e);
            throw new JSONException("Failed to deserialize message", e);
        }
    }

    public PriceChange deserialize(final byte[] message) {
        try {
            return objectMapper.readValue(message, PriceChange.class);
        } catch (IOException e) {
            LOGGER.error("Failed to deserialize PriceChange message.", e);
            throw new JSONException("Failed to deserialize message", e);
        }
    }

    public String serialize(final PriceChange priceChange) {
        try {
            return this.objectMapper.writeValueAsString(priceChange);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize PriceChange object.", e);
            throw new JSONException("Failed to serialize object", e);
        }
    }
}
