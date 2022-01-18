package com.chige.sf;

import com.sensorsdata.webhook.Bootstrap;
import com.sensorsdata.webhook.entry.SfWebhookEntry;
import com.sensorsdata.webhook.processor.SfWebhookProcessor;
import com.sensorsdata.webhook.server.EndpointServer;

import java.util.List;
import java.util.Map;

/**
 * @author wangyc
 * @date 2022/1/4
 */
public class TestSfServer {

    public static void main(String[] args) throws Exception {
        EndpointServer endpointServer = Bootstrap.builder()
          .localPort(12345)
          .webhookProcessor(entries -> {
               for (SfWebhookEntry entry : entries) {
                  System.out.println("触发的用户设备 ID: " + entry.getRequest().getUserProfile().getFirstId());
                  System.out.println("触发的用户登录 ID: " + entry.getRequest().getUserProfile().getSecondId());
                  Map<String, String> params = entry.getRequest().getParams();
                  System.out.println("用户的年龄：" + params.get("age"));
                  System.out.println("用户的等级：" + params.get("level"));
                entry.getResponse().succeeded();
           }
        }).build().startServer();
        endpointServer.join();
    }

}
