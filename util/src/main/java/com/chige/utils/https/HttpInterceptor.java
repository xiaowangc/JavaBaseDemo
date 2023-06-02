package com.chige.utils.https;

import com.chige.utils.json.JsonUtils;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;

public class HttpInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);
    private static final Logger remotingLogger = LoggerFactory.getLogger("remote.digest");
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    private String bodyContentType;

    public HttpInterceptor(String bodyContentType) {
        this.bodyContentType = bodyContentType;
    }

    public HttpInterceptor() {
        this(null);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        long startTime = System.currentTimeMillis();
        Request request = chain.request();
        Response response = null;
        String errorMsg = null;
        boolean success = true;
        try {
            response = chain.proceed(request);
            reSettingBodyContentType(response);
            return response;
        } catch (IOException e) {
            logger.error("request error", e);
            errorMsg = e.getMessage();
            success = false;
            throw e;
        } finally {
            String responseContent;
            if (success) {
                responseContent = null == response ? "" : getResponseBody(response.body());
            } else {
                responseContent = errorMsg;
            }
            RemoteDigestLog remoteDigestLog = new RemoteDigestLog();
            remoteDigestLog.setStartTime(new Date(startTime));
            remoteDigestLog.setElapseTime(System.currentTimeMillis() - startTime);
            remoteDigestLog.setResponse(responseContent);
            remoteDigestLog.setUri(request.url().toString());
            remoteDigestLog.setRequest(getParam(request.body()));
            remoteDigestLog.setSuccess(success);
            remoteDigestLog.setDomain(request.url().host());
            remotingLogger.info("{}", JsonUtils.toJSONString(remoteDigestLog));
        }
    }

    private void reSettingBodyContentType(Response response) {
        ResponseBody body = response.body();
        Class<? extends ResponseBody> aClass = body.getClass();
        try {
            Field field = aClass.getDeclaredField("contentTypeString");
            field.setAccessible(true);
            String contentTypeString = String.valueOf(field.get(body));
            contentTypeString = StringUtils.isBlank(bodyContentType) ? contentTypeString : bodyContentType;
            field.set(body, contentTypeString);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class RemoteDigestLog {

        private static final long serialVersionUID = 1L;
        private String uri;
        private String requestId;
        private String request;
        private String response;
        private Date startTime;
        private long elapseTime;
        private boolean success;
        private String domain;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public long getElapseTime() {
            return elapseTime;
        }

        public void setElapseTime(long elapseTime) {
            this.elapseTime = elapseTime;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        @SuppressWarnings("unused")
        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }

    @SuppressWarnings({"TooBroadScope", "ConstantConditions"})
    private static String getResponseBody(ResponseBody body) throws IOException {
        if (body == null) {
            return null;
        }
        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.getBuffer();
        Charset charset = UTF8;
        MediaType contentType = body.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException ignored) {
            }
        }
        Buffer newBuffer = buffer.clone();
        return newBuffer.readString(charset);
    }

    private static String getParam(RequestBody requestBody) {
        String logParam = "";
        if (null == requestBody) {
            return logParam;
        }
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
            logParam = buffer.readUtf8();
        } catch (IOException e) {
            logger.error("get param has unknown error", e);
        }
        return logParam;
    }

}