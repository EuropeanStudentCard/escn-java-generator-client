package eu.escn.generator.client.exceptions;

public class EscnClientException extends RuntimeException {

    public EscnClientException(String message) {
        super(message);
    }

    public EscnClientException(Exception e) {
        super(e);
    }

}
