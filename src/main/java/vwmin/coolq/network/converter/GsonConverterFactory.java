package vwmin.coolq.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import vwmin.coolq.network.NetworkClient;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public final class GsonConverterFactory extends Converter.Factory{
    public static GsonConverterFactory create(){
        return create(new Gson());
    }

    private static GsonConverterFactory create(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        return new GsonConverterFactory(gson);
    }

    private final Gson gson;

    private GsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<CloseableHttpResponse, ?> closeableHttpResponseConverter(
            Type type, Annotation[] annotations, NetworkClient client){
        if ("java.lang.String".equals(type.getTypeName())){
            return new StringCloseableHttpResponseConverter();
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonCloseableHttpResponseConverter<>(gson, adapter);
    }
}
