package xxl.java.net;

import static java.net.InetAddress.getLocalHost;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import xxl.java.net.offline.HelloServerWorker;


public class ServerTest {

	@Test
	public void sampleServer() throws Exception {
		ConnectionHandler handler = new ConnectionHandler(HelloServerWorker.factory());
		Server server = new Server(getLocalHost(), 0, 1, handler);
		assertFalse(0 == server.port());
		assertTrue(Arrays.equals(new byte[] { 127, 0, 1, 1 }, server.ip()));

		server.start();

		String received;
		Client client = new Client(server.address(), server.port());

		client.send("FR");
		received = client.receive();
		assertEquals(received, "Bonjour");

		client.send(true);
		client.send("ES");
		received = client.receive();
		assertEquals(received, "Hola");

		client.send(true);
		client.send("EN");
		received = client.receive();
		assertEquals(received, "Hello");

		client.send(false);
		client.close();
		server.tearDown();
		assertTrue(server.isDown());
	}
}
