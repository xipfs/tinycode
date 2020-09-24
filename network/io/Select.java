import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * description: Select <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/24 15:05 <br>
 */
public class Select {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();//管道型ServerSocket
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8080));
        ssc.configureBlocking(false);//设置非阻塞
        System.out.println(" NIO single server started, listening on :" + ssc.getLocalAddress());
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);//在建立好的管道上，注册关心的事件 就绪
        while(true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while(it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();//处理的事件，必须删除
                handle(key);
            }
        }
    }
    private static void handle(SelectionKey key) throws IOException {
        if(key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);//设置非阻塞
            sc.register(key.selector(), SelectionKey.OP_READ );//在建立好的管道上，注册关心的事件 可读
        } else if (key.isReadable()) { //flip
            SocketChannel sc = null;
            sc = (SocketChannel)key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(512);
            buffer.clear();
            int len = sc.read(buffer);
            if(len != -1) {
                System.out.println("[" +Thread.currentThread().getName()+"] recv :"+ new String(buffer.array(), 0, len));
            }
            ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
            sc.write(bufferToWrite);
        }
    }
}
