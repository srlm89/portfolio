package xxl.java.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * The method {@link Socket#getOutputStream()} returns an {@code OutputStream} to send data
 * through the {@code Socket}. However, because closing the returned {@code OutputStream} also
 * closes the associated {@code Socket}, we wrap the {@code Sockets}'s {@code OutputStream}
 * with a {@link SocketOutputStream}.<br>
 * The {@code SocketOutputStream} overrides {@link OutputStream#close()} and does not close the
 * {@code Socket}.
 */
public class SocketOutputStream extends OutputStream {

	public SocketOutputStream(Socket socket) throws IOException {
		this.outputStream = socket.getOutputStream();
	}

	@Override
	public void close() {
		/* Does not close because it would close the Socket */
	}

	@Override
	public void write(int b) throws IOException {
		outputStream.write(b);
	}

	@Override
	public String toString() {
		return SocketOutputStream.class.getSimpleName();
	}

	private OutputStream outputStream;
}
