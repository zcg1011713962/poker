package org.cloud.entity.poker.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 操作一副扑克牌
 */
public interface Poker {

    /**
     * 初始化一副牌
     * @return
     */
    default List<Card> initDeck(){
        List<Card> deck = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }

    /**
     * 洗一副牌
     * @param deck
     */
    default void shuffleDeck(List<Card> deck){
        Collections.shuffle(deck);
    }



}
