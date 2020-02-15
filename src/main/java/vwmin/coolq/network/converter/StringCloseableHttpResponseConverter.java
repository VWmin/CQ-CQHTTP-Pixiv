package vwmin.coolq.network.converter;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringCloseableHttpResponseConverter implements Converter<CloseableHttpResponse, String> {
    @Override
    public String convert(CloseableHttpResponse value) throws IOException {
        HttpEntity entity = value.getEntity();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line=br.readLine())!=null){
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
