package vip.creeper.mcserverplugins.creeperrpgsystem.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgCommand;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.ConfigManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

/**
 * Created by July_ on 2017/7/11.
 */
public class ReloadCommand implements RpgCommand {


    public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
        if (!cs.hasPermission("crs.admin")) {
            MsgUtil.sendMsg(cs, "&c没有权限: crs.admin.");
            return true;
        }

        ConfigManager.loadAllConfig();
        MsgUtil.sendMsg(cs, "配置已重载!");
        return true;
    }
}
