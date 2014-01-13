package com.proinlab.hoyeonnuri.parser;

public class HtmlParser {
	public static String getStartLocation(String HtmlString, String startTag) {
		int start = HtmlString.indexOf(startTag);
		HtmlString = HtmlString.substring(start + 3);
		return HtmlString;
	}

    public static String getStartLocation(String HtmlString, String startTag, int startPoint) {
        int start = HtmlString.indexOf(startTag);
        HtmlString = HtmlString.substring(start + startPoint);
        return HtmlString;
    }

	public static String cutSource(String HtmlSource, String startTag, String endTag) {
		int start_point = HtmlSource.indexOf(startTag);
		int end_point = HtmlSource.indexOf(endTag);
		String Temp_HtmlSource = HtmlSource.substring(start_point, end_point);
		return Temp_HtmlSource;
	}

	public static String getParsedData(String HtmlString, String startTag, String endTag) {
		String parsed_data;
		int start = HtmlString.indexOf(startTag);
		int end = HtmlString.indexOf(endTag);
		parsed_data = HtmlString.substring(start + startTag.length(), end);
		return parsed_data;
	}

	public static String getParsedData(String HtmlString, String startTag) {
		String parsed_data;
		int start = HtmlString.indexOf(startTag);
		int end = start + 10 + startTag.length();
		parsed_data = HtmlString.substring(start + startTag.length(), end);
		return parsed_data;
	}

	public static String getParsedData(String HtmlString, String startTag, String endTag, int nearbyEnd) {
		String parsed_data;
		int start = HtmlString.indexOf(startTag);
		int end = HtmlString.indexOf(endTag);
		parsed_data = HtmlString.substring(start + startTag.length() + 1, end - nearbyEnd);
		return parsed_data;
	}

	public static String removeSource(String HtmlString, String start_tag, String end_tag) {
		int start = HtmlString.indexOf(start_tag);
		int end = HtmlString.indexOf(end_tag);
		int endtag_len = end_tag.length();
		String preString;
		String nextString;

		preString = HtmlString.substring(0, start);
		nextString = HtmlString.substring(end + endtag_len);
		HtmlString = preString + nextString;

		return HtmlString;
	}
}
