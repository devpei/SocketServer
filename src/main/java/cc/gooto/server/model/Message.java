package cc.gooto.server.model;

public class Message {

	/**
	 * 消息类型
	 */
	private String type = "RemoteControl";

	/**
	 * 源地址
	 */
	private String sourcesAddress;

	/**
	 * 目的地址
	 */
	private String destinationAddress;

	/**
	 * 内容
	 */
	private String content;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSourcesAddress() {
		return sourcesAddress;
	}

	public void setSourcesAddress(String sourcesAddress) {
		this.sourcesAddress = sourcesAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
