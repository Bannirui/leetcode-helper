package com.github.com.leetcode.utility;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class GraphqlUtil {

	private static final String QUESTION_URL = "https://leetcode-cn.com/problems/two-sum/description/";
	private static final String GRAPHQL_URL = "https://leetcode-cn.com/graphql";


	public static void main(String[] args) throws IOException {

		CookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet httpGet = new HttpGet(QUESTION_URL);
		HttpResponse resp = httpClient.execute(httpGet);
		if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			System.out.println("Http status code is not 200.");
			return;
		}
		// cookie key: aliyungf_tc __cfduid
		for (Cookie cookie : cookieStore.getCookies()) {
			String name = cookie.getName();
			String value = cookie.getValue();
			System.out.println("name=" + name + ", value=" + value);
		}
		System.out.println();
	}
}
