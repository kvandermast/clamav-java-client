package io.acuz.clamav.client.command;

import io.acuz.clamav.client.exception.InvalidResponseException;

public final class Reload extends AbstractCommand<Boolean> {
     @Override
    Command command() {
        return Command.RELOAD;
    }

    @Override
    Boolean parse(String response) {
        if (!"RELOADING".equals(response))
            throw new InvalidResponseException(response);

        return Boolean.TRUE;
    }
}
