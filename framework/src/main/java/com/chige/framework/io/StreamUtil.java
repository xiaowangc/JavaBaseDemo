package com.chige.framework.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

    private static final int BUFFER_SIZE = 1024;

    public static byte[] readStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int count;
        while ((count = inputStream.read(buffer, 0, BUFFER_SIZE)) > 0) {
            byteArrayOutputStream.write(buffer, 0, count);
        }
        byte[] result = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return result;
    }
}