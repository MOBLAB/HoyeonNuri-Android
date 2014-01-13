package com.proinlab.hoyeonnuri.parser;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardParser {
	public static final int KEY1_BOARD_URL = 0;
	public static final int KEY2_TITLE = 1;
	public static final int KEY3_WRITER = 2;
	public static final int KEY4_DATE = 3;
	public static final int KEY5_BOARDNO = 4;
	public static final int KEY6_IS_SECRET = 5;
	public static final int KEY7_IS_NOTICE = 6;
	public static final int KEY8_COMMENT = 7;

	public static ArrayList<HashMap<Integer, String>> getData(String HTMLSource, int page_num) {
		
		ArrayList<HashMap<Integer, String>> retrunList = new ArrayList<HashMap<Integer, String>>();
		HashMap<Integer, String> hashMap;

		for (int i = 0; i < 15; i++) {
			String parseData[] = new String[8];
			if (HTMLSource.indexOf("<tr align=\"center\"  onmouseover=") == -1)
				;
			else {
				HTMLSource = HtmlParser.getStartLocation(HTMLSource, "<tr align=\"center\"  onmouseover=");
				String TempSource = HtmlParser.cutSource(HTMLSource, "onmouseover=\"this.style.backgroundColor='#fafafa'\"", "</td></tr>");
				int secret_test = -1;
				secret_test = TempSource.indexOf("<img src=/img/board_yellow/icon_key.gif border=0 align=absmiddle>");
				if (secret_test != -1)
					parseData[KEY6_IS_SECRET] = "비밀글";
				else
					parseData[KEY6_IS_SECRET] = "일반글";
				if (TempSource.indexOf(")   </td>") != -1)
					parseData[KEY8_COMMENT] = "(" + HtmlParser.getParsedData(TempSource, "</a> (", ")   </td>") + ")";
				else if (TempSource.indexOf(") <img src") != -1)
					parseData[KEY8_COMMENT] = "(" + HtmlParser.getParsedData(TempSource, "</a> (", ") <img src") + ")";
				else
					parseData[KEY8_COMMENT] = "";

				parseData[KEY1_BOARD_URL] = HtmlParser.getParsedData(TempSource, "bbs_free_read.html?", "&key=&orderBy=&cateID=&cateID2=&page=" + page_num);
				parseData[KEY2_TITLE] = HtmlParser.getParsedData(TempSource, "class=\"list\">", "</a>");
				parseData[KEY3_WRITER] = HtmlParser.getParsedData(TempSource, "<b>", "</b>");
				parseData[KEY4_DATE] = HtmlParser.getParsedData(TempSource, "class=\"text_color\">");
				parseData[KEY5_BOARDNO] = HtmlParser.getParsedData(TempSource, "<td height=\"27\">", "<td width=\"1\"></td>", 16);
				if (parseData[KEY5_BOARDNO].length() > 10) {
					parseData[KEY5_BOARDNO] = "공지";
					parseData[KEY7_IS_NOTICE] = "공지글";
				} else
					parseData[KEY7_IS_NOTICE] = "일반글";

				hashMap = createHashMap(parseData);
				retrunList.add(hashMap);
			}
		}
		return retrunList;
	}

	private static HashMap<Integer, String> createHashMap(String[] insertData) {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
		hashMap.put(KEY1_BOARD_URL, insertData[KEY1_BOARD_URL]);
		hashMap.put(KEY2_TITLE, insertData[KEY2_TITLE]);
		hashMap.put(KEY3_WRITER, insertData[KEY3_WRITER]);
		hashMap.put(KEY4_DATE, insertData[KEY4_DATE]);
		hashMap.put(KEY5_BOARDNO, insertData[KEY5_BOARDNO]);
		hashMap.put(KEY6_IS_SECRET, insertData[KEY6_IS_SECRET]);
		hashMap.put(KEY7_IS_NOTICE, insertData[KEY7_IS_NOTICE]);
		hashMap.put(KEY8_COMMENT, insertData[KEY8_COMMENT]);
		return hashMap;
	}
}