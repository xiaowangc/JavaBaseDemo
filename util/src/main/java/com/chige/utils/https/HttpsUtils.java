package com.chige.utils.https;

import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpsUtils extends HttpsClient {

    private static final Logger log = LoggerFactory.getLogger(HttpsUtils.class);

    private HttpsUtils() {
        super();
    }

    public static OkHttpClient initHttpsClient(String path, String password) throws Exception {
        return initHttpsClient(path, password, null);
    }

    /**
     * 初始化https工具
     *
     * @param path
     * @param password
     */
    public static OkHttpClient initHttpsClient(String path, String password, String bodyContentType) throws Exception {
        OkHttpClient httpsClient = null;
        InputStream in = null;
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            in = new FileInputStream(path);
            keyStore.load(in, password.toCharArray());
            httpsClient = new OkHttpClient.Builder()
                    .sslSocketFactory(getSsLSocketFactory(keyStore, password), getX509TrustManager(keyStore))
                    .hostnameVerifier(new TrustAllHostnameVerifier())
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .followRedirects(true)
                    .connectionPool(CONNECTION_POOL)
                    .addInterceptor(new HttpInterceptor(bodyContentType))
                    .build();
        } catch (Exception e) {
            log.error("初始化https client工具失败，原因: ", e);
            throw e;
        } finally {
            if (Objects.nonNull(in)) {
                in.close();
            }
        }
        return httpsClient;
    }


    /**
     * POST请求。
     *
     * @param url
     * @param data
     * @param mediaType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T doPost(String url, String data, HttpMediaType mediaType, Class<T> clz) throws IOException {
        return doPost(CLIENT, url, data, mediaType, clz);
    }

    /**
     * POST请求。
     *
     * @param url
     * @param data
     * @param mediaType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T doPost(OkHttpClient okHttpClient, String url, String data, HttpMediaType mediaType, Class<T> clz) throws IOException {
        RequestBody requestBody = RequestBody.create(data, MEDIA_TYPE_MAP.get(mediaType));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return doRequest(okHttpClient, request, clz);
    }


    /**
     * POST请求
     *
     * @param url
     * @param data
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T doPostForm(String url, Map<String, String> data, Class<T> clz) throws IOException {
        return doPostForm(CLIENT, url, data, clz);
    }

    public static <T> T doPostForm(OkHttpClient okHttpClient, String url, Map<String, String> data, Class<T> clz) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : data.keySet()) {
            builder.add(key, MapUtils.getString(data, key, ""));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("User-Agent", "Mozilla/5.0")
                .build();
        return doRequest(okHttpClient, request, clz);
    }

    /**
     * @param url
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T doGet(String url, Class<T> clz) throws IOException {
        return doGet(CLIENT, url, clz);
    }

    public static <T> T doGet(OkHttpClient okHttpClient, String url, Class<T> clz) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return doRequest(okHttpClient, request, clz);
    }


}