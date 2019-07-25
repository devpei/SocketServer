package cc.gooto.server.message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cc.gooto.server.model.Message;

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
}
