package nvh.run.ideaswap.exceptions;

public enum ErrorCode {
    USER_NOT_FOUND("USER-001", "User not found"),
    EMAIL_ALREADY_EXISTS("USER-002", "Email already exists"),
    // Các lỗi khác...
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

