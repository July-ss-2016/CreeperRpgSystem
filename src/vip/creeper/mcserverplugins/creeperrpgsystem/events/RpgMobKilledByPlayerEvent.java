package vip.creeper.mcserverplugins.creeperrpgsystem.events;

import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by July_ on 2017/7/16.
 */
public class RpgMobKilledByPlayerEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    private Player killer;
    private MythicMob mythicMob;

    public RpgMobKilledByPlayerEvent(final Player killer, final MythicMob mythicMob) {
        this.killer = killer;
        this.mythicMob = mythicMob;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getKiller() {
        return this.killer;
    }

    public MythicMob getMythicMob() {
        return this.mythicMob;
    }
}
