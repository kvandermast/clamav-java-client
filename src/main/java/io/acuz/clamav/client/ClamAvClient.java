package io.acuz.clamav.client;

import io.acuz.clamav.client.dto.ScanResult;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;

public interface ClamAvClient {
    Collection<String> availableCommands();

    String version();

    boolean ping();

    boolean reload();

    boolean shutdown();

    Collection<String> stats();

    ScanResult scan(InputStream inputStream);

    ScanResult scan(InputStream inputStream, int chunkSize);

    ScanResult scan(Path path);

    ScanResult scan(Path path, boolean continueScan);

    ScanResult parallelScan(Path path);
}
