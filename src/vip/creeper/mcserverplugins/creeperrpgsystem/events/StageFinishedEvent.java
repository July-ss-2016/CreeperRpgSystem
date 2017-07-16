package vip.creeper.mcserverplugins.creeperrpgsystem.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgPlayer;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;

/**
 * Created by July_ on 2017/7/16.
 */
public class StageFinishedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private RpgPlayer rpgPlayer;
    private Stage stage;


    public StageFinishedEvent(RpgPlayer rpgPlayer, Stage stage) {
        this.rpgPlayer = rpgPlayer;
        this.stage = stage;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public RpgPlayer getRpgPlayer() {
        return this.rpgPlayer;
    }

    public  Stage getStage() {
        return this.stage;
    }

}
