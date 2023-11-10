package org.cloud.poker.players;

import lombok.Data;
import lombok.ToString;
import org.cloud.entity.poker.cards.Card;
import org.cloud.entity.poker.players.Player;

/**
 * 德州扑克玩家
 */
@Data
@ToString
public class TexasHoldemPlayer extends Player {

    /**
     * 接收牌
     * @param card
     */
    public void receiveCard(Card card) {
        hand.add(card);
    }



}
