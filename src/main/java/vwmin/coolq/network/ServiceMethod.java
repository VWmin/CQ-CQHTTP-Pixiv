package vwmin.coolq.network;


import java.lang.reflect.Method;

abstract class ServiceMethod<T> {
    static <T> ServiceMethod<T> parseAnnotations(NetworkClient networkClient, Method method) {
        RequestFactory requestFactory = RequestFactory.parseAnnotations(networkClient, method);
        return HttpServiceMethod.parseAnnotations(networkClient, method, requestFactory);
    }

    abstract T invoke(Object[] args);
}
