package vip.creeper.mcserverplugins.creeperrpgsystem;

import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.StageEnterEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.RpgPlayerManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.ConfigUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.*;

/**
 * Created by July_ on 2017/7/7.
 */
public class Stage {
    private String stageCode;
    private org.bukkit.Location spawnLoc;
    private boolean freeStage;
    private HashMap<String, Integer> challenges;
    private List<String> confirmMessages;
    private List<String> finishedDeblockingStages;
    private List<String> finishedRewardCommands;
    private HashMap<Optional<MythicItem>, Integer> finishedRewardItems;
    private boolean finishedConfirmSpawn;

    public Stage(String stageCode, Location spawnLoc, boolean freeStage, HashMap<String, Integer> challenges, List<String> confirmMessages, List<String> finishedDeblockingStages, List<String> finishedRewardCommands,
                 HashMap<Optional<MythicItem>, Integer> finishedRewardItems, boolean finishedConfirmSpawn) {
        this.stageCode = stageCode;
        this.spawnLoc = spawnLoc;
        this.freeStage = freeStage;
        this.challenges = challenges;
        this.confirmMessages = confirmMessages;
        this.finishedDeblockingStages = finishedDeblockingStages;
        this.finishedRewardCommands = finishedRewardCommands;
        this.finishedRewardItems = finishedRewardItems;
        this.finishedConfirmSpawn = finishedConfirmSpawn;
    }

    // 是否完成任务时要确认过才能回主城
    public boolean isFinishedConfirmSpawn() {
        return this.finishedConfirmSpawn;
    }

    // 是否为免费关卡
    public boolean isFreeStage() {
        return this.freeStage;
    }

    // 得到出生点
    public Location getSpawnLoc() {
        return this.spawnLoc;
    }

    // 得到关卡代码
    public String getStageCode() {
        return this.stageCode;
    }

    // 得到完成任务奖励的物品
    public HashMap<Optional<MythicItem>, Integer> getFinishedRewardItems() {
        return this.finishedRewardItems;
    }

    // 得到任务
    public HashMap<String, Integer> getChallenges() {
        return this.challenges;
    }

    // 得到确认信息
    public List<String> getConfirmMessages() {
        return this.confirmMessages;
    }

    // 得到任务完成执行的指令
    public List<String> getFinishedRewardCommands() {
        return this.finishedRewardCommands;
    }

    // 得到任务完成解锁的关卡
    public List<String> getFinishedDeblockingStages() {
        return this.finishedDeblockingStages;
    }

    // 得到玩家关卡解锁状态
    public boolean getPlayerStageState(String playerName) {
        return RpgPlayerManager.getRpgPlayer(playerName).getStageState(this.stageCode);
    }

    // 关卡传送
    public void tp(Player player) {
        StageEnterEvent event = new StageEnterEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        // 传送到本关卡出生点
        player.teleport(this.spawnLoc);
    }

    // 发送去人信息
    public void sendConfirmTpMsg(Player player) {
        for (String confirmMessage : confirmMessages) {
            MsgUtil.sendReplacedVarMsg(player, confirmMessage);
        }

        MsgUtil.sendRawMsg(player, "[\"\",{\"text\":\"\"},{\"text\":\"                                                 『点我进入关卡』 \",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/crs stage tp " + stageCode + "\"}}]");
        MsgUtil.sendReplacedVarMsg(player, "");
    }

    // 设置关卡出生点
    public boolean setSpawnLoc(Location loc) {
        File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "stages_0_8.yml");
        return ConfigUtil.setLocConfig(file, "stages." + this.stageCode + ".spawn_loc", loc);
    }

    // 判断是否为任务怪物
    public boolean isChallengeMob(String mobCode) {
        return challenges.containsKey(mobCode);
    }

    // 执行完成任务的命令
    public void performFinishedRewardCommands(final Player player) {
        for (String cmd : this.finishedRewardCommands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MsgUtil.getReplacedVariableMsg(cmd, player));
        }
    }

    // 给予完成任务奖励的物品
    public boolean giveFinishedRewardItems(Player player) {
        if (isNoFinishedRewardItem()) {
            return true;
        }

        PlayerInventory playerInventory = player.getInventory();
        Iterator iter = finishedRewardItems.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Optional<MythicItem>, Integer> entry = (Map.Entry<Optional<MythicItem>, Integer>) iter.next();
            MythicItem mythicItem = entry.getKey().get();
            int amount = entry.getValue();

            for (int i = 0; i < amount; i++) {
                if (playerInventory.firstEmpty() == -1) {
                    return false;
                }

                playerInventory.addItem(new ItemStack[] {BukkitAdapter.adapt(mythicItem.generateItemStack(1, BukkitAdapter.adapt(player), BukkitAdapter.adapt(player)))});
            }
        }
        return true;
    }

    public boolean isNoFinishedRewardItem() {
        return this.finishedRewardItems == null;
    }
}
