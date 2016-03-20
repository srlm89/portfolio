package xxl.java.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.net.EasySocket.ipV4;
import static xxl.java.net.EasySocket.openLocalServerSocket;
import static xxl.java.net.EasySocket.openLocalSocket;
import static xxl.java.net.EasySocket.openServerSocket;
import static xxl.java.net.EasySocket.openSocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.junit.Test;

public class EasySocketTest {

	@Test
	public void openingServerSocket() throws IOException {
		int limit = 1;
		byte[] localIp = new byte[] {127,0,1,1};
		InetAddress localAddress = ipV4(localIp);
		int freePort = checkServerSocket(openLocalServerSocket(limit), localIp, 0);
		checkServerSocket(openServerSocket(localIp, limit), localIp, 0);
		checkServerSocket(openServerSocket(localAddress, limit), localIp, 0);
		checkServerSocket(openLocalServerSocket(freePort, limit), localIp, freePort);
		checkServerSocket(openServerSocket(localIp, freePort, limit), localIp, freePort);
		checkServerSocket(openServerSocket(localAddress, freePort, limit), localIp, freePort);
	}
	
	@Test
	public void checkCreatedSocket() throws IOException {
		byte[] localIp = new byte[] {127,0,1,1};
		InetAddress localAddress = ipV4(localIp);
		ServerSocket socket = openLocalServerSocket(1);
		int port = socket.getLocalPort();
		checkSocket(openLocalSocket(port), localIp, port);
		checkSocket(openSocket(localIp, port), localIp, port);
		checkSocket(openSocket(localAddress, port), localIp, port);
		socket.close();
		assertTrue(socket.isClosed());
	}
	
	private int checkServerSocket(ServerSocket socket, byte[] ip, int port) throws IOException {
		assertTrue(Arrays.equals(new byte[] {127,0,1,1}, ip));
		assertFalse(socket.isClosed());
		assertTrue(socket.isBound());
		if (port != 0) {
			assertEquals(port, socket.getLocalPort());
		}
		socket.close();
		assertTrue(socket.isClosed());
		return socket.getLocalPort();
	}
	
	private int checkSocket(Socket socket, byte[] ip, int port) throws IOException {
		assertTrue(Arrays.equals(new byte[] {127,0,1,1}, ip));
		assertFalse(socket.isClosed());
		assertTrue(socket.isBound());
		if (port != 0) {
			assertEquals(port, socket.getPort());
		}
		socket.close();
		assertTrue(socket.isClosed());
		return socket.getLocalPort();
	}
}
