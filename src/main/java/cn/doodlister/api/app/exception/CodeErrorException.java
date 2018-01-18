package cn.doodlister.api.app.exception;

/**
 * 验证码错误
 */
public class CodeErrorException extends Exception {
    public CodeErrorException() {
    }

    public CodeErrorException(String message) {
        super(message);
    }

    public CodeErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
