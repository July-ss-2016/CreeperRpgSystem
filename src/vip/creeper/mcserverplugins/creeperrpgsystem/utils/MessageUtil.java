package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.Main;

import java.util.logging.Logger;

/**
 * Created by July_ on 2017/7/5.
 */
public class MessageUtil {
    private static Main plugin = Main.getInstance();
    private static Logger logger = plugin.getLogger();
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void info(String msg) {
        logger.info(msg);
    }
    public static void sendMsg(Player player, String msg) {
        if (player != null) {
            player.sendMessage(msg);
        }
    }
}
