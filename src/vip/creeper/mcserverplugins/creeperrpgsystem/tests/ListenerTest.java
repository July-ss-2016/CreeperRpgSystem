package vip.creeper.mcserverplugins.creeperrpgsystem.tests;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

/**
 * Created by July_ on 2017/7/14.
 */
public class ListenerTest implements Listener {


    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Util.spawnFirework(p.getLocation());
    }
}
