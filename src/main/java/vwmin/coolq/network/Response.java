package vwmin.coolq.network;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;

public final class Response<T> {

    private CloseableHttpResponse rawResponse;
    private Type contentType;
    private Gson gson;



    private Response(CloseableHttpResponse rawResponse, Type contentType, Gson gson){
        this.rawResponse = rawResponse;
        this.contentType = contentType;
        this.gson= gson;
    }

    public static <ResponseT> Response<ResponseT> success(
            CloseableHttpResponse response, Type contentType, Gson gson) {
        return new Response<>(response, contentType, gson);
    }


    public String getStringResponse(){
        final HttpEntity entity = rawResponse.getEntity();
        try {
            String raw = EntityUtils.toString(entity);
            rawResponse.close();
            return Utils.unicodeToString(raw);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public T getResponse(){
        return gson.fromJson(getStringResponse(), contentType);
    }

    public void setGson(Gson gson){
        this.gson = gson;
    }

    @Override
    public String toString() {
        return getStringResponse();
    }
}
