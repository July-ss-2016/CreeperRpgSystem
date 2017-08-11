package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgCommand;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.util.HashMap;

/**
 * Created by July_ on 2017/7/16.
 */
public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    private HashMap<String, RpgCommand> commands;

    public CommandExecutor() {
        this.commands = new HashMap<>();
    }

    public boolean onCommand(final CommandSender cs, final Command cmd, final String lable, final String[] args) {
        if (args.length >= 1) {
            String firstArg = args[0].toLowerCase();

            if (commands.containsKey(firstArg)) {
                commands.get(firstArg).execute(cs, cmd, lable, args);
                return true;
            }

            MsgUtil.sendMsg(cs, "§c指令错误!");
            return true;
        }
        return  false;
    }

    public void registerCommand(final String firstArg, final RpgCommand cmd) {
        commands.put(firstArg.toLowerCase(), cmd);
    }
}
