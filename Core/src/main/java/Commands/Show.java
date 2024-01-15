package Commands;

import Managers.CollectionManager;
import Managers.Container;

import java.nio.channels.SocketChannel;

public class Show extends AbstractCommand {
    @Override
    public Container execute(Container container, SocketChannel socketChannel) {
        System.out.println(CollectionManager.getSessionHashMap(socketChannel));
        return new Container(false, "Your hashmap:", CollectionManager.getSessionHashMap(socketChannel));


    }

}
