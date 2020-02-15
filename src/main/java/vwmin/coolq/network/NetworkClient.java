package vwmin.coolq.network;


import vwmin.coolq.network.calladapter.CallAdapter;
import vwmin.coolq.network.converter.Converter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static vwmin.coolq.network.Utils.checkNotNull;

public class NetworkClient<API> {
    final CloseableHttpClient callFactory;
    private final String BaseUrl;
    private final Map<Method, ServiceMethod<?>> serviceMethodCache = new ConcurrentHashMap<>();
    private final CallAdapter.Factory callAdapterFactory;
    private final Converter.Factory converterFactory;
    private API api;

    public NetworkClient(String baseUrl, Class<API> apiClass,
                         CallAdapter.Factory callAdapterFactory,
                         Converter.Factory converterFactory){
        this.BaseUrl = baseUrl;
        api = create(apiClass);
        this.callAdapterFactory = callAdapterFactory;
        this.converterFactory = converterFactory;
        this.callFactory = HttpClients.createDefault();
    }

    @SuppressWarnings("unchecked")
    private API create(Class<API> service){
        try{
            return (API) Proxy.newProxyInstance(service.getClassLoader(),
                    new Class<?>[]{service},
                    new InvocationHandler() {
                        private final Object[] emptyArgs = new Object[0];
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            return loadServiceMethod(method).invoke(args != null ? args : emptyArgs);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private ServiceMethod<?> loadServiceMethod(Method method) {
        ServiceMethod<?> result = serviceMethodCache.get(method);
        if(result != null) {
            return result;
        }

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

    public <ResponseT> Converter<CloseableHttpResponse, ResponseT> closeableHttpResponseConverter(Type responseType, Annotation[] annotations) {
        checkNotNull(responseType, "responseType == null");
        checkNotNull(annotations, "annotations == null");

        Converter<CloseableHttpResponse, ?> converter = converterFactory.closeableHttpResponseConverter(responseType, annotations, this);
        // noinspection unchecked
        return (Converter<CloseableHttpResponse, ResponseT>) converter;
    }


}
