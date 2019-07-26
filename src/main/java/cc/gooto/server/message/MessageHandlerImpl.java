package cc.gooto.server.message;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cc.gooto.server.model.Message;
import cc.gooto.server.socket.SocketHandle;

public class MessageHandlerImpl implements MessageHandler {

	public void singleSend(String ip, String content) {
		try {
			Socket socket = SocketHandle.getClient(ip).getSocket();
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
		// 字段command代表下发命令
		singleSend(message.getDestinationAddress(), (String) message.getContent().get("command"));
	}

	@Override
	public void groupSend(String clientType, List<String> ips) {
		List<String> clientIps = SocketHandle.getClientIps(clientType);
		for (String string : clientIps) {
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("DevicesList", ips);
			Message message = new Message(string, content);
			singleSend(string, "#" + JSON.toJSONString(message) + "$");
		}
	}

}
