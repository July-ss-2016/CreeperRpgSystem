package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by July_ on 2017/7/9.
 */
public interface RpgCommand {
    boolean execute(CommandSender cs, Command cmd, String lable, String[] args);
}
