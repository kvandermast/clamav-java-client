package io.acuz.clamav.client.command;

public enum Command {
    VERSION("VERSION", Format.NULL_CHAR),
    PING("PING", Format.NULL_CHAR),
    RELOAD("RELOAD", Format.NULL_CHAR),
    SHUTDOWN("SHUTDOWN", Format.NULL_CHAR),
    STATS("STATS", Format.NEW_LINE),
    AVAILABLE_COMMANDS("VERSIONCOMMANDS", Format.NEW_LINE),
    CONTINUE_SCAN("CONTSCAN", Format.NEW_LINE),
    INPUT_STREAM_SCAN("INSTREAM", Format.NULL_CHAR),
    MULTI_SCAN("MULTISCAN", Format.NEW_LINE),
    SCAN("SCAN", Format.NULL_CHAR)
    ;


    private final String clamAvCommand;
    private final Format format;

    Command(String command, Format format) {
        this.clamAvCommand = command;
        this.format = format;
    }

    public String getClamAvCommand() {
        return clamAvCommand;
    }

    public Format getFormat() {
        return format;
    }
}
