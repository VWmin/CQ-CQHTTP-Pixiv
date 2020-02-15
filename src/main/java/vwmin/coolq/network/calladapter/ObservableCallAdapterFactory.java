package vwmin.coolq.network.calladapter;

import vwmin.coolq.network.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class ObservableCallAdapterFactory extends CallAdapter.Factory {

    public static ObservableCallAdapterFactory create() {
        return new ObservableCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations) {
        final Type responseType = Utils.getCallResponseType(returnType);
        return new ObservableCallAdapter<>(responseType);
    }
}
