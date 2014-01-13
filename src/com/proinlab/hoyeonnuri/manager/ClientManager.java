package com.proinlab.hoyeonnuri.manager;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import proinfactory.com.hoyeonnuri.MainActivity;

public class ClientManager {
    public static boolean isClientUsing;

    public String HtmlToString(String addr) {
        String htmlSource = "noSource";
        while(isClientUsing) ;
        isClientUsing = true;
        try {
            HttpGet request = new HttpGet();
            request.setURI(new URI(addr));
            HttpResponse response = MainActivity.httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            htmlSource = EntityUtils.toString(entity, "EUC-KR");
        } catch (Exception e) {
            htmlSource = "noSource";
        }
        isClientUsing = false;
        return htmlSource;
    }

    public String HtmlToString(String addr, String incoding) {
        String htmlSource = "noSource";
        while(isClientUsing) ;
        isClientUsing = true;
        try {
            HttpGet request = new HttpGet();
            request.setURI(new URI(addr));
            HttpResponse response = MainActivity.httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            htmlSource = EntityUtils.toString(entity, incoding);
        } catch (Exception e) {
            htmlSource = "noSource";
        }
        isClientUsing = false;
        return htmlSource;
    }
}
