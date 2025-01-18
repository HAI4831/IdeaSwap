package nvh.run.authsystemgradle.common.exceptions.exception.custom.auth;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
