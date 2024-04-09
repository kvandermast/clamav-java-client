package io.acuz.clamav.client.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScanResult {
    public static final ScanResult FAILED = new ScanResult(ScanStatus.ERROR);
    private Map<String, Set<String>> results;
    private ScanStatus status;

    @SuppressWarnings("unused")
    public ScanResult() {
        // default constructor for the serialization
    }

    private ScanResult(ScanStatus status) {
        this.status = status;
        this.results = Map.of();
    }

    private ScanResult(Builder builder) {
        this.results = Map.copyOf(builder.scanResults);

        var found = results.entrySet()
                .stream()
                .anyMatch(e -> !e.getValue().isEmpty());

        this.status = found ? ScanStatus.VIRUS_FOUND : ScanStatus.OK;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<String, Set<String>> getResults() {
        return results;
    }

    public void setResults(Map<String, Set<String>> results) {
        this.results = results;
    }

    public ScanStatus getStatus() {
        return status;
    }

    public void setStatus(ScanStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScanResult{" +
                "scanResults=" + results +
                ", result=" + status +
                '}';
    }

    public enum ScanStatus {
        OK,
        VIRUS_FOUND,
        ERROR,
    }

    public static class Builder {
        private final Map<String, Set<String>> scanResults = new HashMap<>();

        public Builder add(String resource, String virus) {
            if (scanResults.containsKey(resource)) {
                scanResults.get(resource).add(virus);
            } else {
                scanResults.put(resource, Set.of(virus));
            }

            return this;
        }

        public ScanResult build() {
            return new ScanResult(this);
        }
    }
}
