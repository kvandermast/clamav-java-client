package io.acuz.clamav.client.command;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class MultiScan extends AbstractScanCommand {
    private final String path;

    public MultiScan(String path) {
        this.path = path;
    }

    @Override
    Command command() {
        return Command.MULTI_SCAN;
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
