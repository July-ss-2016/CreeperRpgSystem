package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by July_ on 2017/7/14.
 */
public class RpgPlayer {
    private String playerName;
    private File playerDataFile;
    private YamlConfiguration playerDataYml;

    public RpgPlayer(String playerName, File playerDataFile) {
        this.playerName = playerName;
        this.playerDataFile = playerDataFile;

        if (!playerDataFile.exists()) try {
            playerDataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.playerDataYml = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    //得到playerName
    public String getPlayerName() {
        return this.playerName;
    }

    //得到Player
    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerName);
    }

    //得到玩家关卡解锁状态
    public boolean getStageState(String stageCode) {
        return this.getDeblockingStages().contains(stageCode);
    }

    //得到通过的关卡
    public List<String> getDeblockingStages() {
        return playerDataYml.getStringList("stage.deblocking_stages");
    }

    //设置关卡通过状态
    public boolean setStageState(String stageCode, boolean state) {
        if (!StageManager.isExistsStage(stageCode)) {
            return false;
        }

        List<String> temp = playerDataYml.getStringList("stage.deblocking_stages");

        if (temp == null) {
            temp = new ArrayList<>();
        }

        MsgUtil.warring();

        if (state) {
            //禁止重复
            if (temp.contains(stageCode)) {
                return false;
            }
            temp.add(stageCode);
        } else {
            temp.remove(stageCode);
        }

        playerDataYml.set("stage.deblocking_stages", temp);
        return saveYml();

    }

    //是否第一次玩
    public boolean isFirstPlayed() {
        return playerDataYml.getBoolean("first_played", true);
    }

    public boolean setFirstPlayed(boolean state) {
        playerDataYml.set("first_played", state);
        return saveYml();
    }

    //保存Yml
    private boolean saveYml() {
        try {
            playerDataYml.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }
}
