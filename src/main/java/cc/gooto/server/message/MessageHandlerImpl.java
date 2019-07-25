package cc.gooto.server.message;

import java.io.IOException;
import java.net.Socket;

import cc.gooto.server.model.Message;
import cc.gooto.server.socket.SocketHandle;

public class MessageHandlerImpl implements MessageHandler {

	public void singleSend(String ip, String content) {
		try {
			Socket socket = SocketHandle.getSocket(ip);
			if (socket != null) {
				if (socket.isConnected() && !(socket.isClosed())) {
					socket.getOutputStream().write(content.getBytes("UTF-8"));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void singleSendDevices(Message message) {
		singleSend(message.getDestinationAddress(), message.getContent());
	}

}
