package com.proinlab.hoyeonnuri.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import proinfactory.com.hoyeonnuri.MainActivity;

import com.proinlab.hoyeonnuri.parser.HtmlParser;

public class LoginManager {
	public void loginProcess(String idstr, String passstr) {
        while(ClientManager.isClientUsing) ;

        ClientManager.isClientUsing = true;

        Log.i("TAG", idstr);
		String loginsource = null;
		ResponseHandler<String> responsehandler = new BasicResponseHandler();
		HttpPost httpost = new HttpPost("https://portal.korea.ac.kr/s_exLogin.jsp");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", idstr));
		nvps.add(new BasicNameValuePair("password", passstr));
		nvps.add(new BasicNameValuePair("returnURL", "dormitel.korea.ac.kr/pub/process/potal_login.php"));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			loginsource = MainActivity.httpClient.execute(httpost, responsehandler);

			if (PARSE_SOURCE_BY_TAG(loginsource, "name=\"sID\" value=\"", "\">").equals("")) {
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		httpost = new HttpPost("http://dormitel.korea.ac.kr/pub/process/potal_login.php");
		nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sID", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sID\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sUID", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sUID\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sPW", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sPW\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sYN", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sYN\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sWHY", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sWHY\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sNAME", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sNAME\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sGID", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sGID\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sGIDS", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sGIDS\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sStdId", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sStdId\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sGnm", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sGnm\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sEmail", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sEmail\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sMobile", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sMobile\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sDeptCd", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sDeptCd\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("sDeptNm", PARSE_SOURCE_BY_TAG(loginsource, "name=\"sDeptNm\" value=\"", "\">")));
		nvps.add(new BasicNameValuePair("msg", PARSE_SOURCE_BY_TAG(loginsource, "name=\"msg\" value=\"", "\">")));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			MainActivity.httpClient.execute(httpost, responsehandler);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

        ClientManager.isClientUsing = false;
	}

	private String PARSE_SOURCE_BY_TAG(String HtmlString, String start_tag, String end_tag) {
		String parsed_data = HtmlString;
		if (HtmlString == null)
			return null;

		int start = HtmlString.indexOf(start_tag);

		if (start == -1)
			return null;

		parsed_data = HtmlParser.getStartLocation(parsed_data, start_tag, 0);

		start = parsed_data.indexOf(start_tag);
		int end = parsed_data.indexOf(end_tag);

		parsed_data = parsed_data.substring(start + start_tag.length(), end);

		return parsed_data;
	}

    public boolean checkLogin() {
        while(ClientManager.isClientUsing) ;
        String str = new ClientManager().HtmlToString("http://dormitel.korea.ac.kr/main.html");
        if (str.indexOf("로그인중입니다") == -1)
            return false;
        else
            return true;
    }

}
