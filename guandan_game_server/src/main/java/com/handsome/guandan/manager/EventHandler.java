package com.handsome.guandan.manager;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;

public class EventHandler {

    public void handleEvent(SocketIOClient client, JSONObject req) {
        System.out.println(req.toJSONString());
        switch (req.getString("cmd")) {
            case "wxlogin":
                handleWxLogin(client, req);
                break;
            case "createroom_req":
                handleCreateRoom(client, req);
                break;
            case "joinroom_req":
                handleJoinRoom(client, req);
                break;
            case "enterroom_req":
                handleEnterRoom(client, req);
                break;
            case "player_ready_notify":
                handlePlayerReady(client, req);
                break;
            case "player_start_notify":
                handlePlayerStart(client, req);
                break;
            case "player_rob_notify":  //客户端发送抢地主消息
                handlePlayerRobMaster(client, req);
                break;
            case "chu_bu_card_req":   //客户端发送出牌消息
                handleChuBuCard(client, req);
                break;
            case "chu_card_req":
                handleChuCard(client, req);
                break;
            default:
                break;
        }
    }

    private void handleChuCard(SocketIOClient client, JSONObject req) {

    }

    private void handleChuBuCard(SocketIOClient client, JSONObject req) {

    }

    private void handlePlayerRobMaster(SocketIOClient client, JSONObject req) {

    }

    private void handleWxLogin(SocketIOClient client, JSONObject req) {

    }

    private void handleCreateRoom(SocketIOClient client, JSONObject req) {

    }

    private void handleJoinRoom(SocketIOClient client, JSONObject req) {

    }

    private void handleEnterRoom(SocketIOClient client, JSONObject req) {

    }

    private void handlePlayerReady(SocketIOClient client, JSONObject req) {

    }

    private void handlePlayerStart(SocketIOClient client, JSONObject req) {

    }
}
