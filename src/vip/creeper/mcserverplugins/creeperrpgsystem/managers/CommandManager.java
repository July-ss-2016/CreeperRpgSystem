package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperrpgsystem.impls.CommandImpl;

import java.util.HashMap;

/**
 * Created by July_ on 2017/7/9.
 */
public class CommandManager {
    private static HashMap<String, CommandImpl> commands = new HashMap<String, CommandImpl>();


    public static void registerCommand(String firstArg, CommandImpl cmd) {
        commands.put(firstArg, cmd);
    }

    public  static boolean executeCommand(String firstArg, CommandSender cs, Command cmd, String lable, String[] args) {
        if (commands.containsKey(firstArg)) {
            commands.get(firstArg).execute(cs, cmd, lable, args);
            return true;
        }
        return false;
    }
}
