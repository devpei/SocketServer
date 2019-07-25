package cc.gooto.server.socket;

import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Map<String, Socket> socketTables = new HashMap<String, Socket>();

	public void addSocket(Socket socket, String mark) {
		socketTables.put(mark, socket);
	}

	public void removeSocket(String mark) {
		socketTables.remove(mark);
	}

	public Socket getSocket(String mark) {
		return socketTables.get(mark);
	}

}
