package xxl.java.net;

import java.io.IOException;
import java.net.Socket;

/**
 * A {@code ServerWorker} represents an abstract object which performs a given task for a
 * client. It is a {@link SocketEndpoint} which can send and receive data to perform a task.
 * Subclasses should implement the method {@link #work()} to deal with the client.<br>
 * For instance, a {@code HelloWorldServerWorker} would have the following implementation:
 * <pre>
 *  protected void work() {
 *    send("Hello world!"); 
 *  }
 * </pre>
 */
public abstract class ServerWorker extends SocketEndpoint implements Runnable {

	protected abstract void work() throws ClassNotFoundException, IOException;

	public ServerWorker(Socket socket) {
		super(socket);
	}

	@Override
	public final void run() {
		try {
			work();
		}
		catch (Exception e) {
			log(e.getLocalizedMessage());
		}
		finally {
			close();
		}
	}
}
