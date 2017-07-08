package vip.creeper.mcserverplugins.creeperrpgsystem;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;

/**
 * Created by July_ on 2017/7/4.
 */
public class PlaceHolderApiExpansion extends EZPlaceholderHook {
    private static Main plugin = Main.getInstance();

    public PlaceHolderApiExpansion(final Main plugin) {
        super(plugin, "creeperrpgsystem");
    }

    public String onPlaceholderRequest(Player player, String str) {
        String playerName = player.getName();
        if (str.startsWith("is_locked_stange")) {
            String stageName = str.replace("is_locked_stage_", "");
            return StageManager.isStageLocked(playerName, stageName) ? "已解锁" : "未解锁";
        }
        return null;
    }
}
