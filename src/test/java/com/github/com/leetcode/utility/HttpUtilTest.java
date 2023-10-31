package com.github.com.leetcode.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import junit.framework.TestCase;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpUtilTest extends TestCase {

	public void test00() throws IOException {
		String token = StringUtils.isNotBlank(MyHttpUtil.getToken()) ? MyHttpUtil.getToken() : "";
		HttpEntity ent = MultipartEntityBuilder.create()
				.addTextBody("csrfmiddlewretoken", token)
				.addTextBody("login", "1652959004@qq.com")
				.addTextBody("password", "dingrui19920308")
				.addTextBody("next", "/problems")
				.build();
		String contentType = ent.getContentType().getValue();
		MyHttpRequest req = new MyHttpRequest("https://leetcode.cn/accounts/login", "application/json");
		StringWriter sw = new StringWriter();
		InputStreamReader in = new InputStreamReader(ent.getContent(), StandardCharsets.UTF_8);
		char[] buffer = new char[4096];
		int n;
		while (-1 != (n = in.read(buffer))) {
			sw.write(buffer, 0, n);
		}
		req.addBody(sw.toString());
		MyHttpResponse resp = req.post();
		System.out.println();
	}

	public void testLogin() throws IOException {
		String url = "https://leetcode.cn/graphql/";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		File projectRootPath = new File("").getCanonicalFile();
		File f = new File(projectRootPath, "cfg/cookie_4_login.txt");
		if (!f.exists() || !f.isFile()) {
			System.out.println("Cookie file do not exist.");
		}
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String cookie = br.readLine();
		if (StringUtils.isBlank(cookie)) {
			System.out.println("Request cookie is none.");
		}
		HttpPost httpPost = new HttpPost(url);
		// request header
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("Cookie", cookie);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Origin", "https://leetcode.cn");
		// request body
		String body =
				"{\"operationName\":\"signInWithPassword\",\"variables\":{\"data\":{\"username\":\"1652959004@qq.com\",\"password\":\"dingrui19920308\"},\"nvcData\":\"%7B%22a%22%3A%22FFFF000000000179BB25%22%2C%22c%22%3A%221698661242773%3A0.2528812833858156%22%2C%22d%22%3A%22nvc_login%22%2C%22j%22%3A%7B%22test%22%3A1%7D%2C%22h%22%3A%7B%22umidToken%22%3A%22T2gA21PRmKAAHTjq82Pfjg0g2gP9X7hC4JD7gBsDLm9gHldY4tAuN8RJsBolKGJOKkw%3D%22%7D%2C%22b%22%3A%22140%23nXznR%2FZKzzFKwQo22x2%2BCtSd0kaNDFaSEQG8E2hB9AWmKtPdvymepS7PxZo%2F%2Bd2GVKSvLmLPWGSaPSrp14uxmBoqlbzxhJ74uxQtzzrzxXIbl3eY%2BdxFJu8hIM5fjwVLsYl2qCCB6mBmh63TR4WQhToylULSM%2FWQbNPutCCMMJqoa9d8qe7P6sk0lnpYwtnJVj7KXV7QTgqoNsiS%2Ft7BpTYNMlsYUtnQIkWdtPBftYnA79AgXv6E7OE8lPfI%2FC8pIJlzzFzb22U3lpk2ObuvV2VtlzDa2Dc3V3eIhD%2BiDnULU3Srzus%2BbRtVKrvZrI7ZbioVUlH85UodC94NyhlQ9vB7OdBwMnJGkl4LAE6kY7B%2FTPG%2BG3%2FxBQKEsJomnRcTjomMTMErBBHqxXHqPpVvl1jIcbijAk17223%2Fh0xxkr3lYrV2pBY8d3C2V4I0ZjPiaIW62m1qkBVwURvMERMU3YFG7cTfpPxq2hKBhmoSsAUYgjsWyTdX0RcId0sah7ivBu2NCsw1Jh%2FxLMRg3H%2BVKFTQOWrnTc5dudmK%2BcrnFOfDnhKSGzpgtks5%2By%2Ffwki08mtj9LUfObEv67T7LGjDiPLdbjFoJWIHIQ8YA772zH%2Bjvgbk0s660M67YE8eclnCNE8WFF51FeRr1oKO1K3TDJSOmWYBmzGX%2BS6lRsvJ7dHGA%2FX%2BfNXbrWQ8yNvw0cJsGi1Z203sg7JQaIfUwMkIYJxPBHLbJd3WD13UsTJhfIhN%2BkprtJl7FNCji7VcrRYaTAOqw%2FK%2Fgg3fuohIHnlkz8cgXDDazUDd1An0vS%2BZ2uKZfrypmMwczAU6S20SpumgawP40oYjEEzn8xK%2F64dUnh7xZDtfCKKwb7ey11MaCbzlRsYLNCXnyl16LdEULtkgQ5QOyyHhjysep%2BqVq9MYB7C3wYpF4%2BHTvWuB0VLoV16Bw9EyrCJbmvQ5gh10QzbjPLyqlaumfuRRHHUk%2B1ikmvGa%2F1RJKBAjo8jlumcouzBQ4r0R18I7S1GNv%2B5aDU6smRcbWuwggSvz89tgBX6rIsodowGTnfyermjKpgX1Ik8UVa82KT8gq65sDtSqYx5tDav80rnccIur%2BfGHXyMO5zVdSZCa6Y96NNDpohtfqhSgk7KsVPmayxC324QrLMqZemLkQ5htLlgsa6443jGDLwTTTkVfF6uzCu3dyKk7N3H8RlmSTMfeKeRZ%2BZ%2FVmkJmTyolPu4eRHFj9FQ4UNYfpJ63eapup4xMuizGfDHMSXBC%2FMwbq3YhDU1JJA07aRoTOm%2BArRYYEP3jp3%2BhQS4LHEzBvSMeZuCIQTXk5T1X%2BFXC7Ru2lBQRpiyp5a6vfDqlhUGRiHYuh1LNOkb51dImOL%2F0yKdCT6YIP%2B4vU5u3Ujos4Hvpg9PgOL5j0MlT4mPRzTbVU0ytqZ5zMq7Au%2F6RxnIFlQHVQ422uZD9p%2BuvYurD3bRo6TKVPQSHozQqbTWA%2FA74kxkDOX62JgsfbEbHscJN02E2T%2FKLslP5AKPMg3TEWK8H6XC7O7Y2xtFZXUTI8Jhp9AjcwrL0mD%2F6OQXq%2BDD3jZJZ6JuHS%2FICiJhqv6QQbU%2F3w0NcYzyqgVTjYJNzl%2BuqR6a1zb6TqKWeR02K%2B9A0P1R4a2FurNN0prrYmP%2F95jPyGgVbApIsZg7jpf3ctikCopAyRhpROMxSUJW5Fai3IyzOEQLGaTFeFD9BpcsvAKO0pVZ0VZbZtKhU3ySLfcgaLIpkMFw39k%2FhvTyqThpuWnzLsWmO3%2BP0Ow4PD%2FUviEgsS8J60srZcmOQTeIw3nbgZFHK%2FBJDsRcdehsMAZD2y430ovVYWqnSvVqfY4Cpq5eMQxjBtC8d6JmdR1DkHTrBZe%2B0iOkxSnFHmjFefa3wxBQSI0ipTX%2FgKDlu8squwUhuMO1a5MJ9ZyLeeNi7EqRaQABrwIfK8pcPj0G5SHAm4oscFpeFxJ88VGLjvK6isnUhjvFX0vGYdzFCuzlovB0W4CBlWCyezfb8NNDN2dzr%2B8TDv2Y%2Fb6buznsVMGkNOyKQxy8YQHFE7KHIC36A6smnZ%2Bvh6RcJQGqEC2SmDkUjipaP14cQF4l9xO5Q7YtltOTHFWETV6Grn9D6IMuQdbYKkdR7RgGYaDIgL7ihTx9Hj141ipE69ml2ZPpAxzMeRuOCHKYYh%2BHMwllALuBsJsU%2BmKwfH3DNGHSH%2BrjuF8sihYh%2BTPj4iIoJ9CJlpyCy%22%2C%22e%22%3A%229K2b1C1GDGo6SFJfPuTsNdt35W8FsZ00LquVvJd6qS6vnRq-SVwN3Q0vqJ5u4urXzJ_lOngNU-cY9DTeWqd1JbDHysWLFnrV818jgwHex3DsUDHo4HroN9k34vtS9tXGPAkM4OKly4afOQO7E87qBwicACrPgLkq6tJi2zWL5GNGTxgkH77tt4lRpIUHey2a0BTVzh5O71q9eQZJaI-Yeg%22%7D\"},\"query\":\"mutation signInWithPassword($data: AuthSignInWithPasswordInput!, $nvcData: String!) {\\n  authSignInWithPasswordByNvc(data: $data, nvcData: $nvcData) {\\n    output {\\n      ok\\n      otpConfig {\\n        configUrl\\n        userToken\\n        __typename\\n      }\\n      __typename\\n    }\\n    nvcStatus\\n    __typename\\n  }\\n}\\n\"}\n";
		StringEntity se = new StringEntity(body, "UTF-8");
		se.setContentType("application/json");
		httpPost.setEntity(se);
		HttpResponse resp = httpClient.execute(httpPost);
		int code = resp.getStatusLine().getStatusCode();
		int scOk = HttpStatus.SC_OK;
		String s = EntityUtils.toString(resp.getEntity());
		CookieStore cookies = httpClient.getCookieStore();
		System.out.println();
	}

	@Test
	public void testLoginStatus() throws IOException {
		String url = "https://leetcode.cn/graphql/";
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod(url);
		File projectRootPath = new File("").getCanonicalFile();
		File f = new File(projectRootPath, "cfg/cookie.txt");
		if (!f.exists() || !f.isFile()) {
			System.out.println("Cookie file do not exist.");
		}
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String cookie = br.readLine();
		if (StringUtils.isBlank(cookie)) {
			System.out.println("Request cookie is none.");
		}
		// request header
		pm.addRequestHeader("Cookie", cookie);
		pm.addRequestHeader("Content-Type", "application/json");
		// request body
		String body =
				"{\"query\":\"\\n    query globalData {\\n  userStatus {\\n    isSignedIn\\n    isPremium\\n    username\\n    realName\\n    avatar\\n    userSlug\\n    isAdmin\\n    checkedInToday\\n    useTranslation\\n    premiumExpiredAt\\n    isTranslator\\n    isSuperuser\\n    isPhoneVerified\\n    isVerified\\n  }\\n  jobsMyCompany {\\n    nameSlug\\n  }\\n}\\n    \",\"variables\":{},\"operationName\":\"globalData\"}\n";
		pm.setRequestBody(body);
		int code = hc.executeMethod(pm);
		String resp = pm.getResponseBodyAsString();
		System.out.println();
	}

	public void testProblemList() throws IOException {
		String url = "https://leetcode.cn/graphql/";
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod(url);
		File projectRootPath = new File("").getCanonicalFile();
		File f = new File(projectRootPath, "cfg/cookie.txt");
		if (!f.exists() || !f.isFile()) {
			System.out.println("Cookie file do not exist.");
		}
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String cookie = br.readLine();
		if (StringUtils.isBlank(cookie)) {
			System.out.println("Request cookie is none.");
		}
		// request header
		pm.addRequestHeader("Cookie", cookie);
		pm.addRequestHeader("Content-Type", "application/json");
		// request body
		String body = "{\n" +
				"    \"query\":\"\\n    query problemsetQuestionList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) {\\n  problemsetQuestionList(\\n    categorySlug: $categorySlug\\n    limit: $limit\\n    skip: $skip\\n    filters: $filters\\n  ) {\\n    hasMore\\n    total\\n    questions {\\n      acRate\\n      difficulty\\n      freqBar\\n      frontendQuestionId\\n      isFavor\\n      paidOnly\\n      solutionNum\\n      status\\n      title\\n      titleCn\\n      titleSlug\\n      topicTags {\\n        name\\n        nameTranslated\\n        id\\n        slug\\n      }\\n      extra {\\n        hasVideoSolution\\n        topCompanyTags {\\n          imgUrl\\n          slug\\n          numSubscribed\\n        }\\n      }\\n    }\\n  }\\n}\\n    \",\n" +
				"    \"variables\":{\n" +
				"        \"categorySlug\":\"\",\n" +
				"        \"skip\":0,\n" +
				"        \"limit\":50,\n" +
				"        \"filters\":{\n" +
				"\n" +
				"        }\n" +
				"    },\n" +
				"    \"operationName\":\"problemsetQuestionList\"\n" +
				"}";
		pm.setRequestBody(body);
		int code = hc.executeMethod(pm);
		String resp = pm.getResponseBodyAsString();
		System.out.println();
	}
}