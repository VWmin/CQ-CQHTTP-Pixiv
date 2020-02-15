package vwmin.coolq.network.converter;


import vwmin.coolq.network.NetworkClient;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Convert objects from type to type in HTTP.
 * @param <F> FromType
 * @param <T> ToType
 */
public interface Converter<F, T> {
    T convert(F value) throws IOException;

    abstract class Factory {
        public Converter<CloseableHttpResponse, ?> closeableHttpResponseConverter(
                Type type, Annotation[] annotations, NetworkClient client) {
            return null;
        }

        // 用来干嘛的？
//        public Converter<?, String> stringConverter(
//                Type type, Annotation[] annotations, NetworkClient client) {
//            return null;
//        }
    }
}
