package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgCommand;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

/**
 * Created by July_ on 2017/7/11.
 */
public class InventoryCommand implements RpgCommand {

    public boolean execute(final CommandSender cs, final Command cmd, final String lable, final String[] args) {

        if (args.length == 2 && args[1].equalsIgnoreCase("closeself")) {

            if (!(cs instanceof Player)) {
                MsgUtil.sendMsg(cs, "该指令必须由玩家执行!");
                return true;
            }

            Player player = (Player) cs;

            player.closeInventory();
            return true;
        }
        return false;
    }
}
