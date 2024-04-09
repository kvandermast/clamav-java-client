package io.acuz.clamav.client.command;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ContinuousScan extends AbstractScanCommand {
    private final Path path;

    public ContinuousScan(Path path) {
        this.path = path;
    }

    @Override
    Command command() {
        return Command.CONTINUE_SCAN;
    }

    @Override
    protected ByteBuffer rawCommand() {
        var raw = String.format("%c%s %s%c",
                command().getFormat().getPrefixChar(),
                command().getClamAvCommand(),
                path,
                command().getFormat().getTerminatorChar());

        return ByteBuffer.wrap(raw.getBytes(StandardCharsets.UTF_8));
    }
}
