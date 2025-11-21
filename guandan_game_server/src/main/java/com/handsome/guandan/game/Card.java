package com.handsome.guandan.game;

import lombok.Data;

import java.io.Serializable;

@Data
public class Card implements Serializable {

    private Integer value;

    private Integer shape;

    private Integer king;

    private Integer index = -1;

    public Card(Integer value, Integer shape, Integer king, Integer index) {
        this.value = value;
        this.shape = shape;
        this.king = king;
        this.index = index;
    }
}
