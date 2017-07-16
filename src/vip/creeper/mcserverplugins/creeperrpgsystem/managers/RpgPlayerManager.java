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


    public static void registerRpgPlayer(Player player) {
        File file = FileUtil.getPlayerDataFile(player.getName());
        rpgPlayers.put(player.getName(), new RpgPlayer(player, file));
    }

    public static RpgPlayer getRpgPlayer(String playerName) {
        return rpgPlayers.get(playerName);
    }

    public static void unregisterPlayer(Player player) {
        rpgPlayers.remove(player.getName());
    }

    public static void unreigsterAllPlayers() {
        rpgPlayers.clear();
    }
}

