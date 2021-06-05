package com.chige.streamFunction_Lambda.实战;

import java.util.List;

/**
 * @author wangyc
 * @date 2021/6/5 12:01
 */
public interface TopicData {

    List<Long> getTopicData(String topic, Integer sortTopic);

}
