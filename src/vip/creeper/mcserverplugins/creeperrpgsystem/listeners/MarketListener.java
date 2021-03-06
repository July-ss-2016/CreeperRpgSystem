package vip.creeper.mcserverplugins.creeperrpgsystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Market;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.MarketEnterEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

/**
 * Created by July_ on 2017/7/5.
 */
public class MarketListener implements Listener {
    private CreeperRpgSystem plugin;

    public MarketListener(final CreeperRpgSystem plugin) {
        this.plugin = plugin;
    }

    //事件_进入集市
    @EventHandler
    public void onMarketEnterEvent(final MarketEnterEvent event) {
        Player player = event.getPlayer();
        Market market = event.getMarket();
        Location loc = player.getLocation();

        MsgUtil.sendReplacedVarMsg(player, market.getWelcomeMsg());

        if (market.getEnterGiveHorse()) {
            Horse horse = (Horse) (loc.getWorld().spawnEntity(loc, EntityType.HORSE));

            horse.setAge(10);
            horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true));
            horse.setVariant(Horse.Variant.MULE); //骡子
            horse.setTamed(true); //设置为驯服状态
            horse.setColor(Horse.Color.BLACK);
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Bukkit.getScheduler().runTask(plugin, () -> horse.setPassenger(player)), 20L);
        }
    }

    //事件_下马
    @EventHandler
    public void onPlayerLeaveHorseEvent(final VehicleExitEvent event) {
        Entity entity = event.getExited();

        if (entity instanceof  Player) {
            Player player = (Player)entity;
            String world = player.getWorld().getName();
            Entity veh = player.getVehicle();

            if (CreeperRpgSystem.getInstance().getMarketManager().isMarketWorld(world) && veh != null && veh.getType() == EntityType.HORSE) {
                veh.remove(); //
            }
        }
    }

    //事件_点击马背包
    @EventHandler
    public void onInvClickEvent(final InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if (inv == null) {
            return;
        }

        if (CreeperRpgSystem.getInstance().getMarketManager().isMarketWorld(player.getWorld().getName()) && "Mule".equals(inv.getTitle())) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            MsgUtil.sendMsg(player, "&c谁允许你拿的!");
        }
    }

    //事件_禁止攻击生物
    @EventHandler
    public void onHorseDamageEvent(final EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.HORSE) {
            event.setCancelled(true);
        }
    }
}
