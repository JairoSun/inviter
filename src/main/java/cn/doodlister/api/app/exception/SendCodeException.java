package cn.doodlister.api.app.exception;

public class SendCodeException extends Exception {
    public SendCodeException() {
    }

    public SendCodeException(String message) {
        super(message);
    }
}
