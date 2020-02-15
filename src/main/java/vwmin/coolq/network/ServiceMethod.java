package vwmin.coolq.network;


import java.lang.reflect.Method;

/**
 * 解释方法
 * @param <ReturnT>
 */
abstract class ServiceMethod<ReturnT> {

    static <T> ServiceMethod<T> parseAnnotations(NetworkClient networkClient, Method method) {
        RequestFactory requestFactory = RequestFactory.parseAnnotations(networkClient, method);
        return HttpServiceMethod.parseAnnotations(networkClient, method, requestFactory);
    }

    abstract ReturnT invoke(Object[] args);
}
