package io.acuz.clamav.client.command;

import io.acuz.clamav.client.exception.ClamAvClientException;
import io.acuz.clamav.client.exception.UnknownCommandException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public abstract class AbstractCommand<T> {

    abstract Command command();

    abstract T parse(String response);

    protected ByteBuffer rawCommand() {
        var raw = String.format("%c%s%c",
                command().getFormat().getPrefixChar(),
                command().getClamAvCommand(),
                command().getFormat().getTerminatorChar());

        return ByteBuffer.wrap(raw.getBytes(StandardCharsets.UTF_8));
    }

    public T writeTo(InetSocketAddress server) {
        try (var channel = SocketChannel.open(server)) {

            channel.write(rawCommand());
            return read(channel);
        } catch (Exception e) {
            throw new ClamAvClientException(e);
        }
    }

    protected T read(SocketChannel channel) throws IOException {
        var readByteBuffer = ByteBuffer.allocate(32);
        var responseByteArray = new byte[0];
        var readSize = channel.read(readByteBuffer);

        while (readSize > -1) {
            var readByteArray = readByteBuffer.array();

            if (readSize < 32) {
                var tempByteArray = new byte[readSize];
                System.arraycopy(readByteArray, 0, tempByteArray, 0, readSize);
                readByteArray = tempByteArray;
            }

            var swap = new byte[responseByteArray.length + readSize];
            if (responseByteArray.length > 0) {
                // copy the read bytes to the swap - copy existing values
                System.arraycopy(responseByteArray, 0, swap, 0, responseByteArray.length);
            }
            // copy the next slice to the swp
            System.arraycopy(readByteArray, 0, swap, swap.length - readSize, readSize);
            responseByteArray = swap;
            readByteBuffer = ByteBuffer.allocate(32);
            readSize = channel.read(readByteBuffer);
        }

        var response = removeResponseTerminator(new String(responseByteArray));

        if ("UNKNOWN COMMAND".equals(response)) {
            throw new UnknownCommandException();
        }

        return parse(response);
    }

    private String removeResponseTerminator(String response) {
        var index = response.lastIndexOf(command().getFormat().getTerminatorChar());

        if (index > 0)
            return response.substring(0, index);

        return response;
    }
}
