package nvh.run.ideaswap.exceptions;

public class MapException extends RuntimeException {
    public MapException(String message) {
        super(message);
    }
    public MapException(String message, Throwable cause) {
        super(message, cause);
    }
}
