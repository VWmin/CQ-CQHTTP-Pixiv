package vwmin.coolq.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class MyCallAdapterFactory extends CallAdapter.Factory {
    public static MyCallAdapterFactory create() {
        return new MyCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations) {

        return new MyCallAdapter<>();
    }
}
