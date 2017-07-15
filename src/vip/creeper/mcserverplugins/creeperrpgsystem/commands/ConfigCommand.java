package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.ConfigType;
import vip.creeper.mcserverplugins.creeperrpgsystem.Market;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.impls.CommandImpl;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.ConfigManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.MarketManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

/**
 * Created by July_ on 2017/7/11.
 */
public class ConfigCommand implements CommandImpl {


    @Override
    public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
        if (!cs.hasPermission("crs.admin")) {
            MsgUtil.sendMsg(cs, "&c没有权限: crs.admin.");
            return true;
        }

        if (args.length == 4) {
            if (args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("sloc")) {

                if (!Util.isPlayer(cs)) {
                    MsgUtil.sendMsg(cs, "§c该玩家必须由玩家来执行!");
                    return true;
                }

                Player player = (Player) cs;
                String prefix = args[3].substring(0, args[3].indexOf("_")).toLowerCase();

                switch (prefix) {
                    case "market":
                        Market market = MarketManager.getMarketByMarketCode(args[3]);
                        boolean marketResult = market.setSpawnLoc(player.getLocation());

                        if (marketResult) {
                            ConfigManager.loadConfig(ConfigType.CONFIG_MARKET);
                        }

                        MsgUtil.sendMsg(player, marketResult ? "&b集市出生点设置成功!" : "&c集市出生点设置失败!");
                        return true;
                    case "stage":
                        Stage stage = StageManager.getStage(args[3]);
                        boolean stageResult = stage.setSpawnLoc(player.getLocation());

                        if (stageResult) {
                            ConfigManager.loadConfig(ConfigType.CONFIG_STAGE);
                        }

                        MsgUtil.sendMsg(player, stageResult ? "&b设置成功!" : "&c设置失败!");
                        return true;
                    default:
                        MsgUtil.sendMsg(player, "&c参数错误!");
                        return true;
                }
            }
        }
        return false;
    }
}
