package io.acuz.clamav.client.exception;

public class ScanFailureException extends ClamAvClientException{
    public ScanFailureException(String response) {
        super(new IllegalStateException("Failed to scan files; " + response));
    }
}
