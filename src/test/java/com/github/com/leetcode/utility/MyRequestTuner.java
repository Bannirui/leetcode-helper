package com.github.com.leetcode.utility;

import com.intellij.util.io.HttpRequests;
import java.io.IOException;
import java.net.URLConnection;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MyRequestTuner implements HttpRequests.ConnectionTuner {

	private MyHttpRequest myReq;

	public MyRequestTuner(MyHttpRequest myReq) {
		this.myReq = myReq;
	}

	@Override
	public void tune(@NotNull URLConnection connection) throws IOException {
		String url = connection.getURL().toString();
		String token = null;
		if (StringUtils.isNotBlank(token = MyHttpUtil.getToken()) && StringUtils.isNotBlank(url) && url.contains("leetcode.cn")) {
			connection.addRequestProperty("x-csrftoken", token);
		}
		connection.addRequestProperty("referer", url);
		this.myReq.addDefaultHeader();
		this.myReq.getHeader().forEach(connection::addRequestProperty);
	}
}
