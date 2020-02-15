package vwmin.coolq.network;


import vwmin.coolq.network.calladapter.CallAdapter;
import vwmin.coolq.network.converter.Converter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 将方法转化为http请求
 * @param <ResponseT>
 * @param <ReturnT>
 */
final class HttpServiceMethod<ResponseT, ReturnT> extends ServiceMethod<ReturnT>{

    static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(
            NetworkClient client, Method method, RequestFactory requestFactory){

        CallAdapter<ResponseT, ReturnT> callAdapter = creatCallAdapter(client, method);

        Type responseType = callAdapter.responseType();


        Converter<CloseableHttpResponse, ResponseT> responseConverter =
                    createResponseConverter(client, method, responseType);


        CloseableHttpClient callFactory = client.callFactory;

        return new HttpServiceMethod<>(requestFactory, callFactory, callAdapter, responseConverter);
    }


    private static <ResponseT> Converter<CloseableHttpResponse, ResponseT> createResponseConverter(
            NetworkClient client, Method method, Type responseType){
        Annotation[] annotations = method.getAnnotations();
        try{
            //noinspection unchecked
            return client.closeableHttpResponseConverter(responseType, annotations);
        }catch (RuntimeException e){
            throw Utils.methodError(method, e, "Unable to create converter for %s", responseType);
        }
    }


    private static <ResponseT, ReturnT> CallAdapter<ResponseT, ReturnT> creatCallAdapter(
            NetworkClient client, Method method) {
        Type returnType = method.getGenericReturnType();
        Annotation[] annotations = method.getAnnotations();
        try{
            //noinspection unchecked
            return (CallAdapter<ResponseT, ReturnT>) client.callAdapter(returnType, annotations);
        }catch (RuntimeException e) { // Wide exception range because factories are user code.
            throw Utils.methodError(method, e, "Unable to create call adapter for %s", returnType);
        }
    }

    private final RequestFactory requestFactory;
    private final CloseableHttpClient callFactory;
    private final CallAdapter<ResponseT, ReturnT> callAdapter;
    private final Converter<CloseableHttpResponse, ResponseT> responseConverter;

    private HttpServiceMethod(RequestFactory requestFactory, CloseableHttpClient callFactory,
                              CallAdapter<ResponseT, ReturnT> callAdapter,
                              Converter<CloseableHttpResponse, ResponseT> responseConverter) {
        this.requestFactory = requestFactory;
        this.callFactory = callFactory;
        this.callAdapter = callAdapter;
        this.responseConverter = responseConverter;
    }

    @Override
    final ReturnT invoke(Object[] args){
        return callAdapter.adapt(
                new HttpClientCall<>(requestFactory, args, callFactory, responseConverter));
    }

}
