package vip.creeper.mcserverplugins.creeperrpgsystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;

/**
 * Created by July_ on 2017/7/12.
 */
public class StageLeaveEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    private Player player;
    private Stage stage;

    public StageLeaveEvent(final Player player, final Stage stage) {
        this.player = player;
        this.stage = stage;
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

    public Stage getStage() {
        return this.stage;
    }
}
