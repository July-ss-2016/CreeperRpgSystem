package vip.creeper.mcserverplugins.creeperrpgsystem.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgPlayer;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;

/**
 * Created by July_ on 2017/7/16.
 */
public class StageFinishedEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    private RpgPlayer rpgPlayer;
    private Stage stage;


    public StageFinishedEvent(RpgPlayer rpgPlayer, Stage stage) {
        this.rpgPlayer = rpgPlayer;
        this.stage = stage;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public RpgPlayer getRpgPlayer() {
        return this.rpgPlayer;
    }

    public Stage getStage() {
        return this.stage;
    }

}
