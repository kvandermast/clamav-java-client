package io.acuz.clamav.client.exception;

public class CommunicationException extends ClamAvClientException {
    public CommunicationException(Exception e) {
        super(e);
    }
}
