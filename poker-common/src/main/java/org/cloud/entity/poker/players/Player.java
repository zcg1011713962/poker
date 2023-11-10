package org.cloud.entity.poker.players;

import lombok.Data;
import lombok.ToString;
import org.cloud.entity.poker.cards.Card;

import java.util.List;

/**
 * 基础玩家
 */
@Data
@ToString
public class Player {
    // 玩家隐形id
    protected String id;
    // 玩家显性id
    protected String showId;
    // 玩家昵称
    protected String nickName;
    // 手牌
    protected List<Card> hand;
    // 筹码
    protected int chips;

}
