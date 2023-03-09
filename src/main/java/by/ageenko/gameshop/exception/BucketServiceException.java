package by.ageenko.gameshop.exception;

public class BucketServiceException extends Exception{
    public BucketServiceException() {
    }

    public BucketServiceException(String message) {
        super(message);
    }

    public BucketServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BucketServiceException(Throwable cause) {
        super(cause);
    }
}
