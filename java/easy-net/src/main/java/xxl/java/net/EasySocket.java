package xxl.java.net;

import static java.net.InetAddress.getByAddress;
import static java.net.InetAddress.getLocalHost;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EasySocket {

	public static InetAddress ipV4(byte[] address) throws IOException {
		if (address.length != 4) {
			throw new IllegalArgumentException("Invalid IPv4 address: " + address);
		}
		return getByAddress(address);
	}

	public static Socket openLocalSocket(int serverPort) throws IOException {
		return openSocket(getLocalHost(), serverPort);
	}

	public static Socket openSocket(byte[] ipV4, int serverPort) throws IOException {
		return openSocket(ipV4(ipV4), serverPort);
	}

	public static Socket openSocket(InetAddress address, int serverPort) throws IOException {
		return new Socket(address, serverPort);
	}

	public static ServerSocket openLocalServerSocket(int maxConnections) throws IOException {
		return openLocalServerSocket(0, maxConnections);
	}

	public static ServerSocket openLocalServerSocket(int port, int maxConnections) throws IOException {
		return openServerSocket(getLocalHost(), port, maxConnections);
	}

	public static ServerSocket openServerSocket(byte[] ipV4, int maxConnections) throws IOException {
		return openServerSocket(ipV4(ipV4), 0, maxConnections);
	}

	public static ServerSocket openServerSocket(InetAddress address, int maxConnections) throws IOException {
		return openServerSocket(address, 0, maxConnections);
	}

	public static ServerSocket openServerSocket(byte[] ipV4, int port, int maxConnections) throws IOException {
		return openServerSocket(ipV4(ipV4), port, maxConnections);
	}

	public static ServerSocket openServerSocket(InetAddress address, int port, int maxConnections) throws IOException {
		return new ServerSocket(port, maxConnections, address);
	}

}
