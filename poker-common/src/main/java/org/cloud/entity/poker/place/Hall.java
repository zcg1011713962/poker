package org.cloud.entity.poker.place;

import lombok.Data;
import lombok.ToString;
import org.cloud.entity.poker.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 基础大厅
 */
@ToString
@Data
public class Hall {
    // 大厅玩家
    private ConcurrentMap<String, Player> hallPlayers = new ConcurrentHashMap<>();

    /**
     * 玩家进入大厅
     */
    public void enter(Player player){
        hallPlayers.put(player.getId(), player);
    }

    /**
     * 玩家离开大厅
     * @param player
     */
    public void leave(Player player){
        hallPlayers.remove(player.getId());
    }

    /**
     * 离开大厅进房
     */
    public void enterRoom(String roomId, Player player){
        leave(player);
        Room.enter(roomId, player);
    }


}
