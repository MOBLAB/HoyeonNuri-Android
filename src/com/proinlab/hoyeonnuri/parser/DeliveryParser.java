package com.proinlab.hoyeonnuri.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by PROIN on 13. 5. 18..
 */
public class DeliveryParser {

    public static final int CODE = 0;
    public static final int NAME = 1;
    public static final int TEL = 2;
    public static final int IS_DELIVERY = 3;
    public static final int TIME = 4;
    public static final int LOCATION = 5;
    public static final int CATEGORY = 6;
    public static final int MENU_IMG = 7;
    public static final int MENU_TXT = 8;
    public static final int BANNER = 9;
    public static final int SPONSER = 10;
    public static final int BESTAD = 11;

    public static ArrayList<String[]> DataBaseXMLParse(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<String[]> retrunData = new ArrayList<String[]>();
        try {
            String[] nodeNames = { "code", "name", "tel", "delivery", "time",
                    "location", "category", "menu_img", "menu_txt", "banner",
                    "sponser", "bestad" };

            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            Element data = doc.getDocumentElement();
            NodeList nodes = data.getElementsByTagName("data");

            for (int i = 0; i < nodes.getLength(); i++) {
                String[] strs = new String[nodeNames.length];
                for (int j = 0; j < nodeNames.length; j++)
                    strs[j] = data.getElementsByTagName(nodeNames[j]).item(i).getFirstChild().getNodeValue();
                retrunData.add(strs);
            }
        } catch (Exception e) {
        }

        return retrunData;
    }

}
