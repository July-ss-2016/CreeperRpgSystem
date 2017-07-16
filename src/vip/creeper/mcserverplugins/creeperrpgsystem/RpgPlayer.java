package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by July_ on 2017/7/14.
 */
public class RpgPlayer {
    private Player bukkitPlayer;
    private File playerDataFile;
    private YamlConfiguration playerDataYml;


    public RpgPlayer(Player bukkitPlayer, File playerDataFile) {
        this.bukkitPlayer = bukkitPlayer;
        this.playerDataFile = playerDataFile;

        if (!playerDataFile.exists()) try {
            playerDataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.playerDataYml = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    //得到Bukkit玩家
    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    //得到玩家关卡解锁状态
    public boolean getStageState(String stageCode) {
        return this.getPassedStages().contains(stageCode);
    }

    //得到通过的关卡
    public List<String> getPassedStages() {
        return playerDataYml.getStringList("stage.passed_stages");
    }

    //设置关卡通过状态
    public boolean setStageState(String stageCode, boolean state) {
        if (!StageManager.isExistsStage(stageCode)) {
            return false;
        }

        List<String> temp = playerDataYml.getStringList("stage.passed_stages");

        if (temp == null) {
            temp = new ArrayList<>();
        }

        if (state) {
            //禁止重复
            if (temp.contains(stageCode)) {
                return false;
            }
            temp.add(stageCode);
        } else {
            temp.remove(stageCode);
        }

        playerDataYml.set("stage.passed_stages", temp);
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
