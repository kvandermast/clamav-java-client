package io.acuz.clamav.client.exception;

public class UnknownCommandException extends ClamAvClientException {
    public UnknownCommandException() {
        super(new IllegalStateException("Unknown command triggered"));
    }
}
