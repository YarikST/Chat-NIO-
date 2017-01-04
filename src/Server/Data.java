package Server;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by admin-iorigins on 15.12.16.
 */
public class Data {
    protected HashMap<SelectionKey ,LinkedList<ByteBuffer>>mapIn, mapOut;

    public Data(SelectionKey ...keys) {
        mapIn = new HashMap<>(keys.length);
        mapOut = new HashMap<>(keys.length);

        for (SelectionKey selectionKey : keys) {
            LinkedList<ByteBuffer> objects = new LinkedList<>();
            objects.add(getBuffer());
            mapOut.put(selectionKey, objects);
            mapIn.put(selectionKey, new LinkedList<>());
        }
    }

    public synchronized ByteBuffer getIn(SelectionKey key) {
        LinkedList<ByteBuffer> remove = mapIn.get(key);


        return remove.poll();
    }

    public synchronized  ByteBuffer  getOut(SelectionKey key) {
        LinkedList<ByteBuffer> remove = mapOut.get(key);
        ByteBuffer pop = remove.poll();
        if (pop == null) {
            pop = getBuffer();
        }
        return pop;
    }

    public synchronized void in(SelectionKey key, ByteBuffer buffer) {
        if (buffer.position() != buffer.limit()) {

            LinkedList<ByteBuffer> remove = mapIn.get(key);

            remove.add(buffer);
        }
    }

    public synchronized void out(SelectionKey key, ByteBuffer buffer) {
        if (buffer.position()!=0 && buffer.get(buffer.position() - 1) == '\n') {

            buffer.flip();

            for(Iterator<SelectionKey> iterator = mapIn.keySet().iterator();iterator.hasNext();) {
                SelectionKey next = iterator.next();

                LinkedList<ByteBuffer> list = mapIn.get(next);

                list.add(ByteBuffer.wrap(buffer.array()));
            }

        } else {
            buffer.flip();
            LinkedList<ByteBuffer> list = mapOut.get(key);
            list.add(buffer);
        }
    }

    public static ByteBuffer getBuffer() {
        return ByteBuffer.allocate(1024);
    }
}
