package com.chige.protobuf;


import com.chige.protobuf.proto.IMDataFormatProto;
import com.chige.protobuf.proto.MessageFormat;
import com.chige.protobuf.proto.MsgTypeProto;

/**
 * @author wangyc
 * @date 2024/3/27
 */
public class PbTest {
    public static void main(String[] args) {
        PbTest pbTest = new PbTest();
        pbTest.testMsg();


    }

    public void testMessageFormat() {
        MessageFormat.Message file = MessageFormat.Message.newBuilder()
                .setContent("你好")
                .setReceiverUid("1")
                .setSendTime(1L)
                .build();
        System.out.println(file);
    }

    public void testMsg() {
        IMDataFormatProto.GroupChatMsg groupChatMsg = IMDataFormatProto.GroupChatMsg.newBuilder()
                .setGroupId("群聊idxxx")
                .setBusinessType(102)
                .setContent("群聊死的房间地上")
                .setSenderUid("123")
                .setSenderPid(1)
                .setSendTime(123)
                .setMessageId("sdfasfsdf")
                .build();

        IMDataFormatProto.SendGroupChatReq sendGroupChatReq = IMDataFormatProto.SendGroupChatReq.newBuilder()
                .setMsg(groupChatMsg)
                .build();

        IMDataFormatProto.SendGroupChatRsp sendGroupChatRsp = IMDataFormatProto.SendGroupChatRsp.newBuilder()
                .setMessageId("messageId=sfafsfsd")
                .setGuid(12312L)
                .build();

        IMDataFormatProto.RequestData requestData = IMDataFormatProto.RequestData.newBuilder()
                .setSendGroupChatReq(sendGroupChatReq)
                .build();

        IMDataFormatProto.ResponseData responseData = IMDataFormatProto.ResponseData.newBuilder()
                .setSendGroupChatRsp(sendGroupChatRsp)
                .setMsg("群聊响应数据体:msg")
                .setStatus(1)
                .build();

        IMDataFormatProto.Msg msg = IMDataFormatProto.Msg.newBuilder()
                .setMsgType(MsgTypeProto.MsgType.SEND_TRANSPARENT_PUSH_MSG_REQ)
                .setBusinessType(4)
                .setProtocolVersion(1)
                .setRequest(requestData)
                .setResponse(responseData)
                .setSeqId(1)
                .build();

        System.out.println(msg);
    }
}
