package com.listertechnologies.occgf.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listertechnologies.occgf.api.GfCustomerDetail;
import com.listertechnologies.occgf.api.OccCustomerDetail;
import com.listertechnologies.occgf.api.OccProfile;

@Component
public class CustomerService {

    public void sendCustomerData(GfCustomerDetail gcd) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        String user = System.getenv("GF_API_USER");
        String pass = System.getenv("GF_API_PASS");
        String url = System.getenv("GF_API_URL");

        String auth = new String(Base64.encodeBase64((user + ":" + pass).getBytes()));

        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Authorization", "Basic " + auth);
        httppost.setHeader("Content-Type", "application/json");

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Arrays.asList(gcd));
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

    public void transformAndSend(OccCustomerDetail ocd) {
        GfCustomerDetail gcd = new GfCustomerDetail();

        if (ocd != null) {
            OccProfile ocdp = ocd.getProfile();
            if (ocdp != null) {
                gcd.setCustomerId(ocd.getProfileId());
                gcd.setFirstName(ocdp.getFirstName());
                gcd.setLastName(ocdp.getLastName());
                gcd.setEmail1(ocdp.getEmail());
            }
        }

        sendCustomerData(gcd);
    }
    
    /*@Scheduled(fixedDelay=5000)
    public void run() {
        System.out.println("I ran");
    }*/

}
