package xxl.java.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A {@code ConnectionHandler} is used by a {@link Server} to listen for clients in a
 * {@link ServerSocket}. This is done by calling {@link ServerSocket#accept()}. This is a
 * blocking method, so in order to tear down the {@code ConnectionHandler} a method call to
 * {@link interrupt()} should be performed from outside the {@code ConnectionHandler}.<br>
 * When a client arrives, a {@link ServerWorker} is created and launched in a new
 * {@code Thread}.
 */
public final class ConnectionHandler implements Runnable {

	public ConnectionHandler(ServerWorkerFactory workerFactory) {
		this.interrupted = false;
		this.workerFactory = workerFactory;
	}

	protected void setServerSocket(ServerSocket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		if (socket != null) {
			while (!finished()) {
				try {
					Socket connection = socket.accept();
					ServerWorker worker = workerFactory.workerFor(connection);
					new Thread(worker, "ServerWorker").start();
				}
				catch (IOException e) {
					log("Connection Error: " + e.getLocalizedMessage());
				}
			}
		}
	}

	public void interrupt() {
		this.interrupted = true;
		if (!socket.isClosed()) {
			try {
				socket.close();
			}
			catch (IOException e) {
				log("Could not close ServerSocket: " + socket);
			}
		}
	}

	public boolean isInterrupted() {
		return interrupted;
	}

	protected boolean finished() {
		return socket.isClosed() || isInterrupted();
	}

	private void log(String message) {
		System.err.println(message);
	}

	private boolean interrupted;
	private ServerSocket socket;
	private ServerWorkerFactory workerFactory;
}
