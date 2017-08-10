package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgPlayer;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;

import java.io.File;
import java.util.HashMap;

/**
 * Created by July_ on 2017/7/15.
 */
public class RpgPlayerManager {
    private static HashMap<String, RpgPlayer> rpgPlayers = new HashMap<>();

    //得到rpg玩家
    public static RpgPlayer getRpgPlayer(String playerName) {
        if (!rpgPlayers.containsKey(playerName)) {
            File file = FileUtil.getPlayerDataFile(playerName);
            rpgPlayers.put(playerName, new RpgPlayer(playerName, file));
        }
        return rpgPlayers.get(playerName);
    }

    //注销某个rpg玩家
    public static void unregisterPlayer(Player player) {
        rpgPlayers.remove(player.getName());
    }

    //注销所有rpg玩家
    public static void unregisterAll() {
        rpgPlayers.clear();
    }
}

