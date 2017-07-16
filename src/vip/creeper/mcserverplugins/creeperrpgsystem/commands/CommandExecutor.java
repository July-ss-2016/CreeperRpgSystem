package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperrpgsystem.impls.CommandImpl;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.util.HashMap;

/**
 * Created by July_ on 2017/7/16.
 */
public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    private HashMap<String, CommandImpl> commands = new HashMap<String, CommandImpl>();


    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        if (args.length >= 1) {
            String firstArg = args[0];

            if (commands.containsKey(firstArg)) {
                commands.get(firstArg).execute(cs, cmd, lable, args);
                return true;
            }

            MsgUtil.sendMsg(cs, "§c指令错误!");
            return true;
        }
        return  false;
    }

    public void registerCommand(String firstArg, CommandImpl cmd) {
        commands.put(firstArg, cmd);
    }
}
