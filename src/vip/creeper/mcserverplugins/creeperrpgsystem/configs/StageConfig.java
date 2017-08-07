package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.items.ItemManager;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by July_ on 2017/7/7.
 */
public class StageConfig implements RpgConfig {
    private  static JavaPlugin plugin = CreeperRpgSystem.getInstance();
    private static ItemManager mythicMobsItemManager = MythicMobs.inst().getItemManager();

    public void loadConfig() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            StageManager.unregisterAllStages();

            File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "StageConfig.yml");
            YamlConfiguration rootYml = YamlConfiguration.loadConfiguration(file);
            HashMap<String,Object> stageSections = (HashMap<String, Object>) rootYml.getConfigurationSection("stages").getValues(false);// 关卡SecMap

            for (Map.Entry<String, Object> entry : stageSections.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                MemorySection stageSection = (MemorySection) value; // 关卡Sec
                ConfigurationSection stageSpawnLocSection = stageSection.getConfigurationSection("spawn_loc"); // 关卡出生点Sec
                List<String> stageChallengeTexts = (List<String>) stageSection.getList("challenges"); // 关卡任务行文本List
                List<String> stageFinishedRewardItemTexts = (List<String>) stageSection.getList("finished_reward_items");// 关卡任务回报行文本List

                HashMap<Optional<MythicItem>, Integer> stageFinishedRewardItems = new HashMap<>(); // 关卡回报Map
                HashMap<String, Integer> stageChallenges = new HashMap<>(); // 关卡任务Map

                // 处理关卡信息 目标怪物:目标数量
                if (stageChallenges != null) {
                    for (String text : stageChallengeTexts) {
                        String[] aStageChallengeText = text.split(":");
                        stageChallenges.put(aStageChallengeText[0], Integer.parseInt(aStageChallengeText[1]));
                    }
                }

                // 处理奖励信息
                if (stageFinishedRewardItemTexts != null) {
                    for (String text : stageFinishedRewardItemTexts) {
                        String[] aStageFinishedRewardItemText = text.split(":");
                        Optional<MythicItem> item = mythicMobsItemManager.getItem(aStageFinishedRewardItemText[0]);
                        int amount = Integer.parseInt(aStageFinishedRewardItemText[1]);

                        stageFinishedRewardItems.put(item, amount);
                    }
                }

                // 注册关卡
                StageManager.registerStage(new Stage(key,
                        new Location(Bukkit.getWorld(stageSpawnLocSection.getString("world")),
                                stageSpawnLocSection.getDouble("x"),
                                stageSpawnLocSection.getDouble("y"),
                                stageSpawnLocSection.getDouble("z"),
                                Float.parseFloat(stageSpawnLocSection.getString("yaw")),
                                Float.parseFloat(stageSpawnLocSection.getString("pitch"))),
                                stageSection.getBoolean("free_stage"),
                                stageChallenges, // HashMap
                                stageSection.getStringList("confirm_messages"),
                                stageSection.getStringList("finished_deblocking_stages"),
                                stageSection.getStringList("finished_reward_commands"),
                                stageFinishedRewardItems,
                                stageSection.getBoolean("finished_confirm_spawn", false)));

                MsgUtil.info("关卡 = " + key + " 被载入.");
            }
        });
    }
}
