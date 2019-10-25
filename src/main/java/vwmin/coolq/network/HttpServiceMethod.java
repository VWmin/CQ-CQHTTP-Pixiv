package vwmin.coolq.network;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

abstract class HttpServiceMethod<ResponseT, ReturnT> extends ServiceMethod<ReturnT>{

    static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(
            NetworkClient client, Method method, RequestFactory requestFactory){

        Annotation[] annotations = method.getAnnotations();
        Type returnType = method.getGenericReturnType();
        CallAdapter<ResponseT, ReturnT> callAdapter = creatCallAdapter(client, method, returnType, annotations);

        return new CallAdapted<>(requestFactory, callAdapter);
    }

    private static <ResponseT, ReturnT> CallAdapter<ResponseT, ReturnT> creatCallAdapter(
            NetworkClient client, Method method, Type returnType, Annotation[] annotations) {
        return (CallAdapter<ResponseT, ReturnT>) client.callAdapter(returnType, annotations);
    }

    private final RequestFactory requestFactory;

    HttpServiceMethod(RequestFactory requestFactory){
        this.requestFactory = requestFactory;
    }

    @Override
    final ReturnT invoke(Object[] args){
        Call<ResponseT> call = new HttpClientCall<>(requestFactory, args);
        return adapt(call, args);
    }

    protected abstract ReturnT adapt(Call<ResponseT> call, Object[] args);

    static final class CallAdapted<ResponseT, ReturnT> extends HttpServiceMethod<ResponseT, ReturnT> {
        private final CallAdapter<ResponseT, ReturnT> callAdapter;

        CallAdapted(RequestFactory requestFactory, CallAdapter<ResponseT, ReturnT> callAdapter){
            super(requestFactory);
            this.callAdapter = callAdapter;
        }

        @Override
        protected ReturnT adapt(Call<ResponseT> call, Object[] args) {
            return callAdapter.adapt(call);
        }
    }


}
