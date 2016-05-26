package com.listertechnologies.occgf.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listertechnologies.occgf.api.GfCategory;

@Component
public class CategoryService {

    public void sendCategoryData(List<GfCategory> categoryList) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        String user = System.getenv("GF_API_USER");
        String pass = System.getenv("GF_API_PASS");
        String url = System.getenv("GF_API_CATEGORY_URL");

        String auth = new String(Base64.encodeBase64((user + ":" + pass).getBytes()));

        HttpPost httppost = new HttpPost(url);

        httppost.setHeader("Authorization", "Basic " + auth);
        httppost.setHeader("Content-Type", "application/json");

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(categoryList);
            System.out.println("JSON to be sent to GF: " + json);

            httppost.setEntity(new StringEntity(json));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
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

        int c;
        try {
            InputStream istr = entity.getContent();
            c = istr.read();
            while (c != -1) {
                System.out.print((char) c);
                c = istr.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transformAndSend(String categoryCsv) {
        List<String> lines = Arrays.asList(categoryCsv.split("\n"));

        for (int i = 2; i < lines.size(); i++) { // Skip first two lines
            String line = lines.get(i);
            List<String> tokens = Arrays.asList(line.split(","));

            GfCategory c = new GfCategory();

            // Set ID
            int id = 10 + i; // To avoid collision with existing entries
            c.setCategoryId(new Integer(id).toString());

            // Set category name
            if (StringUtils.isEmpty(tokens.get(1))) {
                c.setCategoryName("Cat" + id);
            } else {
                c.setCategoryName(tokens.get(1));
            }

            // Set category description
            if (StringUtils.isEmpty(tokens.get(3))) {
                c.setCategoryDesc("CatDesc" + id);
            } else {
                c.setCategoryDesc(tokens.get(3));
            }

            sendCategoryData(Arrays.asList(c));
        }
    }

    @Scheduled(fixedDelay=5000)
    public void run() {
        // System.out.println("I ran");
    }

}
