package vip.creeper.mcserverplugins.creeperrpgsystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import vip.creeper.mcserverplugins.creeperrpgsystem.Market;

/**
 * Created by July_ on 2017/7/10.
 */
public class MarketEnterEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private Player player;
    private  Market market;

    public MarketEnterEvent(Player player, Market market) {
        this.player = player;
        this.market = market;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Market getMarket() {
        return this.market;
    }
}
