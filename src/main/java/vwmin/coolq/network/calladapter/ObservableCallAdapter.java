package vwmin.coolq.network.calladapter;

import vwmin.coolq.network.Call;

import java.lang.reflect.Type;

public final class ObservableCallAdapter<ResponseT> implements CallAdapter<ResponseT, Object> {

    private final Type responseType;
    public ObservableCallAdapter(Type responseType){
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<ResponseT> call) {
        return new Observable<>(call);
    }
}
