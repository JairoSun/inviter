package cn.doodlister.api.app.exception;

/**
 * 已经绑定过
 */
public class BindInviteException extends Exception {
    public BindInviteException() {
    }

    public BindInviteException(String message) {
        super(message);
    }

    public BindInviteException(String message, Throwable cause) {
        super(message, cause);
    }
}
