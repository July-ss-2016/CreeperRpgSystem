package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by July_ on 2017/7/4.
 */
public class StageManager {
    private HashMap<String, Stage> stages;
    private HashMap<String, String> unfinishedStagePlayers;
    private List<String> stageWorlds;

    public StageManager() {
        this.stages = new HashMap<>();
        this.unfinishedStagePlayers = new HashMap<>();
        this.stageWorlds = new ArrayList<>();
    }

    public boolean setPlayerStagePassed(final String playerName, final String stageCode, final boolean state) {
        File file = FileUtil.getPlayerDataFile(playerName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            List<String> passedStages = yml.getStringList("stage.passed_stages");

            if (passedStages == null) {
                passedStages = new ArrayList<>();
            }

            if (state) {
                if (!passedStages.contains(stageCode)) {
                    passedStages.add(stageCode);
                }
            } else {
                passedStages.remove(stageCode);
            }

            yml.set("stage.passed_stages", passedStages);
            yml.save(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    //通过关卡代码得到关卡
    public Stage getStage(final String stageCode) {
        return stages.getOrDefault(stageCode, null);
    }

    //判断是否存在关卡
    public boolean isExistsStage(final String stageCode) {
        return stages.containsKey(stageCode);
    }

    //得到所有关卡
    public HashMap<String, Stage> getStages() {
        return stages;
    }

    //注销所有关卡
    public void unregisterAllStages() {
        stages.clear();
        stageWorlds.clear();
    }

    //注册关卡
    public void registerStage(final Stage stage) {
        stages.put(stage.getStageCode(), stage);

        String worldName = stage.getSpawnLoc().getWorld().getName();

        if (!stageWorlds.contains(worldName)) {
            stageWorlds.add(worldName);
        }
    }

    //添加某玩家未完待续的关卡
    public void addUnfinishedStagePlayer(final String playerName, final String stageCode) {
        unfinishedStagePlayers.put(playerName, stageCode);
    }

    //得到某玩家未完待续的关卡
    public String getUnfinishedStage(final String playerName) {
        return unfinishedStagePlayers.get(playerName);
    }

    //判断是否为关卡世界
    public boolean isStageWorld(final String worldName) {
        return stageWorlds.contains(worldName);
    }
}
