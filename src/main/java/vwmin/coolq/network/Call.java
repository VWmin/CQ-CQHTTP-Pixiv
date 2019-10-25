package vwmin.coolq.network;



public interface Call<ResponseT> extends Cloneable {

    Response<ResponseT> execute();
}
