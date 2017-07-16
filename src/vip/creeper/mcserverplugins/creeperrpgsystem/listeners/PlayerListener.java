package vip.creeper.mcserverplugins.creeperrpgsystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.RpgPlayerManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

/**
 * Created by July_ on 2017/7/15.
 */
public class PlayerListener implements Listener {
    private static JavaPlugin plugin = CreeperRpgSystem.getInstance();


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTask(plugin, () -> {
            Util.teleportToSpawnPoint(player);
            RpgPlayerManager.registerRpgPlayer(player);
        });

    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        RpgPlayerManager.unregisterPlayer(player);
    }

    //事件_玩家死在副本世界
    @EventHandler
    public void onPlayerDeathEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (StageManager.isStageWorld(player.getWorld().getName())) {

            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Util.teleportToSpawnPoint(player);
                }
            });

            MsgUtil.sendMsg(player, "&e你死了,之前关卡的任务进度任然被保存着.");
        }
    }

    //事件_指令输入
    @EventHandler
    public void onPlayerCommandProcessEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();

        if (!StageManager.isStageWorld(player.getWorld().getName()) || Util.isAdmin(player)) {
            return;
        }

        for (String cmd : Settings.stageWhitelistCommands) {
            if (msg.startsWith(cmd)) {
                return;
            }
        }

        event.setCancelled(true);
        MsgUtil.sendMsg(player, "&c在副本世界中,你不能输入该指令!");
    }

}
