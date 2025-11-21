package com.handsome.guandan.network;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;

public class SocketHelper {

    public static void sendMessage(String event, Object data, SocketIOClient socket) {
        socket.sendEvent(event, data);
    }

    public static void _notify(String type, Integer result, Object data, Integer callIndex, SocketIOClient socket) {
        JSONObject payload = new JSONObject();
        payload.put("type", type);
        payload.put("data", data == null ? new JSONObject() : data);
        payload.put("result", result);
        payload.put("callBackIndex", callIndex);
        System.out.println("notify = " + payload.toJSONString());
        sendMessage("notify", payload, socket);
    }
}
