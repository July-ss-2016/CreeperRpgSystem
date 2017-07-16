package vip.creeper.mcserverplugins.creeperrpgsystem;

import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.StageEnterEvent;
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

    public Stage(String stageCode, Location spawnLoc, boolean freeStage, HashMap<String, Integer> challenges, List<String> confirmMessages, List<String> finishedDeblockingStages, List<String> finishedRewardCommands,
                 HashMap<Optional<MythicItem>, Integer> finishedRewardItems) {
        this.stageCode = stageCode;
        this.spawnLoc = spawnLoc;
        this.freeStage = freeStage;
        this.challenges = challenges;
        this.confirmMessages = confirmMessages;
        this.finishedDeblockingStages = finishedDeblockingStages;
        this.finishedRewardCommands = finishedRewardCommands;
        this.finishedRewardItems = finishedRewardItems;
    }

    public boolean isFreeStage() {
        return this.freeStage;
    }

    public Location getSpawnLoc() {
        return this.spawnLoc;
    }

    public String getStageCode() {
        return this.stageCode;
    }

    public HashMap<String, Integer> getChallenges() {
        return this.challenges;
    }

    public List<String> getConfirmMessages() {
        return this.confirmMessages;
    }

    public List<String> finishedRewardCommands() {
        return this.finishedRewardCommands;
    }

    public List<String> getFinishedDeblockingStages() {
        return this.finishedDeblockingStages;
    }

    public void tp(Player player) {
        StageEnterEvent event = new StageEnterEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);
        player.teleport(this.spawnLoc);
    }

    public void sendConfirmTpMsg(Player player) {
        for (String confirmMessage : confirmMessages) {
            MsgUtil.sendReplacedVarMsg(player, confirmMessage);
        }

        MsgUtil.sendRawMsg(player, "[\"\",{\"text\":\"\"},{\"text\":\"                                      『点我进入关卡』 \",\"color\":\"light_purple\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/crs stage tp " + stageCode + "\"}}]");
        MsgUtil.sendReplacedVarMsg(player, "");
    }

    public boolean setSpawnLoc(Location loc) {
        File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "StageConfig.yml");
        return ConfigUtil.setLocConfig(file, "stages." + this.stageCode + ".spawn_loc", loc);
    }

    public boolean getPlayerStageState(String playerName) {
        File file = FileUtil.getPlayerDataFile(playerName);

        if (!file.exists()) {
            return false;
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        return yml.getStringList("stage.passed_stages").contains(stageCode);
    }

    public boolean isChallengeMob(String mobCode) {
        return challenges.containsKey(mobCode);
    }

    public void performFinishedRewardCommands(Player player) {
        for (String cmd : this.finishedRewardCommands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MsgUtil.replacePlayerVariable(cmd, player));
        }
    }

    public boolean giveFinishedRewardItems(Player player) {
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

}
