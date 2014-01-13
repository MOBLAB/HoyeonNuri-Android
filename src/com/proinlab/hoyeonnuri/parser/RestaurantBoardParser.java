package com.proinlab.hoyeonnuri.parser;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantBoardParser {
    public static final int KEY1_BOARD_URL = 0;
    public static final int KEY2_TITLE = 1;
    public static final int KEY3_WRITER = 2;
    public static final int KEY4_DATE = 3;
    public static final int KEY5_BOARDNO = 4;
    public static final int KEY6_IS_SECRET = 5;
    public static final int KEY7_IS_NOTICE = 6;

    public static ArrayList<HashMap<Integer, String>> getData(String HTMLSource, int page_num) {
        ArrayList<HashMap<Integer, String>> returnList = new ArrayList<HashMap<Integer, String>>();
        HashMap<Integer, String> hashMap;

        int count_notice = 0;
        int count_num = 0;
        String Tmps = HTMLSource;
        while (Tmps.indexOf("<tr class=\"first-child\">") != -1) {
            Tmps = HtmlParser.getStartLocation(Tmps, "<tr class=\"first-child\">", 2);
            count_notice++;
        }
        Tmps = HTMLSource;
        while (Tmps.indexOf("<td class=\"tit\">") != -1) {
            Tmps = HtmlParser.getStartLocation(Tmps, "<td class=\"tit\">", 2);
            count_num++;
        }
        count_num = count_num - count_notice;
        for (int i = 0; i < count_notice; i++) {
            String parseData[] = new String[8];
            parseData[KEY3_WRITER] = "관리자";
            parseData[KEY6_IS_SECRET] = "일반글";
            parseData[KEY7_IS_NOTICE] = "일반글";

            if (HTMLSource.indexOf("<tr class=\"first-child\">") == -1)
                ;
            else {
                HTMLSource = HtmlParser.getStartLocation(HTMLSource, "<tr class=\"first-child\">", 2);
                String TempSource = HtmlParser.cutSource(HTMLSource, "first-child", "</tr>");

                parseData[KEY1_BOARD_URL] = HtmlParser.getParsedData(TempSource, "javascript:a_view('", "</a>");
                String idx = HtmlParser.cutSource(parseData[KEY1_BOARD_URL], "", "'");
                parseData[KEY1_BOARD_URL] = HtmlParser.getStartLocation(parseData[KEY1_BOARD_URL], "'", 2);
                String num = HtmlParser.cutSource(parseData[KEY1_BOARD_URL], "'", "')");
                parseData[KEY1_BOARD_URL] = "http://sejong.welstory.com/sejong/notice/notice_view.jsp?idx=" + idx + "&num=" + num.substring(1);

                parseData[KEY2_TITLE] = HtmlParser.getParsedData(TempSource, "<td class=\"tit\">", "</a></td>");
                parseData[KEY2_TITLE] = HtmlParser.removeSource(parseData[KEY2_TITLE], "<a href=", "')\">");
                if (parseData[KEY2_TITLE].indexOf("]") != -1)
                    parseData[KEY3_WRITER] = parseData[KEY2_TITLE].substring(0, parseData[KEY2_TITLE].indexOf("]"));
                if (parseData[KEY2_TITLE].indexOf("]") != -1)
                    parseData[KEY2_TITLE] = parseData[KEY2_TITLE].substring(parseData[KEY2_TITLE].indexOf("]") + 1);
                while (parseData[KEY2_TITLE].charAt(0) == ' ')
                    parseData[KEY2_TITLE] = parseData[KEY2_TITLE].substring(1);
                parseData[KEY2_TITLE] = parseData[KEY2_TITLE].replaceAll("&lt;", "<");
                parseData[KEY2_TITLE] = parseData[KEY2_TITLE].replaceAll("&gt;", ">");

                TempSource = HtmlParser.getStartLocation(TempSource, "<td>", 2);
                TempSource = HtmlParser.getStartLocation(TempSource, "<td>", 2);
                TempSource = HtmlParser.getStartLocation(TempSource, "</td>", 2);
                parseData[KEY4_DATE] = HtmlParser.getParsedData(TempSource, "<td>", "</td>");

                parseData[KEY5_BOARDNO] = "공지";
                parseData[KEY7_IS_NOTICE] = "공지글";

                hashMap = createHashMap(parseData);
                returnList.add(hashMap);
            }
        }
        return returnList;
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
        return hashMap;
    }
}
