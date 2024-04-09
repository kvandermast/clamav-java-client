package io.acuz.clamav.client.command;

public final class Shutdown extends AbstractCommand<Boolean> {
    @Override
    Command command() {
        return Command.SHUTDOWN;
    }

    @Override
    Boolean parse(String response) {
        return Boolean.TRUE;
    }
}
