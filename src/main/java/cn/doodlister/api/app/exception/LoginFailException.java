package cn.doodlister.api.app.exception;

/**
 * 登陆失效
 */
public class LoginFailException extends Exception {
    public LoginFailException() {
    }

    public LoginFailException(String message) {
        super(message);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
