package xxl.java.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


/**
 * A basic implementation of a {@code Client}. To instantiate a client, you should provide the {@code address}
 * of the server and the {@code port} where it listens.<br>
 * A client is a {@link SocketEndpoint} which is able to send and receive data through a {@code Socket}.
 */
public class Client extends SocketEndpoint  {
	
	public Client(InetAddress address, int port) throws IOException {
		super(new Socket(address, port));
	}
	
	public Client(Socket socket) throws IOException {
		super(socket);
	}
	
}
