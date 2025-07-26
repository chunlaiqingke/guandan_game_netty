package com.handsome.guandan.manager;

import com.handsome.guandan.game.Player;
import com.handsome.guandan.game.Room;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

    static Map<String, Room> hall = new HashMap<>();

    static Map<String, Player> players = new HashMap<>();

    /*
    To prevent circular references, the Player class will no longer hold a reference to its room object
     */
    static Map<Player, Room> playerBelongRoom = new HashMap<>();

    /*
    Locate the player by request; not every request needs to send userId
     */
    static Map<String, String> session2Account = new HashMap<>();
}
