package io.acuz.clamav.client.exception;

public class InvalidResponseException extends  ClamAvClientException {

    public InvalidResponseException(String response) {
        super(new IllegalStateException("Invalid response returned; " + response));
    }
}
