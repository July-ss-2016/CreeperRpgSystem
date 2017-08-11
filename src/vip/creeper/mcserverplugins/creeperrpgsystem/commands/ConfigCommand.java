package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.*;
import vip.creeper.mcserverplugins.creeperrpgsystem.configs.PluginConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

/**
 * Created by July_ on 2017/7/11.
 */
public class ConfigCommand implements RpgCommand {

    public boolean execute(final CommandSender cs, final Command cmd, final String lable, final String[] args) {
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
                        boolean serverSpawnLocSetResult = ((PluginConfig) CreeperRpgSystem.getInstance().getConfigManager().getConfig(ConfigType.CONFIG_PLUGIN)).setServerSpawnLoc(player.getLocation());

                        if (serverSpawnLocSetResult) {
                            CreeperRpgSystem.getInstance().getConfigManager().loadConfig(ConfigType.CONFIG_PLUGIN);
                        }

                        MsgUtil.sendMsg(player, serverSpawnLocSetResult ? "&b服务器出生点设置成功!" : "&c服务器出生点设置失败!");
                        return true;
                    case "market":
                        if (!CreeperRpgSystem.getInstance().getMarketManager().isExistsMarket(args[3])) {
                            MsgUtil.sendMsg(cs, "集市不存在!");
                            return true;
                        }

                        Market market = CreeperRpgSystem.getInstance().getMarketManager().getMarketByMarketCode(args[3]);
                        boolean marketSpawnLocSetResult = market.setSpawnLocation(player.getLocation());

                        if (marketSpawnLocSetResult) {
                            CreeperRpgSystem.getInstance().getConfigManager().loadConfig(ConfigType.CONFIG_MARKET);
                        }

                        MsgUtil.sendMsg(player, marketSpawnLocSetResult ? "&b集市出生点设置成功!" : "&c集市出生点设置失败!");
                        return true;
                    case "stage":
                        if (!CreeperRpgSystem.getInstance().getStageManager().isExistsStage(args[3])) {
                            MsgUtil.sendMsg(cs, "关卡不存在!");
                            return true;
                        }

                        Stage stage = CreeperRpgSystem.getInstance().getStageManager().getStage(args[3]);
                        boolean stageSpawnLocSetResult = stage.setSpawnLoc(player.getLocation());

                        if (stageSpawnLocSetResult) {
                            CreeperRpgSystem.getInstance().getConfigManager().loadConfig(ConfigType.CONFIG_STAGE);
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
