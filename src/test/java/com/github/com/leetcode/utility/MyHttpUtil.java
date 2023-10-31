package com.github.com.leetcode.utility;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class MyHttpUtil {

	private static final CookieManager COOKIE_MANAGER = new CookieManager(null, (uri, cookie) -> {
		if (null == uri || null == cookie || StringUtils.equals(uri.getHost(), "hm.baidu.com")) {
			return false;
		}
		return HttpCookie.domainMatches(cookie.getDomain(), uri.getHost());
	});

	static {
		CookieHandler.setDefault(COOKIE_MANAGER);
	}

	public static String getToken() {
		List<HttpCookie> cookies = COOKIE_MANAGER.getCookieStore().getCookies();
		if (CollectionUtils.isEmpty(cookies)) {
			return null;
		}
		for (HttpCookie cookie : cookies) {
			String tmp = null;
			if (StringUtils.isBlank(tmp = cookie.getDomain())) {
				continue;
			}
			if (tmp.toLowerCase().contains("leetcode.cn") && StringUtils.equals("csrftoken", cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
