package io.acuz.clamav.client;

import io.acuz.clamav.client.command.AbstractScanCommand;
import io.acuz.clamav.client.command.ContinuousScan;
import io.acuz.clamav.client.command.InputStreamScan;
import io.acuz.clamav.client.command.Ping;
import io.acuz.clamav.client.command.Reload;
import io.acuz.clamav.client.command.Scan;
import io.acuz.clamav.client.command.Shutdown;
import io.acuz.clamav.client.command.Stats;
import io.acuz.clamav.client.command.Version;
import io.acuz.clamav.client.command.VersionCommands;
import io.acuz.clamav.client.dto.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.Collection;

public class ClamAvClientImpl implements ClamAvClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClamAvClientImpl.class);

    private final InetSocketAddress server;

    public ClamAvClientImpl(InetSocketAddress server) {
        this.server = server;
    }


    @Override
    public Collection<String> availableCommands() {
        var command = new VersionCommands();
        return command.writeTo(server);
    }

    @Override
    public String version() {
        var command = new Version();
        return command.writeTo(server);
    }

    @Override
    public boolean ping() {
        var command = new Ping();

        return command.writeTo(server);
    }

    @Override
    public boolean reload() {
        var command = new Reload();
        return command.writeTo(server);
    }

    @Override
    public boolean shutdown() {
        var command = new Shutdown();
        return command.writeTo(server);
    }

    @Override
    public Collection<String> stats() {
        var command = new Stats();
        return command.writeTo(server);
    }

    @Override
    public ScanResult scan(InputStream inputStream) {
        return scan(inputStream, InputStreamScan.DEFAULT_CHUNK_SIZE);
    }

    @Override
    public ScanResult scan(InputStream inputStream, int chunkSize) {
        var command = new InputStreamScan(inputStream, chunkSize);

        return command.writeTo(server);
    }

    @Override
    public ScanResult scan(Path path) {
        return scan(path, false);
    }


    @Override
    public ScanResult scan(Path path, boolean continueScan) {
        AbstractScanCommand command;

        if (continueScan) {
            command = new ContinuousScan(path);
        } else {
            command = new Scan(path);
        }

        return command.writeTo(server);
    }

    @Override
    public ScanResult parallelScan(Path path) {
        return null;
    }


    public void destroy() {
        LOGGER.info("Triggering destroy method");
    }
}
