package io.acuz.clamav.client.command;

public final class Version extends AbstractCommand<String> {
    @Override
    Command command() {
        return Command.VERSION;
    }

    @Override
    String parse(String response) {
        return response;
    }
}
