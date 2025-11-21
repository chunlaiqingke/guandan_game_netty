package com.handsome.guandan.config;

import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GameConfig {

    Properties config = new Properties();

    static Map<Integer, RoomConfig> roomConfigMap = new HashMap<>();

    public static GameConfig instance = new GameConfig();

    private GameConfig (){
        InputStream inputStream = GameConfig.class.getClassLoader()
                .getResourceAsStream("config.properties");
        try {
            config.load(inputStream);
            for(Object key : config.keySet()) {
                Integer keyInt = Integer.valueOf((String)key);
                RoomConfig roomConfig = JSONObject.parseObject(config.getProperty(keyInt.toString()), RoomConfig.class);
                roomConfigMap.put(keyInt, roomConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RoomConfig getRate(Integer rate){
        return roomConfigMap.get(rate);
    }
}
