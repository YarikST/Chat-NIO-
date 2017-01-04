package Server;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * Created by admin-iorigins on 13.12.16.
 */
public abstract class Connection {
    protected Data data;

    protected SelectionKey selectionKey;

    protected String name;

    public Connection(Data data, SelectionKey selectionKey, String name) {
        this.data = data;
        this.selectionKey = selectionKey;
        this.name = name;
    }

    public abstract ByteBuffer read() ;

    public abstract ByteBuffer write();

    public Data getData() {
        return data;
    }

    public String getName() {
        return name;
    }
}
