package com.proinlab.hoyeonnuri.parser;

import java.util.ArrayList;
import java.util.HashMap;

public class LibraryParser {
	public static final int KEY1_BOARD_URL = 0;
	public static final int KEY2_LIBRARY_NAME = 1;
	public static final int KEY3_ENTIRE_SPACE = 2;
	public static final int KEY4_USEING_SPACE = 3;
	public static final int KEY5_REMAIN_SPACE = 4;
	public static final int KEY6_RATE = 5;

	public static ArrayList<HashMap<Integer, String>> parsing_process(String HTMLSource) {
		ArrayList<HashMap<Integer, String>> returnList = new ArrayList<HashMap<Integer, String>>();
		HashMap<Integer, String> parsed_list;

		String parsedData[] = new String[6];

		int count = 1;
		String countSource = HTMLSource;
		while (countSource.indexOf("onmouseout=\"javascript:this.style.backgroundColor=") != -1) {
			count++;
			countSource = HtmlParser.getStartLocation(countSource, "onmouseout=\"javascript:this.style.backgroundColor=");
		}

		if (count == 1) {
			parsedData[KEY2_LIBRARY_NAME] = "네트워크 상태를 체크해주세요";
			parsed_list = createHashMap(parsedData);
			returnList.add(parsed_list);
		} else {
			for (int i = 0; i < count - 1; i++) {
				HTMLSource = HtmlParser.getStartLocation(HTMLSource, "onmouseout=\"javascript:this.style.backgroundColor=");

				String TempSource = HtmlParser.cutSource(HTMLSource, "javascript:this.style.backgroundColor=", "</FONT></TD></TR>");

				parsedData[KEY1_BOARD_URL] = HtmlParser.getParsedData(TempSource, "<A HREF=\"", "\">&nbsp;");
				parsedData[KEY2_LIBRARY_NAME] = HtmlParser.getParsedData(TempSource, "\">&nbsp;", "</A></FONT></TD><TD ALIGN=\"CENTER\">");

				TempSource = HtmlParser.getStartLocation(TempSource, "</FONT></TD><TD ALIGN=\"CENTER\"><FONT SIZE=-1>&nbsp;");
				parsedData[KEY3_ENTIRE_SPACE] = HtmlParser.getParsedData(TempSource, "</TD><TD ALIGN=\"CENTER\"><FONT SIZE=-1>&nbsp;", "</FONT></TD><TD ALIGN=\"CENTER\"><FONT SIZE=-1>&nbsp;");

				TempSource = HtmlParser.getStartLocation(TempSource, "</FONT></TD><TD ALIGN=\"CENTER\"><FONT SIZE=-1>&nbsp;");
				parsedData[KEY4_USEING_SPACE] = HtmlParser.getParsedData(TempSource, "</TD><TD ALIGN=\"CENTER\"><FONT SIZE=-1>&nbsp;", "</FONT></TD><TD ALIGN=\"CENTER\"><FONT COLOR=\"blue\" SIZE=-1>");

				parsedData[KEY5_REMAIN_SPACE] = HtmlParser.getParsedData(TempSource, "</FONT></TD><TD ALIGN=\"CENTER\"><FONT COLOR=\"blue\" SIZE=-1>&nbsp;", "</FONT></TD><TD ALIGN=\"CENTER\"><FONT SIZE=-1>&nbsp;");

				parsedData[KEY6_RATE] = HtmlParser.getParsedData(TempSource, "</FONT></TD><TD ALIGN=\"CENTER\"><FONT SIZE=-1>&nbsp;", "%");
				parsed_list = createHashMap(parsedData);
				returnList.add(parsed_list);
			}
		}
		
		return returnList;
	}

	private static HashMap<Integer, String> createHashMap(String[] insertData) {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        if(insertData==null)
            return hashMap;
		hashMap.put(KEY1_BOARD_URL, insertData[KEY1_BOARD_URL]);
		hashMap.put(KEY2_LIBRARY_NAME, insertData[KEY2_LIBRARY_NAME]);
		hashMap.put(KEY3_ENTIRE_SPACE, insertData[KEY3_ENTIRE_SPACE]);
		hashMap.put(KEY4_USEING_SPACE, insertData[KEY4_USEING_SPACE]);
		hashMap.put(KEY5_REMAIN_SPACE, insertData[KEY5_REMAIN_SPACE]);
		hashMap.put(KEY6_RATE, insertData[KEY6_RATE]);
		return hashMap;
	}
}
