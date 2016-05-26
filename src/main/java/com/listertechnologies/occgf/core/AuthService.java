package com.listertechnologies.occgf.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listertechnologies.occgf.api.OccAuthResponse;

@Component
public class AuthService {

    public String authenticate() {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        String url = System.getenv("OCC_API_AUTH_URL");
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        try {
            String user = System.getenv("OCC_API_USER");
            String pass = System.getenv("OCC_API_PASS");
            String postStr = String.format("grant_type=password&username=%s&password=%s", user, pass);
            httppost.setEntity(new StringEntity(postStr));
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

        String responseStr = "";
        try {
            InputStream istr = entity.getContent();
            responseStr = IOUtils.toString(istr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        if (authResponse != null) {
            return authResponse.access_token;
        } else {
            return "";
        }

    }

}
