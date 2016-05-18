package com.listertechnologies.occgf.core;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class CustomerService {
    public void sendCustomerData() throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        String encoding = new String(Base64.encodeBase64("156224a2:c59d1ee342e735a1c8c29108f49962bd".getBytes()));
        HttpPost httppost = new HttpPost("https://qa-api.listeremerge.net/api/v1/customer");
        httppost.setHeader("Authorization", "Basic " + encoding);
        httppost.setHeader("Content-Type", "application/json");
        httppost.setEntity(new StringEntity("[{\"customerId\": \"1\",\"firstName\": \"Test1\",\"email1\":\"test1@example.com\"}]"));

        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        InputStream istr = entity.getContent();
        int c = istr.read();
        while (c != -1) {
            System.out.print((char)c);
            c = istr.read();
        }
    }
}
