package Test;

import java.io.IOException;

/**
 * Created by admin-iorigins on 16.12.16.
 */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
     // new TestServer(1080).start();
      //new Klient(1070, 1080,"1\n",false).start();
      new Klient(1071, 1080,"2\n",true).start();

        /*ByteBuffer wrap = ByteBuffer.wrap("\n".getBytes());

        System.out.println(wrap.get(wrap.limit()-1));
*/
    }
}
