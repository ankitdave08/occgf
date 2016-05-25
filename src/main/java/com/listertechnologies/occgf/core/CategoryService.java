package com.listertechnologies.occgf.core;

import java.util.Arrays;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CategoryService {

    /* public void sendCustomerData(GfCustomerDetail gcd) {
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
    } */

    public void transformAndSend(String categoryCsv) {
        List<String> lines = Arrays.asList(categoryCsv.split("\n"));
        System.out.println("Lines: " + lines.size());
        System.out.println("Tokens:");
        
        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get(i);
            List<String> tokens = Arrays.asList(line.split(","));
            System.out.println(tokens.get(0) + ": " + tokens.get(1) + " - " + tokens.get(3));
        }
    }

    @Scheduled(fixedDelay=5000)
    public void run() {
        // System.out.println("I ran");
    }

}
