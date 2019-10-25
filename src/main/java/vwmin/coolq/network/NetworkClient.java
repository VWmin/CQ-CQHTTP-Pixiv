package vwmin.coolq.network;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NetworkClient<API> {
    private  final String BaseUrl;
    private final Map<Method, ServiceMethod<?>> serviceMethodCache = new ConcurrentHashMap<>();
    private final CallAdapter.Factory callAdapterFactory;
    private API api;

    public NetworkClient(String baseUrl, Class<API> apiClass, CallAdapter.Factory callAdapterFactory){
        this.BaseUrl = baseUrl;
        api = create(apiClass);
        this.callAdapterFactory = callAdapterFactory;
    }

    @SuppressWarnings("unchecked")
    private API create(Class<API> service){
        return (API) Proxy.newProxyInstance(service.getClassLoader(),
                new Class<?>[]{service},
                new InvocationHandler() {
                    private final Object[] emptyArgs = new Object[0];
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return loadServiceMethod(method).invoke(args != null ? args : emptyArgs);
                    }
                });
    }

    private ServiceMethod<?> loadServiceMethod(Method method) {
        ServiceMethod<?> result = serviceMethodCache.get(method);
        if(result != null) return result;

        synchronized (serviceMethodCache){
            result = serviceMethodCache.get(method);
            if(result == null){
                result = ServiceMethod.parseAnnotations(this, method);
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    public API getApi(){
        return api;
    }

    public String getBaseUrl(){return BaseUrl;}

    public  CallAdapter<?, ?> callAdapter(Type returnType, Annotation[] annotations) {
        return callAdapterFactory.get(returnType, annotations);
    }



}
