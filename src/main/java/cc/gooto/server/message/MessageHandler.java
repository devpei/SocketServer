package cc.gooto.server.message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cc.gooto.server.model.Message;
import cc.gooto.server.socket.SocketHandle;

public interface MessageHandler {

	/**
	 * 发送给某一客户端
	 * 
	 * @param ip
	 * @param content
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	void singleSend(String ip, String content);

	/**
	 * 将命令转发给设备
	 * 
	 * @param message
	 */
	void singleSendDevices(Message message);

	/**
	 * 同一类型客户端消息群发
	 * 
	 * @param clientType
	 * @param message
	 */
	void groupSend(String clientType, List<String> ips);

	/**
	 * 消息解析
	 * 
	 * @param ip      消息来源地址
	 * @param message
	 */
	default void messageAnalysis(String ip, Message message) {
		// 说明是上报信息消息，无需转发
		System.out.println("<==收到消息：" + JSON.toJSONString(message));
		if ("0.0.0.0".equals(message.getDestinationAddress())) {
			// 字段clientType表示客户端类型
			String clientType = (String) message.getContent().get("clientType");
			SocketHandle.getClient(ip).setClientType(clientType);
			if ("Devices".equals(clientType)) {
				// 说明是设备端
				List<String> deviceIps = SocketHandle.getClientIps(clientType);
				// 向所有移动控制端发送设备端地址，以便移动端决定操控哪个设备
				groupSend("RemoteControl", deviceIps);
			}
		} else {
			singleSendDevices(message);
		}
	}
}
