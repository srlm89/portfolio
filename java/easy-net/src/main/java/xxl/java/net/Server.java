package xxl.java.net;

import static java.lang.String.format;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * A basic implementation of a {@code Server}. To instantiate it, it needs an
 * {@link InetAddress}, the port where to listen (values lower than 1024 require root
 * permission; and value 0 lets the server pick a port), a maximum number of connections
 * allowed, and a {@link ConnectionHandler}<br>
 * To set up a local server, use {@link InetAddress#getLocalHost()} as the {@code address}.<br>
 * Once created, you can {@link #start()} the server so clients can connect and you shut down
 * the server with {@link #tearDown()}.
 */
public class Server {

	public Server(InetAddress address, int port, int maxConnections, ConnectionHandler handler) throws IOException {
		this.socket = new ServerSocket(port, maxConnections, address);
		this.handler = handler;
	}

	public int port() {
		return socket.getLocalPort();
	}

	public byte[] ip() {
		return socket.getInetAddress().getAddress();
	}

	public String host() {
		return socket.getInetAddress().getCanonicalHostName();
	}

	public InetAddress address() {
		return socket.getInetAddress();
	}

	public void start() {
		if (thread == null) {
			handler.setServerSocket(socket);
			thread = new Thread(handler, "Server");
			thread.start();
		}
	}

	public boolean isDown() {
		return !thread.isAlive() && socket.isClosed();
	}

	public void tearDown() {
		handler.interrupt();
		while (thread.isAlive()) {/* wait */}
		if (!isDown()) {
			throw new IllegalStateException("Server could not be closed properly");
		}
	}

	@Override
	public String toString() {
		return format("%s [host= %s, port= %s]", Server.class.getSimpleName(), host(), port());
	}

	private Thread thread;
	private ServerSocket socket;
	private ConnectionHandler handler;
}
