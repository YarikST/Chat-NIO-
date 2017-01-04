package Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by admin-iorigins on 15.12.16.
 */
public class PrivateConnection extends Connection {


    public PrivateConnection(Data data, SelectionKey selectionKey, String name) {
        super(data, selectionKey, name);
    }

    @Override
    public ByteBuffer read() {
        ByteBuffer buffer = data.getOut(selectionKey);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        try {
            socketChannel.read(buffer);
            data.out(selectionKey,buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer;
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer buffer = data.getIn(selectionKey);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        if (buffer == null) {
            return buffer;
        }

        try {
            socketChannel.write(buffer);
           data.in(selectionKey, buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer;
    }
}
