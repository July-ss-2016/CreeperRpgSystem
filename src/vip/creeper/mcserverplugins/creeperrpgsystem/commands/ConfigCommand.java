package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.ConfigType;
import vip.creeper.mcserverplugins.creeperrpgsystem.Market;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.configs.PluginConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgCommand;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.ConfigManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.MarketManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

/**
 * Created by July_ on 2017/7/11.
 */
public class ConfigCommand implements RpgCommand {

    public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
        if (!cs.hasPermission("CreeperRpgSystem.admin")) {
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
                    case "spawn":
                        boolean serverSpawnLocSetResult = ((PluginConfig)ConfigManager.getConfig(ConfigType.CONFIG_PLUGIN)).setServerSpawnLoc(player.getLocation());

                        if (serverSpawnLocSetResult) {
                            ConfigManager.loadConfig(ConfigType.CONFIG_PLUGIN);
                        }

                        MsgUtil.sendMsg(player, serverSpawnLocSetResult ? "&b服务器出生点设置成功!" : "&c服务器出生点设置失败!");
                        return true;
                    case "market":
                        if (!MarketManager.isExistsMarket(args[3])) {
                            MsgUtil.sendMsg(cs, "集市不存在!");
                            return true;
                        }

                        Market market = MarketManager.getMarketByMarketCode(args[3]);
                        boolean marketSpawnLocSetResult = market.setSpawnLocation(player.getLocation());

                        if (marketSpawnLocSetResult) {
                            ConfigManager.loadConfig(ConfigType.CONFIG_MARKET);
                        }

                        MsgUtil.sendMsg(player, marketSpawnLocSetResult ? "&b集市出生点设置成功!" : "&c集市出生点设置失败!");
                        return true;
                    case "stage":
                        if (!StageManager.isExistsStage(args[3])) {
                            MsgUtil.sendMsg(cs, "关卡不存在!");
                            return true;
                        }

                        Stage stage = StageManager.getStage(args[3]);
                        boolean stageSpawnLocSetResult = stage.setSpawnLoc(player.getLocation());

                        if (stageSpawnLocSetResult) {
                            ConfigManager.loadConfig(ConfigType.CONFIG_STAGE);
                        }

                        MsgUtil.sendMsg(player, stageSpawnLocSetResult ? "&b关卡出生点设置成功!" : "&c关卡出生点设置失败!");
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
