package vip.creeper.mcserverplugins.creeperrpgsystem;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by July_ on 2017/7/4.
 */
public class PlaceHolderApiExpansion extends EZPlaceholderHook {

    public PlaceHolderApiExpansion(final Plugin plugin) {
        super(plugin, "crs");
    }

    //请求事件
    public String onPlaceholderRequest(final Player player, final String str) {
        String playerName = player.getName();

        //前缀过滤，排除其他插件
        if (str.startsWith("stage_state_")) {
            String stageCode = str.replace("stage_state_", "");
            Stage stage = CreeperRpgSystem.getInstance().getStageManager().getStage(stageCode);

            if (stage.isFreeStage()) {
                return "已解锁(免费)";
            }

            RpgPlayer rpgPlayer = CreeperRpgSystem.getInstance().getRpgPlayerManager().getRpgPlayer(playerName);
            return rpgPlayer.getStageState(stageCode) ? "已解锁" : "未解锁";
        }
        return null;
    }
}
