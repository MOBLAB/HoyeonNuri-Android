package com.proinlab.hoyeonnuri.parser;

public class RestaurantMenuParser {
	public static String getData(String HtmlSource) {

		if (HtmlSource.indexOf("<div class=\"menu_nodate\">") == -1) {
			HtmlSource = HtmlParser.removeSource(HtmlSource, "<SCRIPT LANGUAGE=\"JavaScript\">", "<!-- [s]리스트 반복 부분 -->");
			HtmlSource = HtmlParser.removeSource(HtmlSource, "<!-- [e]검색 결과 -->", "</form>");
			HtmlSource = HtmlSource.replace("/common/css/style.css", "http://mob.korea.ac.kr/hoyeonnuri/style.css");
			HtmlSource = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" + HtmlSource;
		} else {
			HtmlSource = "<br>조회하실 내용이 없습니다";
			HtmlSource = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" + HtmlSource;
		}

		return HtmlSource;
	}
}
