package Server;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * Created by admin-iorigins on 15.12.16.
 */
public class GrupConnection extends Connection {
    private Grup grup;
    private PrivateConnection privateConnection;
    private DataGrup data = (DataGrup) super.data;

    public GrupConnection(Data data, SelectionKey selectionKey, String name) {
        super(data, selectionKey, name);

        addClient(selectionKey);
    }

    public void addClient(SelectionKey key) {
        if (grup.isMember(name)) {
            data.put(key);
        }
    }

    public void removeClient(SelectionKey key) {
        if (grup.isMember(name)) {
            data.remove(key);
        }
    }

    public boolean isEntry() {
        return data.mapOut.size() == 0 ? true : false;
    }

    @Override
    public ByteBuffer read() {
        return privateConnection.read();
    }

    @Override
    public ByteBuffer write() {
        return privateConnection.write();
    }
}
