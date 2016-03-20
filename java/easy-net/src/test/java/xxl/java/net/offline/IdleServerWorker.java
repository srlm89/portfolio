package xxl.java.net.offline;

import java.io.IOException;
import java.net.Socket;

import xxl.java.net.ServerWorker;
import xxl.java.net.ServerWorkerFactory;

public class IdleServerWorker extends ServerWorker {
	
	public static ServerWorkerFactory factory() {
		return new ServerWorkerFactory() {
			
			@Override
			public ServerWorker workerFor(Socket socket) {
				return new IdleServerWorker(socket);
			}
		};
	}

	public IdleServerWorker(Socket socket) {
		super(socket);
	}

	@Override
	protected void work() throws ClassNotFoundException, IOException {
		/* do nothing */
	}
}
