package capstone.kafka.producer.exception;

public class JSONException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JSONException(String message, Exception e) {
        super(message, e);
    }
}
