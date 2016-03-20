package xxl.java.net;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.net.EasySocket.openLocalServerSocket;
import static xxl.java.net.EasySocket.openLocalSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class SocketOutputStreamTest {

	@Test
	public void closingDoesNotCloseTheSocket() throws IOException {
		ServerSocket serverSocket = openLocalServerSocket(1);
		Socket socket = openLocalSocket(serverSocket.getLocalPort());
		SocketOutputStream stream = new SocketOutputStream(socket);
		assertFalse(socket.isClosed());
		stream.close();
		assertFalse(socket.isClosed());
		serverSocket.close();
		socket.close();
		assertTrue(socket.isClosed());
	}
}
