package vwmin.coolq.network;


import java.io.IOException;

public interface Call<ResponseT> extends Cloneable {

    Response<ResponseT> execute() throws IOException;
}

// 在原本的retrofit中，CallAdapter.adapter()默认构造的返回类型是Call<?>；
// call是注解式HTTP函数执行的真正步骤，本来目的就是简化操作，希望能够通过注解式函数直接得到请求结果，
// 因此，返回Call再执行execute()我觉得不太合理
// 观察retrofit对rxjava的整合，提供的是Observer<>作为对call执行的封装
// 这里就希望今后的我能够回来做了，目前采用返回Call  todo: 对Call的执行进行封装