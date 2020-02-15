package vwmin.coolq.network;

import com.google.gson.Gson;
import vwmin.coolq.network.annotation.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static vwmin.coolq.network.Utils.methodError;

/**
 * 将接口注解上的信息解析为一个Http请求，由{@link HttpClientCall}进行后续处理
 */
final class RequestFactory {

    public static RequestFactory parseAnnotations(NetworkClient networkClient, Method method) {
        return new Builder(networkClient, method).build();
    }

    private final Method method;
    private final String baseUrl;
    private final String relativeUrl;
    private final String httpMethod;
    private final String contentType;
    private final Map<String, String> headers;
    private final ParameterHandler<?>[] parameterHandlers;

    private final boolean gotField;
    private final boolean gotQuery;
    private final boolean gotJson;

    RequestFactory(Builder builder){
        this.method = builder.method;
        this.baseUrl = builder.client.getBaseUrl();
        this.relativeUrl = builder.relativeUrl;
        this.httpMethod = builder.httpMethod;
        this.contentType = builder.contentType;
        this.headers = builder.headers;
        this.parameterHandlers = builder.parameterHandlers;

        this.gotField = builder.gotField;
        this.gotQuery = builder.gotQuery;
        this.gotJson = builder.gotJson;
    }

    public String getHttpMethod(){return httpMethod;}

    public Method getMethod(){return method;}

    public Type getResponseType(){return method.getGenericReturnType();}

    HttpUriRequest creat(Object[] args){

        String url = baseUrl + relativeUrl;
        Gson gson = new Gson();

        RequestBuilder requestBuilder = RequestBuilder.create(httpMethod);
        requestBuilder.setUri(url);
        if(!headers.isEmpty()){
            for(String key : headers.keySet()){
                requestBuilder.setHeader(key, headers.get(key));
            }
        }

        List<BasicNameValuePair> queries = new ArrayList<>();
        Map<String, Object> fields = new HashMap<>();

        for(int i=0; i<parameterHandlers.length; i++){
            if(args[i] == null) {
                continue; //跳过未填入的参数
            }
            if(parameterHandlers[i] instanceof ParameterHandler.Query){
                ((ParameterHandler.Query) parameterHandlers[i]).apply(queries, args[i].toString());
            } else if(parameterHandlers[i] instanceof ParameterHandler.Field){
                fields.put(((ParameterHandler.Field) parameterHandlers[i]).getFieldName(), args[i]);
            } else if(parameterHandlers[i] instanceof ParameterHandler.Json){
                StringEntity stringEntity = new StringEntity(gson.toJson(args[i]), "UTF-8");
                stringEntity.setContentType("application/json");
                requestBuilder.setEntity(stringEntity);
            }
        }

        if(gotQuery){
            for (BasicNameValuePair query : queries) {
                requestBuilder.addParameter(query);
            }
        }else if(gotField){
            if(gotJson){
                throw methodError(method, "数据提交只能有一种方式");
            }
            switch (contentType) {
                case "application/x-www-form-urlencoded":
                    List<BasicNameValuePair> pairs = new ArrayList<>();
                    for (String key : fields.keySet()) {
                        pairs.add(new BasicNameValuePair(key, fields.get(key).toString()));
                    }
                    try {
                        requestBuilder.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case "application/json":
                    requestBuilder.setEntity(new StringEntity(gson.toJson(fields), "UTF-8"));
                    break;
                default:
                    break;
            }
        }

        return requestBuilder.build();
    }


    /**
     * 负责信息的包装过程
     */
    static final class Builder{
        private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
        private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");
        private static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);

        final NetworkClient client;
        final Method method;
        final Annotation[] methodAnnotations;
        final Annotation[][] parameterAnnotationsArray;
        final Type[] parameterTypes;

        String relativeUrl;
        String httpMethod;
        private Map<String, String> headers;
        private ParameterHandler<?>[] parameterHandlers;

        boolean hasBody;
        private boolean gotField;
        private boolean gotQuery;
        private String contentType;
        private boolean gotJson;
        private Set<String> relativeUrlParamNames;

        Builder(NetworkClient client, Method method){
            this.client = client;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
        }

        RequestFactory build(){
            for(Annotation annotation : methodAnnotations){
                parseMethodAnnotation(annotation);
            }

            if(httpMethod == null){
                throw methodError(method, "HTTP Method annotation is required.");
            }

            int parameterCount = parameterAnnotationsArray.length;
            this.parameterHandlers = new ParameterHandler<?>[parameterCount];
            for(int p = 0; p<parameterCount; p++){
                parameterHandlers[p] =
                    parseParameter(p, parameterTypes[p], parameterAnnotationsArray[p]);
            }

            if(relativeUrl == null){
                throw methodError(method, "Http %s Url.", httpMethod);
            }

            if(headers == null) {
                headers = new HashMap<>();
            }

            return new RequestFactory(this);
        }

        /**
         * 解析类方法中参数的注解（如{@link Field}）
         * @param index 第几个参数
         * @param parameterType 参数类型
         * @param annotations 参数拥有的所有注解
         * @return 参数注解包装
         */
        private ParameterHandler<?> parseParameter(int index, Type parameterType, Annotation[] annotations) {
            ParameterHandler<?> result = null;
            if(annotations != null){
                for(Annotation annotation : annotations){
                    ParameterHandler<?> annotationAction =
                            parseParameterAnnotation(index, parameterType, annotation);
                    // 非client annotation
                    if(annotationAction == null){
                        continue;
                    }

                    if(result != null){
                        throw Utils.parameterError(method, index,
                                "Multiple client annotation found, only one allowed.");
                    }

                    result = annotationAction;
                }
            }

            if(result == null){
                throw Utils.parameterError(method, index,
                        "No client annotation found.");
            }


            return result;
        }

        /**
         * 解析方法参数注解中的一个注解
         * @param index 第几个注解
         * @param parameterType 参数类型
         * @param annotation 注解
         * @return 参数注解包装
         */
        private ParameterHandler<?> parseParameterAnnotation(int index, Type parameterType, Annotation annotation) {
            if(annotation instanceof Field) {
                // TODO: 2019/8/8 参数类型检查？比如有的不能解析的，不是很懂
                Field field = (Field) annotation;
                String fieldName = field.value();

                this.gotField = true;

                return new ParameterHandler.Field(fieldName);
            }else if(annotation instanceof Query){
                Query query = (Query) annotation;
                String queryName = query.value();
                boolean required = query.required();

                this.gotQuery = true;

                return new ParameterHandler.Query(queryName, required);
            }else if(annotation instanceof Json){
                this.gotJson = true;

                return new ParameterHandler.Json(index);
            }


            else{
                // 任何一个注解都不是
                return null;
            }
        }

        /**
         * 解析类方法上的注解
         * @param annotation 方法上的一个注解
         */
        private void parseMethodAnnotation(Annotation annotation) {
            if(annotation instanceof POST){
                this.contentType = ((POST) annotation).contentType().toLowerCase();
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), false);
            } else if(annotation instanceof GET){
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if(annotation instanceof PUT){
                parseHttpMethodAndPath("PUT", ((PUT) annotation).value(), true);
            } else if(annotation instanceof Headers){
                String[] headersToParse = ((Headers) annotation).value();
                if(headersToParse.length == 0) {
                    throw methodError(method, "@Headers annotation is empty.");
                }
                this.headers = parseHeaders(headersToParse);
            }
        }

