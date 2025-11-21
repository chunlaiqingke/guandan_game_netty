package com.handsome.guandan;


import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.handsome.guandan.manager.EventHandler;
import com.handsome.guandan.manager.GameManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameServer {

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.start();
    }

    private static final int PORT = 3000;
    private final SocketIOServer server;

    private final EventHandler eventHandler = new EventHandler();

    public GameServer(){
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(PORT);
        config.setOrigin("*");
        config.setPingTimeout(60000);
        config.setPingInterval(25000);

        server = new SocketIOServer(config);
    }

    public void start(){
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                String sessionId = client.getSessionId().toString();
                System.out.println("client connected: " + sessionId);
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("client disconnected: " + client.getSessionId().toString());
                GameManager.removePlayer(client.getSessionId().toString());
            }
        });

        server.addEventListener("notify", JSONObject.class, new DataListener<JSONObject>() {
            @Override
            public void onData(SocketIOClient client, JSONObject req, AckRequest ackRequest) throws Exception {
                System.out.println("client id: " + client.getSessionId().toString());
                try {
                    eventHandler.handleEvent(client, req);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    log.error("handle event error: ", e);
                }
            }
        });

        server.start();
    }
}
