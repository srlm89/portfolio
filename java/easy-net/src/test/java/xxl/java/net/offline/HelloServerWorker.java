package xxl.java.net.offline;

import java.io.IOException;
import java.net.Socket;

import xxl.java.net.ServerWorker;
import xxl.java.net.ServerWorkerFactory;

public class HelloServerWorker extends ServerWorker {

	public static ServerWorkerFactory factory() {
		return new ServerWorkerFactory() {
			@Override
			public ServerWorker workerFor(Socket socket) {
				return new HelloServerWorker(socket);
			}
		};
	}

	private HelloServerWorker(Socket socket) {
		super(socket);
	}

	@Override
	protected void work() throws ClassNotFoundException, IOException {
		boolean keepWorking = true;
		while (keepWorking) {
			String received = receive();
			String toSend = null;
			switch (received) {
			case "ES":
				toSend = "Hola";
				break;
			case "FR":
				toSend = "Bonjour";
				break;
			default:
				toSend = "Hello";
				break;
			}
			send(toSend);
			keepWorking = receive();
		}
	}
}
