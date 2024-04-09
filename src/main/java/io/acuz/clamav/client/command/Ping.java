package io.acuz.clamav.client.command;

import io.acuz.clamav.client.exception.InvalidResponseException;

public final class Ping extends AbstractCommand<Boolean> {
    @Override
    Command command() {
        return Command.PING;
    }

    @Override
    Boolean parse(String response) {
        if (!"PONG".equals(response)) {
            throw new InvalidResponseException(response);
        }

        return Boolean.TRUE;
    }
}
