package cc.gooto.server.model;

import java.util.Map;

public class Message {

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
	private Map<String, Object> content;

	public Message() {
	}

	public Message(String destinationAddress, Map<String, Object> content) {
		super();
		this.destinationAddress = destinationAddress;
		this.content = content;
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

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}
}
