package nvh.run.ideaswap.exceptions;

import java.io.Serial;

public class CommonException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 944218762645229018L;
//    private final WithHttpStatusCode errorKey;
//    private final transient Object[] args;

    public CommonException(String message) {
        super(message);
    }
}
