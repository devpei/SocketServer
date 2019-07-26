package cc.gooto.server.model;

import java.net.Socket;

public class Client {

	/**
	 * 套接字
	 */
	private Socket socket;

	/**
	 * 客户端类型
	 */
	private String clientType;

	public Client() {
	}

	public Client(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
}
