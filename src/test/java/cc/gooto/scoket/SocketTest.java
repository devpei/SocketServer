package cc.gooto.scoket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cc.gooto.server.model.Message;

public class SocketTest {

	@Test
	public void conect() throws UnknownHostException, IOException, InterruptedException {
		Socket s = new Socket("192.168.1.202", 7500);

		OutputStream outputStream = s.getOutputStream();
		InputStream inputStream = s.getInputStream();

		int i = 0;

		while (i < 50) {
			outputStream.write(new String("hello" + i).getBytes("UTF-8"));

			Thread.sleep(100);

			System.out.println(i);

			i++;
		}

		while (inputStream.read() != -1) {

		}
	}

	@Test
	public void assic() {
		Message message = new Message();
		message.setType("devices");
		message.setSourcesAddress("192.168.1.169");
		message.setDestinationAddress("192.168.1.202");
		message.setContent("1");
		String jsonString = JSON.toJSONString(message);
		System.out.println(jsonString);
	}
}
