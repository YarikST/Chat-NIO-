package Server;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.LinkedList;

/**
 * Created by admin-iorigins on 16.12.16.
 */
public class DataGrup extends Data {


        public DataGrup(SelectionKey ...keys) {
           super(keys);
        }


    public synchronized void put(SelectionKey key) {
        LinkedList<ByteBuffer> list = mapOut.get(key);
        list.add(Data.getBuffer());

        }

        public synchronized void remove(SelectionKey key) {
            mapIn.remove(key);
            mapOut.remove(key);
        }

}
