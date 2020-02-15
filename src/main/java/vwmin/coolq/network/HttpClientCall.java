package vwmin.coolq.network;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import vwmin.coolq.network.converter.Converter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

@Slf4j
final class HttpClientCall<ResponseT> implements Call<ResponseT> {
   private final RequestFactory requestFactory;
   private final Object[] args;
   private final CloseableHttpClient callFactory;
   private final Converter<CloseableHttpResponse, ResponseT> responseConverter;

   private final RequestConfig requestConfig;


    HttpClientCall(RequestFactory requestFactory, Object[] args,
                   CloseableHttpClient callFactory,
                   Converter<CloseableHttpResponse, ResponseT> responseConverter){
        this.requestFactory = requestFactory;
        this.args = args;
        this.callFactory = callFactory;
        this.responseConverter = responseConverter;

       this.requestConfig = RequestConfig.custom()
               //链接超时时间
               .setConnectTimeout(5000)
               //获取数据超时时间
               .setSocketTimeout(5000)
               //从连接池获取链接超时时间
               .setConnectionRequestTimeout(1000)
               .build();
   }

    @Override
    public Response<ResponseT> execute() throws IOException {
        HttpRequestBase request = (HttpRequestBase) requestFactory.creat(args);
        log.info("正在请求 >> " + request.getURI().toString());
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36");
        request.setConfig(requestConfig);
        return parseResponse(callFactory.execute(request));
    }

    private Response<ResponseT> parseResponse(CloseableHttpResponse rawResponse) throws IOException {
        // FIXME: 2020/2/10 什么时候关闭连接？
        // 如果连接失败，保存好信息，关闭连接
        // 如果连接成功，
        int code = rawResponse.getStatusLine().getStatusCode();
        log.info("响应码 >> " + code);
        if(code < 200 || code >= 300) {
            HttpEntity entity = rawResponse.getEntity();
            return Response.error(entity, rawResponse);
        }

        if(code == 204 || code == 205){
            return Response.success(null, rawResponse);
        }

        ResponseT body = responseConverter.convert(rawResponse);
        return Response.success(body, rawResponse);
    }
}
