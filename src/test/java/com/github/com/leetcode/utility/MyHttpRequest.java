package com.github.com.leetcode.utility;

import com.intellij.util.io.HttpRequests;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyHttpRequest {

	private String url;
	private String contentType;
	private Map<String, String> header;
	private String body;

	public MyHttpRequest(String url, String contentType) {
		this.url = url;
		this.contentType = contentType;
		this.header = new HashMap<>();
	}

	public String getUrl() {
		return url;
	}

	public String getContentType() {
		return contentType;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void addHeader(String key, String value) {
		this.header.put(key, value);
	}

	public void addDefaultHeader() {
		this.header.putIfAbsent("Accept", "*/*");
		this.header.putIfAbsent("Accept-Language", "zh-CN,zh;q=0.9");
		this.header.putIfAbsent("origin", "https://leetcode.cn");
	}

	public void addBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public MyHttpResponse post() {
		MyHttpResponse resp = new MyHttpResponse();
		try {
			HttpRequests
					.post(this.url, this.contentType)
					.throwStatusCodeException(false)
					.tuner(new MyRequestTuner(this))
					.connect(new MyHttpResponseProcessor(this, resp));
		} catch (IOException e) {
			resp.setCode(-1);
		}
		return resp;
	}
}
