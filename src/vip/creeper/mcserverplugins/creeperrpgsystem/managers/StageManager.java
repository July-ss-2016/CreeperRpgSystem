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
    private static HashMap<String, Stage> stages = new HashMap<>();
    private static HashMap<String, String> unfinishedStagePlayers = new HashMap<>();
    private static List<String> stageWorlds = new ArrayList<>();

    public static boolean setPlayerStagePassed(String playerName, String stageCode, boolean state) {
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

    // 通过关卡代码得到关卡
    public static Stage getStage(String stageCode) {
        return stages.getOrDefault(stageCode, null);
    }

    // 判断是否存在关卡
    public static boolean isExistsStage(String stageCode) {
        return stages.containsKey(stageCode);
    }

    // 得到所有关卡
    public static HashMap<String, Stage> getStages() {
        return stages;
    }

    // 注销所有关卡
    public static void unregisterAllStages() {
        stages.clear();
        stageWorlds.clear();
    }

    // 注册关卡
    public static void registerStage(Stage stage) {
        stages.put(stage.getStageCode(), stage);

        String worldName = stage.getSpawnLoc().getWorld().getName();

        if (!stageWorlds.contains(worldName)) {
            stageWorlds.add(worldName);
        }
    }

    // 添加某玩家未完待续的关卡
    public static void addUnfinishedStagePlayer(String playerName, String stageCode) {
        unfinishedStagePlayers.put(playerName, stageCode);
    }

    // 得到某玩家未完待续的关卡
    public static String getUnfinishedStage(String playerName) {
        return unfinishedStagePlayers.get(playerName);
    }

    // 判断是否为关卡世界
    public static boolean isStageWorld(String worldName) {
        return stageWorlds.contains(worldName);
    }
}
