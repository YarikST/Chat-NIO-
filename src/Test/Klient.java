package Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin-iorigins on 16.12.16.
 */
public class Klient {

    public int port1,port2;
    private String string;
    private boolean is;

    public Klient(int port1, int port2, String string, boolean is) {
        this.port1 = port1;
        this.port2 = port2;
        this.string = string;
        this.is = is;
    }

    public void start() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.bind(new InetSocketAddress(port1));
        socketChannel.connect(new InetSocketAddress(port2));

        ByteBuffer buffer;
        if(is) {

            buffer = ByteBuffer.allocate(25);
            socketChannel.read(buffer);
            buffer.flip();

            CharBuffer charBuffer = Charset.forName("utf-8").decode(buffer);
            System.out.println(charBuffer);
        }else {
            buffer = ByteBuffer.wrap(string.getBytes("utf-8"));
            socketChannel.write(buffer);

            TimeUnit.SECONDS.sleep(20);
        }

        socketChannel.close();
    }
}
