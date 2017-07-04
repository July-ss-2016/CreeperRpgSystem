package vip.creeper.mcserverplugins.creeperrpgsystem;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

/**
 * Created by July_ on 2017/7/4.
 */
public class PlaceHolderApiExpansion extends EZPlaceholderHook {
    private static Main plugin = Main.getInstance();

    public PlaceHolderApiExpansion(final Main plugin) {
        super(plugin, "creepermarriage");
    }

    public String onPlaceholderRequest(Player player, String str) {
        switch (str) {
            case "is_locked_W":
        }
        return null;

    }
}
