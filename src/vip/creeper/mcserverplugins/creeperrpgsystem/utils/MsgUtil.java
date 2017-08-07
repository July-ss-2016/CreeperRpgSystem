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
    private static final JavaPlugin plugin = CreeperRpgSystem.getInstance();
    private static final Logger logger = plugin.getLogger();
    private static final com.sn1cko.actionbar.actionbar actionBar = (com.sn1cko.actionbar.actionbar) Bukkit.getPluginManager().getPlugin("Actionbar");

    // 输出信息
    public static void info(String msg) {
        logger.info(msg);
    }

    // 向cs发送信息
    public static void sendMsg(final CommandSender cs, final String msg) {
        cs.sendMessage(HEAD_MSG + translateColorMsg(msg));
    }

    // 发送替换过变量后的信息
    public static void sendReplacedVarMsg(final CommandSender cs, final String msg) {
        cs.sendMessage(translateColorMsg(getReplacedVariableMsg(msg, cs)));
    }

    // 翻译彩色代码
    private static String translateColorMsg(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    // 输出警告信息
    public static void warring() {
        logger.warning("-");
    }

    // 发送raw json msg
    public static void sendRawMsg(CommandSender cs, String json) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + cs.getName() + " " + json);
    }

    // 发送actionbar
    public static boolean sendActionBar(Player player, String msg) {
        com.sn1cko.actionbar.methods.theAction.sendAction(player, msg, actionBar,true);
        return true;
    }

    // 发送title
    public static void sendTitle(Player player, String text) {
        CreeperTitleAPI.getTitleManager().sendTitle(player, TitleManager.TitleType.TITLE, ChatColor.translateAlternateColorCodes('&', text), 0, 60, 20);
    }

    // 发送小title
    public static void sendSubTitle(Player player, String text) {
        CreeperTitleAPI.getTitleManager().sendTitle(player, TitleManager.TitleType.TITLE, "" ,0, 0, 0);
        CreeperTitleAPI.getTitleManager().sendTitle(player, TitleManager.TitleType.SUBTITLE, ChatColor.translateAlternateColorCodes('&', text), 0, 60, 20);
    }

    // 发送广播消息
    public static void sendBroadcastMsg(String msg) {
        Bukkit.getServer().broadcastMessage(HEAD_MSG + ChatColor.translateAlternateColorCodes('&', msg));
    }

    // 得到替换过变量后的msg
    public static String getReplacedVariableMsg(String msg, CommandSender cs) {
        return msg.replace("%head_msg%", HEAD_MSG).replace("%player_name", cs.getName());
    }
}
