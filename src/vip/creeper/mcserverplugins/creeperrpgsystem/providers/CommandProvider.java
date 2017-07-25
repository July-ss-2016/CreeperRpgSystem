package vip.creeper.mcserverplugins.creeperrpgsystem.providers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by July_ on 2017/7/9.
 */
public interface CommandProvider {
    boolean execute(CommandSender cs, Command cmd, String lable, String[] args);
}
