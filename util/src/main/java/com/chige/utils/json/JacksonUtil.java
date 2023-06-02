package com.chige.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linyc
 * @version JacksonUtil 2021/1/25 10:57
 */
public class JacksonUtil {

    private static final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转换成json
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String objectToJson(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Parse Object to Json error", e);
            return null;
        }
    }

    /**
     * 将json转换成对象Class
     *
     * @param src
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String src, Class<T> clz) {
        if (StringUtils.isEmpty(src) || clz == null) {
            return null;
        }
        try {
            return clz.equals(String.class) ? (T) src : objectMapper.readValue(src, clz);
        } catch (Exception e) {
            logger.warn("Parse Json to Object error", e);
            return null;
        }
    }

    /**
     *
     * @param bytes
     * @param clz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T bytesToObject(byte[] bytes, Class<T> clz) {
        try {
            return clz.equals(String.class) ? (T) new String(bytes) : objectMapper.readValue(bytes, clz);
        } catch (Exception e) {
            logger.error("Parse bytes to object error", e);
            return null;
        }
    }

    /**
     * 将json转换成对象Class
     *
     * @param src
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToListObject(String src, Class<T> clz) {
        if (StringUtils.isEmpty(src) || clz == null) {
            return null;
        }
        try {
            return objectMapper.readValue(src, getCollectionType(ArrayList.class, clz));
        } catch (Exception e) {
            logger.warn("Parse Json to Object error", e);
            return null;
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}