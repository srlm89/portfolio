package xxl.java.net;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.net.EasySocket.openLocalServerSocket;

import java.net.ServerSocket;

import org.junit.Test;

import xxl.java.net.offline.HelloServerWorker;

public class ServerWorkerTest {

	@Test
	public void closingClientTearsDownWorker() throws Exception {
		ServerSocket socket = openLocalServerSocket(1);
		Client client = new Client(socket.getInetAddress(), socket.getLocalPort());
		ServerWorker worker = HelloServerWorker.factory().workerFor(socket.accept());
		Thread thread = new Thread(worker);
		thread.start();
		assertFalse(worker.isClosed());
		client.close();
		while (thread.isAlive()) {/* wait */}
		assertTrue(worker.isClosed());
	}
}
