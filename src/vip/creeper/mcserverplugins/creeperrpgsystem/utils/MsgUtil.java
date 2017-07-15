package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creepertitleapi.CreeperTitleAPI;
import vip.creeper.mcserverplugins.creepertitleapi.TitleManager;

import java.util.logging.Logger;

/**
 * Created by July_ on 2017/7/5.
 */
public class MsgUtil {
    public static final String HEAD_MSG = "§a[CreeperRpgSystem] §b";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final JavaPlugin plugin = CreeperRpgSystem.getInstance();
    private static final Logger logger = plugin.getLogger();
    private static final com.sn1cko.actionbar.actionbar actionBar = (com.sn1cko.actionbar.actionbar) Bukkit.getPluginManager().getPlugin("Actionbar");


    public static void info(String msg) {
        logger.info(msg);
    }

    public static void sendMsg(final CommandSender cs, final String msg) {
        cs.sendMessage(HEAD_MSG + translateColorMsg(msg));
    }

    public static void sendReplacedVarMsg(final CommandSender cs, final String msg) {
        cs.sendMessage(translateColorMsg(msg).replace("%head_msg%", HEAD_MSG));
    }

    public static String translateColorMsg(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void warring(final String msg) {
        logger.warning(msg);
    }

    public static void sendRawMsg(CommandSender cs, String json) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + cs.getName() + " " + json);
    }

    public static boolean sendActionBar(Player player, String msg) {
        com.sn1cko.actionbar.methods.theAction.sendAction(player, msg, actionBar,true);
        return true;
    }

    public static boolean sendTitle(Player player, String text) {
        CreeperTitleAPI.getTitleManager().sendTitle(player, TitleManager.TitleType.TITLE, ChatColor.translateAlternateColorCodes('&', text), 0, 60, 20);
        return true;
    }

    public static boolean sendSubTitle(Player player, String text) {
        CreeperTitleAPI.getTitleManager().sendTitle(player, TitleManager.TitleType.TITLE, "" ,0, 0, 0);
        CreeperTitleAPI.getTitleManager().sendTitle(player, TitleManager.TitleType.SUBTITLE, ChatColor.translateAlternateColorCodes('&', text), 0, 60, 20);
        return  true;
    }

    public static void sendBroaddcastMsg(String msg) {
        Bukkit.getServer().broadcastMessage(HEAD_MSG + ChatColor.translateAlternateColorCodes('&', msg));
    }
}
