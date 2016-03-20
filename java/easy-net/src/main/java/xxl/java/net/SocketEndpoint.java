package xxl.java.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

/**
 * A {@code SocketEndpoint} represents an abstract end-point of a communication using a
 * {@link Socket}.<br>
 * A {@code SocketEndpoint} implements the methods {@link #send(java.io.Serializable)} and
 * {@link #receive()} to exchange objects. For instance, if end-point {@code A} wants to send
 * an object to end-point {@code B}, then {@code A} should call {@code send()} and {@code B}
 * should call {@code receive()}.<br>
 * The {@code send()} method is non-blocking, and the {@code receive()} method is blocking.
 * However, if the other {@code SocketEndpoint} is closed with {@link #close()} method, the
 * blocked receiver gets unblocked and throws a {@link SocketException}.
 */
public abstract class SocketEndpoint implements Closeable {

	public SocketEndpoint(Socket socket) {
		this.socket = socket;
	}

	protected Socket socket() {
		return socket;
	}

	public boolean isClosed() throws IOException {
		return socket().isClosed();
	}

	public boolean dataToRead() throws IOException {
		return socketInputStream().available() > 0;
	}

	public <T extends Serializable> void send(T object) throws IOException {
		InputStream serialized = migrator().serialized(object);
		try {
			migrator().migrateTo(socketOutputStream(), serialized);
		}
		catch (Exception e) {
			throw newSocketException("Failed to send object: " + object, e);
		}
		finally {
			serialized.close();
		}
	}

	public <T extends Serializable> T receive() throws SocketException {
		try {
			T object = migrator().deserialized(socketInputStream());
			return object;
		}
		catch (Exception e) {
			throw newSocketException("Failed to receive", e);
		}
	}

	protected SocketException newSocketException(String message, Throwable cause) {
		SocketException e = new SocketException(message);
		e.setStackTrace(cause.getStackTrace());
		return e;
	}

	protected OutputStream socketOutputStream() throws IOException {
		if (socketOutputStream == null) {
			this.socketOutputStream = new SocketOutputStream(socket);
		}
		return socketOutputStream;
	}

	protected InputStream socketInputStream() throws IOException {
		if (socketInputStream == null) {
			this.socketInputStream = new SocketInputStream(socket);
		}
		return socketInputStream;
	}

	protected ObjectMigrator migrator() {
		return ObjectMigrator.instance();
	}

	protected void log(String message) {
		System.err.println(message);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	@Override
	public void close() {
		try {
			socket().close();
		}
		catch (IOException e) {
			log("Could not close socket: " + socket());
		}
	}

	private Socket socket;
	private InputStream socketInputStream;
	private OutputStream socketOutputStream;
}
