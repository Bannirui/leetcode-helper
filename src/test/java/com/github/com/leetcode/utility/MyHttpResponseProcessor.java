package com.github.com.leetcode.utility;

import com.intellij.util.io.HttpRequests;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MyHttpResponseProcessor implements HttpRequests.RequestProcessor<MyHttpResponse> {

	private MyHttpRequest myReq;
	private MyHttpResponse myResp;

	public MyHttpResponseProcessor(MyHttpRequest myReq, MyHttpResponse myResp) {
		this.myReq = myReq;
		this.myResp = myResp;
	}

	@Override
	public MyHttpResponse process(HttpRequests.@NotNull Request request) throws IOException {
		if (StringUtils.isNotBlank(this.myReq.getBody())) {
			request.write(this.myReq.getBody());
		}
		URLConnection conn = request.getConnection();
		if (conn instanceof HttpURLConnection httpConn) {
			this.myResp.setCode(httpConn.getResponseCode());
		} else {
			this.myResp.setCode(-1);
			return this.myResp;
		}
		this.myResp.setBody(request.readString());
		return this.myResp;
	}
}
