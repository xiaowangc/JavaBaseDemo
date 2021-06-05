package com.chige.streamFunction_Lambda;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author wangyc
 * @date 2021/6/5 11:11
 */
@FunctionalInterface
public interface FileReadInterface {

    String process(BufferedReader reader) throws IOException;
}
