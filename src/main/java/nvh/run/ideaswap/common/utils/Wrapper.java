package nvh.run.ideaswap.common.utils;

import java.util.function.Supplier;

public class Wrapper {
    public static <R> R wrapperRuntimeException(Supplier<R> supplier, String messageError) {
        try {
            return supplier.get();
        } catch (RuntimeException e) {
            throw new RuntimeException(messageError, e);
        }
    }
}
