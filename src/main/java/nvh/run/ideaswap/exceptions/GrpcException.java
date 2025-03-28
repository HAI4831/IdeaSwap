package nvh.run.ideaswap.exceptions;

public class GrpcException extends RuntimeException {
    public GrpcException(String message) {
        super(message);
    }
    public GrpcException(String message, Throwable cause) {
      super(message, cause);
    }
}
