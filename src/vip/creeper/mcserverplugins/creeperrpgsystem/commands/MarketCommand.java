package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgCommand;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.MarketManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

/**
 * Created by July_ on 2017/7/9.
 */
public class MarketCommand implements RpgCommand {


    public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
        String marketCode = args[2];

        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("tp")) {
                if (!Util.isPlayer(cs)) {
                    MsgUtil.sendMsg(cs, "&c该指令必须由玩家执行!");
                    return true;
                }

                Player player = (Player) cs;

                if (!MarketManager.isExistsMarket(marketCode)) {
                    MsgUtil.sendMsg(player, "&c不存在该集市.");
                    return true;
                }

                MarketManager.getMarketByMarketCode(marketCode).tp(player);
                return true;
            }
        }
        return false;
    }
}
