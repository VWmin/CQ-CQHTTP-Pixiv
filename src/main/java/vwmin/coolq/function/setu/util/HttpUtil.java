package vwmin.coolq.function.setu.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpUtil {
    private static HttpUtil instance = null;
    private static CloseableHttpClient client = HttpClients.createDefault();
    private static Gson gson = new Gson();

    private static final String User_agent = "PixivAndroidApp/5.0.134 (Android 6.0.1; D6653)";
    private static final String Accept_language = "zh_CN";

    private HttpUtil(){}

    public static HttpUtil getInstance(){
        if(instance == null) instance = new HttpUtil();
        return instance;
    }

    public HttpTask newTask(String method, String url){
        return new HttpTask().setMethod(method).setUrl(url);
    }

    public HttpTask newTask(String method){return new HttpTask().setMethod(method);}

    public class HttpTask extends HttpRequestBase implements HttpEntityEnclosingRequest {

        private String method;
        private HttpEntity entity;

        public HttpTask setHeaders(Map<String, String> headers){
            headers.put("User-Agent", User_agent);
            headers.put("Accept-Language", Accept_language);
            for (String key : headers.keySet()) {
                this.setHeader(key, headers.get(key));
            }
            return this;
        }

        public HttpTask setBody(Map<String, String> body){
            List<NameValuePair> list = new ArrayList<>();
            for(String key : body.keySet()){
                list.add(new BasicNameValuePair(key, body.get(key)));
            }
            try {
                UrlEncodedFormEntity content = new UrlEncodedFormEntity(list, "utf-8");
                this.setEntity(content);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return this;
        }

        public HttpTask myAddHeader(String name, String value){
            super.addHeader(name, value);
            return this;
        }

        public HttpTask setMethod(String method){
            this.method = method.toUpperCase();
            return this;
        }

        public HttpTask setUrl(String url){
            super.setURI(URI.create(url));
            return this;
        }


        // TODO: 2019/10/21 这部分功能应当交由什么什么factory处理
        public <T> T execute(Class<T> returnType) throws IOException {
            return gson.fromJson(execute(), returnType);
        }

        public String execute() throws IOException {
            CloseableHttpResponse response = client.execute(this);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);

            int statusCode = response.getStatusLine().getStatusCode();
            log.info("get response with status code >> " + statusCode);

            //处理错误
            if(statusCode != 200) throw new IOException("错误的响应码");

            response.close();
            return result;
        }

        public String getMethod() {
            return method;
        }

        public boolean expectContinue() {
            Header expect = this.getFirstHeader("Expect");
            return expect != null && "100-continue".equalsIgnoreCase(expect.getValue());
        }

        public void setEntity(HttpEntity entity){
            this.entity = entity;
        }

        public HttpEntity getEntity(){
            return this.entity;
        }
    }


}
