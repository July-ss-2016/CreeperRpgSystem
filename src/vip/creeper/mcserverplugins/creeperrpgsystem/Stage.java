package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.StageEnterEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.ConfigUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by July_ on 2017/7/7.
 */
public class Stage {
    private org.bukkit.Location spawnLoc;
    private String stageCode;
    private HashMap<String, Integer> challenges;
    private List<String> confirmMessages;
    private List<String> finishingCommands;
    private List<String> finishedDeblockingStages;
    private boolean freeStage;

    public Stage(String stageCode, Location spawnLoc, HashMap<String, Integer> challenges, List<String> confirmMessages, List<String> finishCommands, boolean freeStage, List<String> finishedDeblockingStages) {
        this.spawnLoc = spawnLoc;
        this.stageCode = stageCode;
        this.challenges = challenges;
        this.confirmMessages = confirmMessages;
        this.finishingCommands = finishCommands;
        this.freeStage = freeStage;
        this.finishedDeblockingStages = finishedDeblockingStages;
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

    public List<String> getFinishingCommands() {
        return this.finishingCommands;
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
}
