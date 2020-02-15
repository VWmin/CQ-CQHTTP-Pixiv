package vwmin.coolq.network;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

import static vwmin.coolq.network.Utils.checkNotNull;

/** Http response */
public final class Response<T> {

    public static <T> Response<T> success(T body, CloseableHttpResponse rawResponse) throws IOException {
        checkNotNull(rawResponse, "rawResponse == null");
        return new Response<>(rawResponse, body, false);
    }

    public static <T> Response<T> error(HttpEntity body, CloseableHttpResponse rawResponse) throws IOException {
        checkNotNull(body, "body == null");
        checkNotNull(rawResponse, "rawResponse == null");
        return new Response<>(rawResponse, null, true);
    }


    private final T body;
    private final Boolean error;

    private final int code;
    private final String reason;


    private Response(CloseableHttpResponse rawResponse, T body,
                     Boolean isError) throws IOException {
        this.body = body;
        this.error = isError;

        code = rawResponse.getStatusLine().getStatusCode();
        reason = rawResponse.getStatusLine().getReasonPhrase();

        rawResponse.close();
    }

    public int code(){
        return code;
    }

    public String reason(){
        return reason;
    }

    public T body(){
        return body;
    }

    public boolean hasError(){
        return error;
    }
}
