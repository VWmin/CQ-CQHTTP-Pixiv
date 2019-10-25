package vwmin.coolq.network;

import java.lang.reflect.Type;

public class MyCallAdapter<ResponseT> implements CallAdapter<ResponseT, Response<ResponseT>> {
    @Override
    public Type responseType() {
        return null;
    }

    @Override
    public Response<ResponseT> adapt(Call<ResponseT> call) {
        return call.execute();
    }
}
