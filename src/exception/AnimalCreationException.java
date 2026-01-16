package exception;

public class AnimalCreationException extends RuntimeException {
    public AnimalCreationException(String message, Throwable e) {
        super(message, e);
    }
}
