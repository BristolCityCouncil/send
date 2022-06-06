package uk.gov.bristol.send;

public class SENDException extends RuntimeException {

    public SENDException (Throwable ex) {
        super(ex);
    }

    public SENDException (String message) {
        super(message);
    }

    public SENDException (String errorMsg, Throwable ex) {
        super(errorMsg, ex);
    }
}
