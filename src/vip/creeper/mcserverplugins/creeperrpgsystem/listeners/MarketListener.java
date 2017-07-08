package vip.creeper.mcserverplugins.creeperrpgsystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import vip.creeper.mcserverplugins.creeperrpgsystem.Main;
import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;
import vip.creeper.mcserverplugins.creeperrpgsystem.Supermarket;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

import java.util.HashMap;

/**
 * Created by July_ on 2017/7/5.
 */
public class MarketListener implements Listener {
    private  static Main plugin = Main.getInstance();
    private HashMap<Integer, Integer> horseCleanTaskMap = new HashMap<Integer, Integer>();


    @EventHandler
    public void onPlayerEnterSupermarket(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        String nowWorld = loc.getWorld().getName();
        if (Util.isSupermarketWorld(nowWorld)) {
            Supermarket supermarket = Settings.marketMap.get(nowWorld);
            MsgUtil.sendReplacedVarMsg(player, supermarket.getWelcomeMsg());
            if (supermarket.getEnterGiveHorse()) {
                Horse horse = (Horse) Bukkit.getWorld(nowWorld).spawnEntity(loc, EntityType.HORSE);
                horse.setAge(10);
                horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true));
                horse.setVariant(Horse.Variant.MULE);//骡子
                horse.setTamed(true);//设置为驯服状态
                horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                Bukkit.getScheduler().runTaskLater(plugin, () -> horse.setPassenger(player), 10L);
            }
        }
    }

    @EventHandler
    public void onPlayerLeaveHorseEvent(VehicleExitEvent event) {
        Entity entity = event.getExited();
        if (entity instanceof  Player) {
            Player player = (Player)entity;
            String world = player.getWorld().getName();
            Entity veh = player.getVehicle();

            if (Util.isSupermarketWorld(world) && veh != null && veh.getType() == EntityType.HORSE) {
                BukkitTask bt = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Bukkit.getScheduler().runTask(plugin, () -> {
                    veh.remove();
                }), 200);//10s后清除马
                // 这些id都是唯一的
                horseCleanTaskMap.put(veh.getEntityId(), bt.getTaskId());
            }
        }
    }

    @EventHandler
    public void onPlayerRideHorseEvent(VehicleEnterEvent event) {
        Entity rider = event.getEntered();
        Entity veh = event.getVehicle();
        int vehId = veh.getEntityId();
        if (Util.isSupermarketWorld(rider.getWorld().getName()) && rider.getType() == EntityType.PLAYER && veh.getType() == EntityType.HORSE && horseCleanTaskMap.containsKey(vehId)) {
            Bukkit.getScheduler().cancelTask(horseCleanTaskMap.get(vehId));
            horseCleanTaskMap.remove(vehId);
        }
    }

    @EventHandler
    public void onInvOpenEvent(InventoryOpenEvent event) {
        Player player = (Player)event.getPlayer();
        if (event.getInventory().getSize() == 2 && Util.isSupermarketWorld(player.getWorld().getName())) {
            event.setCancelled(true);
            player.closeInventory();
        }
    }
}
