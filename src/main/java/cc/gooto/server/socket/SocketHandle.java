package cc.gooto.server.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import cc.gooto.server.message.MessageHandler;
import cc.gooto.server.message.MessageHandlerImpl;
import cc.gooto.server.model.Client;
import cc.gooto.server.model.Message;

public class SocketHandle implements Runnable {

	public static Map<String, Client> clients = new HashMap<String, Client>();

	private Client client;

	public SocketHandle(Socket socket) {
		this.client = new Client(socket);
	}

	public void run() {
		try {
			// 获取输入流
			InputStream inputStream = getClient().getSocket().getInputStream();
			System.out.println("==>" + getClient().getSocket().getInetAddress().getHostAddress() + "开始监听数据：");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			MessageHandler mh = new MessageHandlerImpl();

			while (getClient().getSocket().isConnected() && !(getClient().getSocket().isClosed())) {
				int read = inputStream.read();
				// 数据格式JSON，流分析以#为起点，$为终点。
				if (read != -1) {
					if (read == 35) {
						// 说明是某一段数据的起点#
						baos.reset();
						continue;
					} else if (read == 36) {
						// 说明是某一段数据的终点$
						mh.messageAnalysis(getClient().getSocket().getInetAddress().getHostAddress(),
								JSON.parseObject(baos.toByteArray(), Message.class, Feature.IgnoreNotMatch));
					}
					baos.write(read);
				}
			}

			baos.close();
			System.out.println("==>" + getClient().getSocket().getInetAddress().getHostAddress() + "关闭。");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addSocket(String ip, Socket socket) {
		SocketHandle socketHandle = new SocketHandle(socket);
		// 为接收到的套接字开启新线程，线程名称以IP命名
		new Thread(socketHandle, ip).start();
		// 线程开启后保存套接字
		System.out.println("==>IP" + ip + "接入");
		clients.put(ip, socketHandle.getClient());
	}

	public static void removeClient(String ip) {
		clients.remove(ip);
	}

	public static Client getClient(String ip) {
		return clients.get(ip);
	}

	public static List<String> getClientIps(String clientType) {
		List<String> ips = new ArrayList<String>();
		Set<String> keySet = clients.keySet();
		for (String string : keySet) {
			if (clientType.equals(clients.get(string).getClientType())) {
				ips.add(string);
			}
		}
		return ips;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
