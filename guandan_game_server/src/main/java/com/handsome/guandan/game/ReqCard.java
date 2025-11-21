package com.handsome.guandan.game;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReqCard implements Serializable {

    private Integer cardid;

    private Card card_data;
}
