package vip.creeper.mcserverplugins.creeperrpgsystem;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

/**
 * Created by July_ on 2017/7/4.
 */
public class PlaceHolderApiExpansion extends EZPlaceholderHook {
    private static Main plugin = Main.getInstance();

    public PlaceHolderApiExpansion(final Main plugin) {
        super(plugin, "creeperrpgsystem");
    }

    public String onPlaceholderRequest(Player player, String str) {
        if (str.startsWith("is_locked_stange")) {
            String stangeName = str.replace("is_locked_stage", "");

        }
        return null;

    }
}
