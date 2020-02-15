package vwmin.coolq.network.calladapter;

import vwmin.coolq.network.Call;
import vwmin.coolq.network.Response;

import java.io.IOException;


public class Observable<T> implements ObservableSource<Response<T>> {
    private final Call<T> originCall;

    Observable(Call<T> originCall){
        this.originCall = originCall;
    }

    public T result() throws IOException {
        Response<T> response = originCall.execute();
        if (!response.hasError()){
            return response.body();
        }

        throw new IOException(response.code() + " " + response.reason());
    }


}
