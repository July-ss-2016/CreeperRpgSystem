package vip.creeper.mcserverplugins.creeperrpgsystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;

/**
 * Created by July_ on 2017/7/12.
 */
public class StageEnterEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Stage stage;

    public StageEnterEvent(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Stage getStage() {
        return this.stage;
    }

    public HandlerList getHandlers() {
        return this.handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
