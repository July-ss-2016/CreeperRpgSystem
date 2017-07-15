package vip.creeper.mcserverplugins.creeperrpgsystem;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.RpgPlayerManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;

/**
 * Created by July_ on 2017/7/4.
 */
public class PlaceHolderApiExpansion extends EZPlaceholderHook {

    public PlaceHolderApiExpansion(Plugin plugin) {
        super(plugin, "crs");
    }

    @Override
    public String onPlaceholderRequest(Player player, String str) {
        String playerName = player.getName();

        //前缀过滤，排除其他插件
        if (str.startsWith("stage_state_")) {
            String stageCode = str.replace("stage_state_", "");

            Stage stage = StageManager.getStage(stageCode);

            if (stage.isFreeStage()) {
                return "已解锁(免费)";
            }

            RpgPlayer rpgPlayer = RpgPlayerManager.getRpgPlayer(playerName);
            return rpgPlayer.getStageState(stageCode) ? "已解锁" : "未解锁";
        }
        return null;
    }
}
