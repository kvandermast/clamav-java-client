package io.acuz.clamav.client.command;

/**
 * <a href="https://linux.die.net/man/8/clamd">https://linux.die.net/man/8/clamd</a>
 */
public enum Format {
    NULL_CHAR('z', '\u0000'),
    NEW_LINE('n', '\n');

    private final char terminatorChar;
    private final char prefixChar;

    Format(char prefix, char terminator) {
        this.prefixChar = prefix;
        this.terminatorChar = terminator;
    }

    public char getTerminatorChar() {
        return terminatorChar;
    }

    public char getPrefixChar() {
        return prefixChar;
    }
}
