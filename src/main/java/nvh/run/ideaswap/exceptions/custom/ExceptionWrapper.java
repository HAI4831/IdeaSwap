package nvh.run.ideaswap.exceptions.custom;

import java.util.function.Supplier;

public class ExceptionWrapper {
    public static <R> R RuntimeWrapper(Supplier<R> func, String errorMessage) {
        try {
            return func.get();
        } catch (Exception ex) {
            throw new RuntimeException(errorMessage, ex);
        }
    }
}