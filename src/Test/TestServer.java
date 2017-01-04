package Test;

import Server.Connection;
import Server.Data;
import Server.PrivateConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin-iorigins on 16.12.16.
 */
public class TestServer  {
    public int port;

    public TestServer(int port) {
        this.port = port;
    }

    public void start() throws IOException, InterruptedException {

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));

        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        int att = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

        while (true) {
            System.out.println("acept0");
            SocketChannel accept1 = null;
            while ((accept1 = serverSocketChannel.accept()) == null) {

            }
            System.out.println("acept1");

            System.out.println("acept0");
            SocketChannel accept2 = null;
            while ((accept2 = serverSocketChannel.accept()) == null) {

            }
            System.out.println("acept1");

            accept1.configureBlocking(false);
            accept2.configureBlocking(false);

            SelectionKey register1 = accept1.register(selector, att);
            SelectionKey register2 = accept2.register(selector, att);

            Data data = new Data(register1, register2);

            PrivateConnection name1 = new PrivateConnection(data, register1, "name1");
            PrivateConnection name2 = new PrivateConnection(data, register2, "name2");


            register1.attach(name1);
            register2.attach(name2);


            while (true) {
                for (int i = 0; i == 0; i = selector.select()) {
                    System.out.println("i");

                    TimeUnit.SECONDS.sleep(1);
                }
                System.out.println("run");

                Set<SelectionKey> keys = selector.selectedKeys();


                for (Iterator<SelectionKey> iterator=keys.iterator();iterator.hasNext();) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                 Connection connection= (Connection) selectionKey.attachment();

                    Data data1 = connection.getData();



                    if (selectionKey.isReadable()) {
                        System.out.println("read0");
                        ByteBuffer read = connection.read();
                        System.out.println("read1");


                    } else if (selectionKey.isWritable()) {
                        System.out.println("write0");
                        ByteBuffer write = connection.write();
                        System.out.println("write1");

                        if (write == null) {
                            continue;
                        }

                    }

                }


                System.out.println("sleep0");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("sleep1");
            }
        }
    }
}
