package com.proinlab.hoyeonnuri.parser;


public class NoticeParser {

	public static final int NOTICE_MAIN = 0;
	public static final int NOTICE_MAINTXT = 1;
	public static final int NOTICE_JINLI = 2;
	public static final int NOTICE_HOYEON = 3;
	public static final int NOTICE_REST4 = 4;

	/**
	 * Web page notice.
	 * @return String
	 */
	public static String getNoticeText(String HtmlSource) {
		if (HtmlSource.indexOf("<td align='left' valign='top'>") == -1)
			HtmlSource = "네트워크 상태가 원활하지 않습니다.";
		else {
			HtmlSource = HtmlParser.getStartLocation(HtmlSource, "<td align='left' valign='top'>");
			HtmlSource = HtmlParser.getParsedData(HtmlSource, "<td align='left' valign='top'>", "</td>");
			HtmlSource = HtmlSource.replaceAll("<br />", "");
		}
		return HtmlSource;
	}

	/**
	 * 
	 * @return boolean[] : boolean[NoticeParser.NOTICE_*]
	 */
	public static boolean[] checkNotice(String HtmlSource) {
		boolean bool[] = new boolean[5];
		if (HtmlSource != null) {
			if (HtmlSource.indexOf("<main_pop>NO") == -1)
				bool[NOTICE_MAIN] = true;
			else
				bool[NOTICE_MAIN] = false;

			if (HtmlSource.indexOf("<main_text>NO") == -1)
				bool[NOTICE_MAINTXT] = true;
			else
				bool[NOTICE_MAINTXT] = false;

			if (HtmlSource.indexOf("<rest1>NO") == -1)
				bool[NOTICE_JINLI] = true;
			else
				bool[NOTICE_JINLI] = false;

			if (HtmlSource.indexOf("<rest2>NO") == -1)
				bool[NOTICE_HOYEON] = true;
			else
				bool[NOTICE_HOYEON] = false;

			if (HtmlSource.indexOf("<rest3>NO") == -1)
				bool[NOTICE_REST4] = true;
			else
				bool[NOTICE_REST4] = false;
		} else {
			bool = new boolean[5];
		}
		return bool;
	}
}
