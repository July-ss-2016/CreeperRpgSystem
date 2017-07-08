package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.Main;

import java.util.logging.Logger;

/**
 * Created by July_ on 2017/7/5.
 */
public class MsgUtil {
    public static final String HEAD_MSG = "§a[CreeperRpgSystem] §b";
    private static Main plugin = Main.getInstance();
    private static Logger logger = plugin.getLogger();
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void sendMsg(final Player player, final String msg) {
        player.sendMessage(HEAD_MSG + translateColorMsg(msg));
    }

    public static void sendReplacedVarMsg(final Player player, final String msg) {
        player.sendMessage(msg.replace("%head_msg%", HEAD_MSG));
    }

    public static String translateColorMsg(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
