package cc.gooto.server.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import cc.gooto.server.message.MessageHandler;
import cc.gooto.server.message.MessageHandlerImpl;
import cc.gooto.server.model.Message;

public class SocketHandle implements Runnable {

	public static Map<String, Socket> sockets = new HashMap<String, Socket>();

	private Socket socket;

	public SocketHandle(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			// 获取输入流
			InputStream inputStream = getSocket().getInputStream();
			System.out.println("==>" + getSocket().getInetAddress().getHostAddress() + "开始监听数据：");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			MessageHandler mh = new MessageHandlerImpl();

			while (getSocket().isConnected() && !(getSocket().isClosed())) {
				int read = inputStream.read();
				if (read != -1) {
					if (read == 35) {
						// 说明是某一段数据的起点#
						baos.reset();
						continue;
					} else if (read == 36) {
						// 说明是某一段数据的终点$
						// System.out.println(new String(baos.toByteArray(), "UTF-8"));
						mh.singleSendDevices(
								JSON.parseObject(baos.toByteArray(), Message.class, Feature.IgnoreNotMatch));
					}
					baos.write(read);
				}
			}

			baos.close();
			System.out.println("==>" + getSocket().getInetAddress().getHostAddress() + "关闭。");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addSocket(String ip, Socket socket) {
		// 为接收到的套接字开启新线程，线程名称以IP命名
		new Thread(new SocketHandle(socket), ip).start();
		// 线程开启后保存套接字
		System.out.println("==>IP" + ip + "接入");
		sockets.put(ip, socket);
	}

	public static void removeSocket(String ip) {
		sockets.remove(ip);
	}

	public static Socket getSocket(String ip) {
		return sockets.get(ip);
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
