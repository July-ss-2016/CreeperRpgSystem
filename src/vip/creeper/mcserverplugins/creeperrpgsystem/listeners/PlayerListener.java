package vip.creeper.mcserverplugins.creeperrpgsystem.listeners;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgPlayer;
import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

import java.util.Optional;

/**
 * Created by July_ on 2017/7/15.
 */
public class PlayerListener implements Listener {
    private CreeperRpgSystem plugin;
    private Settings settings;

    public PlayerListener(final CreeperRpgSystem plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
    }

    //事件_玩家进入
    @EventHandler
    public void onPlayerJoinEvent(final PlayerJoinEvent event) {
        //必须用同步线程
        Bukkit.getScheduler().runTask(plugin, () -> {
            Player player = event.getPlayer();
            RpgPlayer rpgPlayer = CreeperRpgSystem.getInstance().getRpgPlayerManager().getRpgPlayer(player.getName());
            PlayerInventory playerInventory = player.getInventory();

            Util.teleportToServerSpawnPoint(player);

            //第一次登录
            if (rpgPlayer.isFirstPlayed()) {
                int i = 0;

                while (i < settings.firstJoinItems.size()) {
                    Optional<MythicItem> optional = MythicMobs.inst().getItemManager().getItem(settings.firstJoinItems.get(i));

                    if (optional != null) {
                        @SuppressWarnings("ConstantConditions") MythicItem item = optional.get();
                        playerInventory.addItem(BukkitAdapter.adapt(item.generateItemStack(1, BukkitAdapter.adapt(player), BukkitAdapter.adapt(player))));

                    }

                    i++;
                }

                //设置非第一次登录
                rpgPlayer.setFirstPlayed(false);
                MsgUtil.sendMsg(player,"您已获得Rpg新手礼包~");
            }
        });

    }

    //事件_玩家下线
    @EventHandler
    public void onPlayerQuitEvent(final PlayerQuitEvent event) {
        Player player = event.getPlayer();

        CreeperRpgSystem.getInstance().getRpgPlayerManager().unregisterPlayer(player);
    }

    //事件_指令输入
    @EventHandler
    public void onPlayerCommandProcessEvent(final PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage().toLowerCase();

        if (!CreeperRpgSystem.getInstance().getStageManager().isStageWorld(player.getWorld().getName()) || Util.isAdmin(player)) {
            return;
        }

        for (String cmd : settings.stageWhitelistCommands) {
            if (msg.startsWith(cmd.toLowerCase())) {
                return;
            }
        }

        event.setCancelled(true);
        MsgUtil.sendMsg(player, "&c在副本世界中,你不能输入该指令!");
    }

}
