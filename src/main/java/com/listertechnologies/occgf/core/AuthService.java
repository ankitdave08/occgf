package com.listertechnologies.occgf.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listertechnologies.occgf.api.OccAuthResponse;

public class AuthService {
    public String authenticate() {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost("https://ccadmin-z42a.oracleoutsourcing.com/ccadmin/v1/login");
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        try {
            httppost.setEntity(new StringEntity(
                    "grant_type=password&username=senthilkumar.c@listertechnologies.com&password=Lister_123"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("executing request " + httppost.getRequestLine());

        HttpResponse response = null;

        try {
            response = httpclient.execute(httppost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity = response.getEntity();

        StringBuilder responseStrBuilder = new StringBuilder();
        int c;
        try {
            InputStream istr = entity.getContent();
            c = istr.read();
            while (c != -1) {
                responseStrBuilder.append((char) c);
                c = istr.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String responseStr = responseStrBuilder.toString();

        ObjectMapper mapper = new ObjectMapper();
        OccAuthResponse authResponse = null;
        try {
            authResponse = mapper.readValue(responseStr, OccAuthResponse.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (authResponse != null)
            return authResponse.access_token;
        else
            return "";
    }
}
