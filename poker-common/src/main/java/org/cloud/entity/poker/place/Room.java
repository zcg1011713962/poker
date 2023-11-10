package org.cloud.entity.poker.place;

import lombok.Data;
import lombok.ToString;
import org.cloud.entity.poker.players.Player;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 基础房间
 */
@ToString
@Data
public class Room {
    // 房间玩家
    private static ConcurrentMap<String, List<Player>> roomPlayers = new ConcurrentHashMap<>();

    /**
     * 进房
     * @param roomId
     * @param player
     */
    public static void enter(String roomId, Player player){
        List<Player> players = roomPlayers.get(roomId);
        players.add(player);
        roomPlayers.put(roomId, players);
    }


}
