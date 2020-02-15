package vwmin.coolq.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.io.InputStreamReader;

public final class GsonCloseableHttpResponseConverter<T> implements Converter<CloseableHttpResponse, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonCloseableHttpResponseConverter(Gson gson, TypeAdapter<T> adapter){
        this.adapter = adapter;
        this.gson = gson;
    }

    @Override
    public T convert(CloseableHttpResponse value) throws IOException {
        HttpEntity entity = value.getEntity();
        JsonReader reader = gson.newJsonReader(new InputStreamReader(entity.getContent()));
        reader.setLenient(true);
        return adapter.read(reader);
    }
}
