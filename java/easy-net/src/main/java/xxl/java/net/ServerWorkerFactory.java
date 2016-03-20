package xxl.java.net;

import java.net.Socket;

/**
 * A {@link ConnectionHandler} creates a {@link ServerWorker} each time a new client arrives.
 * To do this, the {@code ConnectionHandler} relies on a {@code ServerWorkerFactory} which
 * returns a {@code ServerWorker} when the method {@link #workerFor(Socket)} is called.
 */
public interface ServerWorkerFactory {

	ServerWorker workerFor(Socket socket);
}
