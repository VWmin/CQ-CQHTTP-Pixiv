package vwmin.coolq.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
final class HttpClientCall<ResponseT> implements Call<ResponseT> {
   private final RequestFactory requestFactory;
   private final Object[] args;


    HttpClientCall(RequestFactory requestFactory, Object[] args){
        this.requestFactory = requestFactory;
        this.args = args;
    }

    @Override
    public Response<ResponseT> execute() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpUriRequest request = requestFactory.creat(args);
        CloseableHttpResponse response;
        try {
            response = client.execute(request);
            Type responseType = requestFactory.getResponseType();
            Type genericType = null;
            try {
                genericType = ((ParameterizedType) responseType).getActualTypeArguments()[0];
            }catch (Exception ignored){
                log.info("响应中没有找到没有找到泛型");
            }

            if(genericType != null) responseType = genericType;

            return Response.success(response, responseType, new Gson());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("反正就是不知道出了啥幺蛾子。");
        }

    }
}
