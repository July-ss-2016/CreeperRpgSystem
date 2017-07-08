package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

/**
 * Created by July_ on 2017/7/7.
 */
public class PlayerCommand implements CommandExecutor {
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        if (cs.hasPermission("CreeperRpgSystem.user") && (cs instanceof Player)) {
            Player player = (Player)cs;
            String playerName = player.getName();
            //crs tp player SA1
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("try_tp")) {
                    if (StageManager.isStageLocked(playerName, args[2])) {
                        MsgUtil.sendMsg(player, "&c您还没有解锁该关卡,请先完成已解锁的关卡!");
                        return true;
                    }
                    StageManager.sendEnterStagePlaceJsonMsg(player, args[2]);
                    return  true;
                }
            }
        }
        return false;
    }
}
