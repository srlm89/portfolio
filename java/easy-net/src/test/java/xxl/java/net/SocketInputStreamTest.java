package xxl.java.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.net.EasySocket.openLocalServerSocket;
import static xxl.java.net.EasySocket.openLocalSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class SocketInputStreamTest {

	@Test
	public void closingDoesNotCloseTheSocket() throws IOException {
		ServerSocket serverSocket = openLocalServerSocket(1);
		Socket socket = openLocalSocket(serverSocket.getLocalPort());
		SocketInputStream stream = new SocketInputStream(socket);
		assertFalse(socket.isClosed());
		stream.close();
		assertFalse(socket.isClosed());
		serverSocket.close();
		socket.close();
		assertTrue(socket.isClosed());
	}
	
	@Test
	public void gettingBytesSentToSocket() throws IOException {
		ServerSocket serverSocket = openLocalServerSocket(1);
		Socket localSocket = openLocalSocket(serverSocket.getLocalPort());
		Socket remoteSocket = serverSocket.accept();
		SocketInputStream localStream = new SocketInputStream(localSocket);
		OutputStream outputStream = remoteSocket.getOutputStream();
		outputStream.write("wazzoo".getBytes());
		outputStream.flush();
		byte[] bytes = new byte[6];
		assertEquals(6, localStream.available());
		assertEquals(6, localStream.read(bytes));
		assertEquals("wazzoo", new String(bytes));
		localStream.close();
		remoteSocket.close();
		localSocket.close();
		serverSocket.close();
	}
}
