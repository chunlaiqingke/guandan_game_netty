package com.handsome.guandan.manager;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.handsome.guandan.game.Player;
import com.handsome.guandan.game.PlayerInfo;
import com.handsome.guandan.game.Room;
import com.handsome.guandan.network.SocketHelper;

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
        int callindex = req.getInteger("callindex");
        Player player = GameManager.getPlayer(client.getSessionId().toString());
        Room belongRoom = GameManager.getBelongRoom(player);
        belongRoom.playerChuCard(player, req, (err, res) -> {
            SocketHelper._notify("chu_card_res", err, res.get("data"), callindex, client);
        });

    }

    private void handleChuBuCard(SocketIOClient client, JSONObject req) {
        Player player = GameManager.getPlayer(client.getSessionId().toString());
        Room belongRoom = GameManager.getBelongRoom(player);
        belongRoom.playerChuBuCard(player, req.getJSONArray("data"));
    }

    private void handlePlayerRobMaster(SocketIOClient client, JSONObject req) {
        Player player = GameManager.getPlayer(client.getSessionId().toString());
        Room belongRoom = GameManager.getBelongRoom(player);
        belongRoom.playerRobMaster(player, req.getInteger("data"));
    }

    private void handleWxLogin(SocketIOClient client, JSONObject req) {
        JSONObject json = new JSONObject();
        JSONObject data = req.getJSONObject("data");
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setAccountId(data.getString("accountID"));
        playerInfo.setAvatarUrl(data.getString("avatarUrl"));
        playerInfo.setNickName(data.getString("nickName"));
        playerInfo.setGoldCount(data.getLong("goldcount"));
        Player player = new Player(playerInfo, client, req.getInteger("callindex"));
        GameManager.addPlayer(player);
        SocketHelper._notify("login_resp", 0, json, req.getInteger("callindex"), client);
    }

    private void handleCreateRoom(SocketIOClient client, JSONObject req) {
        JSONObject data = req.getJSONObject("data");
        Player player = GameManager.getPlayer(client.getSessionId().toString());
        Room room = new Room(data, player);
        GameManager.createRoom(room);

        JSONObject resData = new JSONObject();
        resData.put("roomid", room.getRoomId());
        resData.put("bottom", room.getBottom());
        resData.put("rate", room.getRate());
        SocketHelper._notify("createroom_resp", 0, resData, req.getInteger("callindex"), client);
    }

    private void handleJoinRoom(SocketIOClient client, JSONObject req) {
        JSONObject data = req.getJSONObject("data");
        Player player = GameManager.getPlayer(client.getSessionId().toString());
        Room room = GameManager.getRoom(data.getString("roomid"));
        room.joinPlayer(player);
        JSONObject resData = new JSONObject();
        resData.put("roomid", room.getRoomId());
        resData.put("bottom", room.getBottom());
        resData.put("rate", room.getRate());
        resData.put("gold", room.getGold());
        SocketHelper._notify("joinroom_resp", 0, resData, req.getInteger("callindex"), client);
    }

    private void handleEnterRoom(SocketIOClient client, JSONObject req) {
        JSONObject data = req.getJSONObject("data");
        Player player = GameManager.getPlayer(client.getSessionId().toString());
        Room belongRoom = GameManager.getBelongRoom(player);
        JSONObject enterRoomParam = belongRoom.enterRoom(player);
        SocketHelper._notify("createroom_resp", 0, enterRoomParam, req.getInteger("callindex"), client);
    }

    private void handlePlayerReady(SocketIOClient client, JSONObject req) {
        Player player = GameManager.getPlayer(client.getSessionId().toString());
        player.setReady(true);
        Room belongRoom = GameManager.getBelongRoom(player);
        belongRoom.playerReady(player);
    }

    private void handlePlayerStart(SocketIOClient client, JSONObject req) {
        Player player = GameManager.getPlayer(client.getSessionId().toString());
        Room belongRoom = GameManager.getBelongRoom(player);
        int err_code = belongRoom.playerStart(player);
        SocketHelper._notify("playerStart", err_code, null, req.getInteger("callindex"), client);
    }
}
