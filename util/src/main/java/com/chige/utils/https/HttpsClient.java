package com.chige.utils.https;

import com.chige.framework.error.ApplicationException;
import com.chige.framework.error.CommonError;
import com.chige.framework.io.StreamUtil;
import com.chige.utils.json.JacksonUtil;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyc
 * @date 2023/4/22
 */
public abstract class HttpsClient {
    private static final String ALGORITHM = "SunX509";
    private static final int CODE = 400;
    protected static final Map<HttpMediaType, MediaType> MEDIA_TYPE_MAP = new HashMap<>(4);
    protected static final ConnectionPool CONNECTION_POOL = new ConnectionPool(200, 5, TimeUnit.MINUTES);
    protected static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .sslSocketFactory(getSsLSocketFactory(), getX509TrustManager())
            .hostnameVerifier(new TrustAllHostnameVerifier())
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .connectionPool(CONNECTION_POOL)
            .addInterceptor(new HttpInterceptor())
            .build();


    static {
        for (HttpMediaType httpMediaType : HttpMediaType.values()) {
            MediaType mediaType = MediaType.get(httpMediaType.getType());
            MEDIA_TYPE_MAP.put(httpMediaType, mediaType);
        }
    }
    protected HttpsClient() {}

    protected static <T> T doRequest(OkHttpClient okHttpClient, Request request, Class<T> clz) throws IOException {
        byte[] bytes = doRequest(okHttpClient, request);
        return JacksonUtil.bytesToObject(bytes, clz);
    }

    @SuppressWarnings("ConstantConditions")
    protected static byte[] doRequest(OkHttpClient okHttpClient, Request request) throws IOException {
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.code() >= CODE) {
                throw new IOException(String.valueOf(response.code()));
            }
            InputStream inStream = response.body().byteStream();
            return StreamUtil.readStream(inStream);
        }
    }


    /**
     * trust manager
     *
     * @return
     */
    protected static TrustManager[] getTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
    }

    /**
     * x509 truest manager
     *
     * @return
     */
    protected static X509TrustManager getX509TrustManager() {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = getTrustManager();
            return (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            throw new ApplicationException(new CommonError(-1, "系统异常"));
        }
    }

    /**
     * x509 truest manager
     *
     * @return
     */
    protected static X509TrustManager getX509TrustManager(KeyStore keyStore) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            return (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            throw new ApplicationException(new CommonError(-1, "系统异常"));
        }
    }

    /**
     * ssl socket factory
     *
     * @return
     */
    protected static SSLSocketFactory getSsLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new ApplicationException(new CommonError(-1, "无效证书"));
        }
    }

    /**
     * ssl socket factory
     *
     * @return
     */
    protected static SSLSocketFactory getSsLSocketFactory(KeyStore keyStore, String password) {
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(ALGORITHM);
            keyManagerFactory.init(keyStore, password.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new ApplicationException(new CommonError(-1, "无效证书"));
        }
    }

    protected static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;  // 不检查站点域名与站点证书的域名是否匹配
        }
    }

}
