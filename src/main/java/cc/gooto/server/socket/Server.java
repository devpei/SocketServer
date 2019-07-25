package cc.gooto.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	private static final int DEF_PORT = 7800;

	public static ServerSocket ss;

	private int port = DEF_PORT;

	public Server() {
	}

	public Server(int port) {
		this.port = port;
	}

	public void run() {
		try {
			ss = new ServerSocket(getPort());
			Socket socket = null;
			System.out.println("==>服务已经开启，端口：" + getPort());
			while (true) {
				socket = ss.accept();
				if (socket == null) {
					continue;
				}
				if (socket.isConnected()) {
					SocketHandle.addSocket(socket.getInetAddress().getHostAddress(), socket);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
