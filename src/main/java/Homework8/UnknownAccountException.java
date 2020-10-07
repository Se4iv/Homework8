package Homework8;

public class UnknownAccountException extends Exception {
    public UnknownAccountException() {
    }

    public UnknownAccountException(String message) {
        super(message);
    }
}