package demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

	public static void main(String[] args) {
		new Server().startServer();
	}
	public void startServer(){
		try {
			Select select = new Select();
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.socket().bind(new InetSocketAddress(7878));
			server.register(select.getSelector(), SelectionKey.OP_ACCEPT);
			new Thread(select).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class Select implements Runnable{
		private Selector selector;
		Select() throws IOException{
			this.selector = Selector.open();
		}
		public Selector getSelector(){
			return selector;
		}
		@Override
		public void run() {
			while(true){
				try {
					this.selector.select();
					Set<SelectionKey> keys = this.selector.selectedKeys();
					Iterator<SelectionKey> key = keys.iterator();
					while(key.hasNext()){
						SelectionKey k = key.next();
						this.dispatch(k);
						key.remove();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		public void dispatch(SelectionKey key){
			if(key.isAcceptable()){
				try {
					ServerSocketChannel channel = (ServerSocketChannel) key.channel();
					SocketChannel sc = channel.accept();
					sc.configureBlocking(false);
					sc.register(new Select().getSelector() , SelectionKey.OP_READ);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(key.isReadable()){
				try {
					ByteBuffer buff = ByteBuffer.allocate(1024);
					SocketChannel sc = (SocketChannel) key.channel();
					int count = sc.read(buff);
	                if (count < 0) {
	                    key.cancel();
	                    sc.close();
	                    return;
	                }
					buff.flip();
					System.out.println(new String(buff.array())+"  "+sc.getRemoteAddress());
					buff.clear();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
