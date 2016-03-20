package xxl.java.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * The method {@link Socket#getInputStream()} returns an {@code InputStream} to read data sent
 * through the {@code Socket}. However, because closing the returned {@code InputStream} also
 * closes the associated {@code Socket}, we wrap the {@code Sockets}'s {@code InputStream} with
 * a {@link SocketInputStream}.<br>
 * The {@code SocketInputStream} overrides {@link InputStream#close()} and does not close the
 * {@code Socket}.
 */
public class SocketInputStream extends InputStream {

	public SocketInputStream(Socket socket) throws IOException {
		this.inputStream = socket.getInputStream();
	}

	@Override
	public void close() {
		/* Does not close because it would close the Socket */
	}

	@Override
	public int available() throws IOException {
		return inputStream.available();
	}

	@Override
	public int read() throws IOException {
		return inputStream.read();
	}

	@Override
	public String toString() {
		return SocketInputStream.class.getSimpleName();
	}

	private InputStream inputStream;
}
