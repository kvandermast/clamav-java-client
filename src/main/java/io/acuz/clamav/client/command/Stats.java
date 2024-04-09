package io.acuz.clamav.client.command;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public final class Stats extends AbstractCommand<Collection<String>> {
    @Override
    Command command() {
        return Command.STATS;
    }

    @Override
    Collection<String> parse(String response) {
        return Arrays.stream(response.split("\n"))
                .filter(StringUtils::hasText)
                .toList();
    }
}
