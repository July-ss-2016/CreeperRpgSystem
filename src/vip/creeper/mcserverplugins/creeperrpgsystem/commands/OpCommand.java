package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.ConfigManager;

/**
 * Created by July_ on 2017/7/6.
 */
public class OpCommand {
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        if (cs.hasPermission("CreeperRpgSystem.admin")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    ConfigManager.loadAllConfig();
                    cs.sendMessage("OK");
                    return true;
                }
            }
        }
        return false;
    }
}
