package io.acuz.clamav.client.exception;

public class ClamAvClientException extends RuntimeException {
    public ClamAvClientException(Exception e) {
        super(e);
    }
}
