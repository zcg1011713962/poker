package org.cloud.entity.poker.cards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 基础牌
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable {
    protected String rank;
    protected String suit;

}
