package xxl.java.net;

import static org.junit.Assert.*;
import static xxl.java.net.EasySocket.openLocalServerSocket;

import java.net.ServerSocket;
import java.net.SocketException;

import org.junit.Test;

import xxl.java.net.offline.IdleServerWorker;

public class SocketEndpointTest {

	@Test
	public void sendAndReceiveWithSockets() throws Exception {
		ServerSocket socket = openLocalServerSocket(1);
		Client client = new Client(socket.getInetAddress(), socket.getLocalPort());
		ServerWorker worker = IdleServerWorker.factory().workerFor(socket.accept());

		client.send(1910);
		assertEquals(1910, worker.receive());

		assertFalse(worker.isClosed());
		assertFalse(client.isClosed());

		worker.send("1910");
		assertEquals("1910", client.receive());

		assertFalse(worker.isClosed());
		assertFalse(client.isClosed());

		client.close();
		worker.close();
		assertTrue(worker.isClosed());
		assertTrue(client.isClosed());
		socket.close();
	}

	@Test
	public void sendMethodIsNonBlocking() throws Exception {
		ServerSocket socket = openLocalServerSocket(1);
		Client client = new Client(socket.getInetAddress(), socket.getLocalPort());
		client.send(98765);
		client.close();
		assertTrue(client.isClosed());
		ServerWorker worker = IdleServerWorker.factory().workerFor(socket.accept());
		assertFalse(worker.isClosed());
		assertEquals(98765, worker.receive());
		worker.close();
		assertTrue(worker.isClosed());
		socket.close();
	}

	@Test
	public void closingSenderUnblocksReceiver() throws Exception {
		ServerSocket socket = openLocalServerSocket(1);
		final Client client = new Client(socket.getInetAddress(), socket.getLocalPort());
		ServerWorker worker = IdleServerWorker.factory().workerFor(socket.accept());
		Runnable clientWait = new Runnable() {
			@Override
			public void run() {
				try {
					client.receive();
				}
				catch (SocketException e) {
					client.close();
				}
			}
		};
		Thread thread = new Thread(clientWait);
		thread.start();
		worker.close();
		while (thread.isAlive()) {/* wait */}
		assertTrue(client.isClosed());
		socket.close();
	}

	@Test
	public void socketExceptionWithReceive() throws Exception {
		ServerSocket socket = openLocalServerSocket(1);
		Client client = new Client(socket.getInetAddress(), socket.getLocalPort());
		ServerWorker worker = IdleServerWorker.factory().workerFor(socket.accept());
		worker.close();
		assertTrue(worker.isClosed());
		assertFalse(client.isClosed());
		try {
			client.receive();
			fail();
		}
		catch (SocketException e) {
			return;
		}
		finally {
			client.close();
			worker.close();
			socket.close();
		}
	}

	@Test
	public void howToKnowIfEndpointSent() throws Exception {
		ServerSocket socket = openLocalServerSocket(1);
		Client client = new Client(socket.getInetAddress(), socket.getLocalPort());
		ServerWorker worker = IdleServerWorker.factory().workerFor(socket.accept());
		assertFalse(client.dataToRead());
		assertFalse(worker.dataToRead());

		client.send(879813f);
		assertFalse(client.dataToRead());
		assertTrue(worker.dataToRead());

		assertEquals(879813f, worker.receive());
		assertFalse(client.dataToRead());
		assertFalse(worker.dataToRead());

		worker.send("wazzoo");
		assertTrue(client.dataToRead());
		assertFalse(worker.dataToRead());

		assertEquals("wazzoo", client.receive());
		assertFalse(client.dataToRead());
		assertFalse(worker.dataToRead());

		client.close();
		socket.close();
	}
}