        /**
         * 解析{@link Headers}中的内容，属于解析类方法注解的流程
         * @param headers Headers中的值
         * @return k-v map
         */
        private Map<String, String> parseHeaders(String[] headers) {
            Map<String, String> headerMap = new HashMap<>();
            for(String header:headers){
                //检查格式正确性
                int colon = header.indexOf(":");
                if(colon == -1 || colon == 0 || colon == header.length()-1 ){
                    throw methodError(method,
                            "@Headers value must be in the form \"Name: Value\". Found: \"%s\"", header);
                }

                String headerName = header.split(":")[0];
                String headerValue = header.split(":")[1];
                headerMap.put(headerName, headerValue);
            }
            return headerMap;
        }

        /**
         * 解析类方法注解（如{@link GET}、{@link POST}等）中的内容
         * @param httpMethod 请求方法
         * @param value 相对链接
         * @param hasBody 是否存在请求体
         */
        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            // 防止出现两个方法注解
            if(this.httpMethod != null){
                throw methodError(method, "Only one HTTP method is allowed. Found: %s and %s.",
                        this.httpMethod, httpMethod);
            }

            this.httpMethod = httpMethod;
            this.hasBody = hasBody;

            if (value.isEmpty()){
                return;
            }

            // Get the relative URL path and existing query string, if present.
            int question = value.indexOf("?");
            if (question != -1 && question < value.length()-1){
                // Ensure the query string does not have any named parameters.
                String queryParams = value.substring(question+1);
                Matcher queryParamMatcher = PARAM_URL_REGEX.matcher(queryParams);
                if (queryParamMatcher.find()) {
                    throw methodError(method, "URL query string \"%s\" must not have replace block. "
                            + "For dynamic query parameters use @Query.", queryParams);
                }
            }
            this.relativeUrl = value;
            this.relativeUrlParamNames = parsePathParameters(value);
        }

        private Set<String> parsePathParameters(String path) {
            Matcher m = PARAM_URL_REGEX.matcher(path);
            Set<String> patterns = new LinkedHashSet<>();
            while (m.find()) {
                patterns.add(m.group(1));
            }
            return patterns;
        }
    }
}
