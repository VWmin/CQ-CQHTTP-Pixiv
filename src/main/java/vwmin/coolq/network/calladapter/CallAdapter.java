package vwmin.coolq.network.calladapter;

import vwmin.coolq.network.Call;
import vwmin.coolq.network.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public interface CallAdapter<ResponseT, ReturnT> {

    Type responseType();
    ReturnT adapt(Call<ResponseT> call);

    abstract class Factory {
        public abstract CallAdapter<?, ?> get(Type returnType, Annotation[] annotations);
        protected static Class<?> getRawType(Type type){
            return Utils.getRawType(type);
        }
    }
}
