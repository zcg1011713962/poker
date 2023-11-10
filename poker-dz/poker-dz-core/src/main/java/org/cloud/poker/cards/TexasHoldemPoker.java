package org.cloud.poker.cards;

import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.poker.cards.Card;
import org.cloud.entity.poker.cards.Poker;
import org.cloud.poker.players.TexasHoldemPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 德州扑克操作牌
 */
@Slf4j
public class TexasHoldemPoker implements Poker {

    /**
     * 初始化发两张底牌
     * @param players
     */
    public void sendCards(List<TexasHoldemPlayer> players){
        List<Card> deck = initDeck();
        // 洗牌
        shuffleDeck(deck);
        // 发牌
        players.forEach(p->{
            Card card1 = deck.remove(0);
            Card card2 = deck.remove(0);
            List<Card> playerHand = new ArrayList<>();
            playerHand.add(card1);
            playerHand.add(card2);
            p.setHand(playerHand);
            log.info("发牌给玩家:{} 手牌:{}", p.getNickName(), p.getHand());
        });
    }


}
