package lk.ijse.exception;

public class ItemNotFountException extends RuntimeException{
    public ItemNotFountException() {
        super();
    }

    public ItemNotFountException(String message) {
        super(message);
    }

    public ItemNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
