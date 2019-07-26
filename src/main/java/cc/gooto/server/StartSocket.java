package cc.gooto.server;

import java.util.Scanner;
import java.util.Set;

import cc.gooto.server.socket.Server;
import cc.gooto.server.socket.SocketHandle;

public class StartSocket {

	public static void main(String[] args) {

		new Thread(new Server(), "main:server").start();

		Scanner sc = new Scanner(System.in);
		while (true) {
			String nextLine = sc.nextLine();
			if ("".equals(nextLine) || nextLine == null) {
				continue;
			}
			String[] split = nextLine.split("\\s+");
			switch (nextLine) {
			case "sockets":
				sockets(split);
				break;
			case "quit":
			case "exit":
				sc.close();
				System.out.println("再见！");
				System.exit(0);
				break;
			default:
				System.out.println("命令错误...");
				break;
			}
		}
	}

	public static void sockets(String[] args) {
		Set<String> keySet = SocketHandle.clients.keySet();
		if (keySet.size() < 1) {
			System.out.println("null");
		} else {
			for (String string : keySet) {
				System.out.println(string + "\t" + SocketHandle.getClient(string).getSocket().isConnected() + "\t"
						+ SocketHandle.getClient(string).getSocket().isClosed());
			}
		}
	}

}
