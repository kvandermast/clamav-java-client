package io.acuz.clamav.client.command;

import io.acuz.clamav.client.exception.InvalidResponseException;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public final class VersionCommands extends AbstractCommand<Collection<String>> {
    private static final String COMMANDS_START_TAG = "| COMMANDS:";

    @Override
    Command command() {
        return Command.AVAILABLE_COMMANDS;
    }

    @Override
    Collection<String> parse(String response) {
        if (!StringUtils.hasText(response) || !response.contains(COMMANDS_START_TAG)) {
            throw new InvalidResponseException(response);
        }

        return Arrays.stream(response
                        .substring(response.indexOf(COMMANDS_START_TAG) + COMMANDS_START_TAG.length())
                        .split(" "))
                .filter(StringUtils::hasText)
                .toList();
    }
}
