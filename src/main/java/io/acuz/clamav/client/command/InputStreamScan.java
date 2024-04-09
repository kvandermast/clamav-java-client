package io.acuz.clamav.client.command;

import io.acuz.clamav.client.dto.ScanResult;
import io.acuz.clamav.client.exception.CommunicationException;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

public class InputStreamScan extends AbstractScanCommand {
    public static final int DEFAULT_CHUNK_SIZE = 2048;

    private final InputStream inputStream;
    private int chunkSize = DEFAULT_CHUNK_SIZE;

    public InputStreamScan(InputStream inputStream, int chunkSize) {
        this.inputStream = inputStream;
        this.chunkSize = chunkSize;
    }

    @Override
    Command command() {
        return Command.INPUT_STREAM_SCAN;
    }

    @Override
    public ScanResult writeTo(InetSocketAddress server) {
        try (var channel = SocketChannel.open(server)) {
            channel.write(rawCommand());

            var length = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
            var data = new byte[chunkSize];
            var cz = chunkSize;

            while (cz != -1) {
                cz = inputStream.read(data);

                if (cz > 0) {
                    length.clear();
                    length.putInt(cz).flip();
                    channel.write(length);
                    channel.write(ByteBuffer.wrap(data, 0, cz));
                }
            }

            length.clear();
            length.putInt(0).flip();
            channel.write(length);

            return read(channel);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }
}
