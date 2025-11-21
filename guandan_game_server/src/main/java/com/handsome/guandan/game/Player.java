package com.handsome.guandan.game;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.handsome.guandan.network.SocketHelper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private String nickName;
    private String accountId;
    private String avatarUrl;
    private Long gold;
    private SocketIOClient socket;
//    private Room room;
    private int seatIndex;
    private boolean isReady;
    private List<Card> cards;

    public Player(PlayerInfo info, SocketIOClient socket, int callIndex) {
        this.nickName = info.getNickName();
        this.accountId = info.getAccountId();
        this.avatarUrl = info.getAvatarUrl();
        this.gold = info.getGoldCount();
        this.socket = socket;
//        this.room = null;
        this.seatIndex = 0;
        this.isReady = false;
        this.cards = new ArrayList<>();
    }

    private void _notify(String type, Integer result, Object data, int callBackIndex) {
        SocketHelper._notify(type, result, data, callBackIndex, socket);
    }

    public void sendPlayerJoinRoom(JSONObject data) {
        System.out.println("player join room notify: " + data.toJSONString());
        _notify("player_joinroom_notify", 0, data, 0);
    }

    public void gameStart() {
        _notify("gameStart_notify", 0, new JSONObject(), 0);
    }

    public void sendPlayerChangeManage(JSONObject data) {
        System.out.println("sendPlayerChangeManage: account:" + data.getString("account"));
        _notify("changehousemanage_notify", 0, data, 0);
    }

    public void sendCanRob(Object data) {
        _notify("canrob_notify", 0, data, 0);
    }

    public void sendRobState(JSONObject data) {
        _notify("canrob_state_notify", 0, data, 0);
    }

    public void sendChangeMaster(Object data) {
        _notify("change_master_notify", 0, data, 0);
    }

    public void sendShowBottomCard(Object data) {
        _notify("change_showcard_notify", 0, data, 0);
    }

    public void sendRoomState(Object data) {
        _notify("room_state_notify", 0, data, 0);
    }

    public void sendOtherChuCard(JSONObject data) {
        _notify("other_chucard_notify", 0, data, 0);
    }

    public void sendPlayerReady(String accountId) {
        _notify("player_ready_notify", 0, accountId, 0);
    }

    public void sendCards(List<Card> cards){
        this.cards = cards;
        _notify("pushcard_notify", 0, cards, 0);
    }

    public void sendChuCard(Object data) {
        _notify("can_chu_card_notify",0,data,0);
    }

    public int hashCode(){
        return super.hashCode();
    }

    public void removePushCard(List<ReqCard> cards) {
        if (cards == null || cards.isEmpty()) {
            return;
        }
        for (ReqCard card : cards) {
            this.cards.remove(card.getCard_data());
        }
    }
}