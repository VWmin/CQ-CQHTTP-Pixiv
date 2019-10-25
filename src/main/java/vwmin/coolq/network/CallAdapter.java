package vwmin.coolq.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public interface CallAdapter<ResponseT, ReturnT> {
    Type responseType();
    ReturnT adapt(Call<ResponseT> call);


    abstract class Factory {
        public abstract CallAdapter<?, ?> get(Type returnType, Annotation[] annotations);
    }
}
