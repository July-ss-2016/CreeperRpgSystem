package vip.creeper.mcserverplugins.creeperrpgsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgCommand;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

/**
 * Created by July_ on 2017/7/9.
 */
public class StageCommand implements RpgCommand {

    public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
        if (args.length == 3) {
            String stageCode = args[2];

            if (!(cs instanceof Player)) {
                MsgUtil.sendMsg(cs, "&c该指令必须由玩家执行!");
                return true;
            }

            Stage stage = StageManager.getStage(stageCode);

            if (stage == null) {
                MsgUtil.sendMsg(cs, "&c关卡代码不存在.");
                return true;
            }

            if (!(cs instanceof Player)) {
                MsgUtil.sendMsg(cs, "&c该指令必须由玩家执行!");
                return true;
            }

            Player player = (Player) cs;
            String playerName = player.getName();

            switch (args[1]) {
                case "send_confirm_msg":
                    player.closeInventory();
                    StageManager.getStage(stageCode).sendConfirmTpMsg(player);
                    return true;
                case "tp":
                    if (!stage.isFreeStage() && !stage.getPlayerStageState(playerName)) {
                        MsgUtil.sendMsg(player, "&c你还没有解锁本关卡!");
                        return true;
                    }

                    stage.tp(player);
                    return  true;
            }
        }
        return false;
    }
}
