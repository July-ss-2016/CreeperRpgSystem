package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.CommandManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

/**
 * Created by July_ on 2017/7/9.
 */
public class CommandHandler implements CommandExecutor {


    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        if (args.length >= 1) {
            String firstArg = args[0];
            if (!CommandManager.executeCommand(firstArg, cs, cmd, lable, args)) {
                MsgUtil.sendMsg(cs, "§c指令错误!");
                return true;
            }
        }
        return  true;
    }
}
