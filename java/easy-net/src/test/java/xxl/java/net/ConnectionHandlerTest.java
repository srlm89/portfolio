package xxl.java.net;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.net.EasySocket.openLocalServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Test;

public class ConnectionHandlerTest {

	@Test
	public void finishIfSocketClosed() throws IOException {
		ServerSocket socket = openLocalServerSocket(1);
		ConnectionHandler handler = new ConnectionHandler(null);
		handler.setServerSocket(socket);
		Thread thread = new Thread(handler);
		thread.start();
		assertFalse(handler.finished());
		socket.close();
		assertTrue(handler.finished());
		while (thread.isAlive()) { /* wait */}
		assertFalse(thread.isAlive());
	}

	@Test
	public void finishIfIsInterrupted() throws IOException {
		ServerSocket socket = openLocalServerSocket(1);
		ConnectionHandler handler = new ConnectionHandler(null);
		handler.setServerSocket(socket);
		Thread thread = new Thread(handler);
		thread.start();
		assertFalse(handler.finished());
		handler.interrupt();
		assertTrue(handler.isInterrupted());
		assertTrue(handler.finished());
		while (thread.isAlive()) { /* wait */}
		assertFalse(thread.isAlive());
		socket.close();
	}
}
