package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.impls.ConfigImpl;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by July_ on 2017/7/7.
 */
public class StageConfig implements ConfigImpl {
    private  static JavaPlugin plugin = CreeperRpgSystem.getInstance();

    public void loadConfig() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            StageManager.unregisterAllStages();

            File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "StageConfig.yml");
            YamlConfiguration rootYml = YamlConfiguration.loadConfiguration(file);
            HashMap<String,Object> stageSections = (HashMap<String, Object>) rootYml.getConfigurationSection("stages").getValues(false);//关卡SecMap

            for (Map.Entry<String, Object> entry : stageSections.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                MemorySection stageSection = (MemorySection) value;//关卡Se
                ConfigurationSection stageSpawnLocSection = stageSection.getConfigurationSection("spawn_loc");//关卡出生点Sec
                List<String> stageChallengeList = (List<String>) stageSection.getList("challenges");//关卡任务List
                HashMap<String, Integer> stageChallengeMap = new HashMap<>();//关卡任务Map

                //添加任务信息到map
                for (String aStageChallengeList : stageChallengeList) {
                    String[] stageChallengeInfoArr = aStageChallengeList.split(":");
                    stageChallengeMap.put(stageChallengeInfoArr[0], Integer.parseInt(stageChallengeInfoArr[1]));
                }

                StageManager.registerStage(new Stage(key, new Location(Bukkit.getWorld(stageSpawnLocSection.getString("world")), stageSpawnLocSection.getDouble("x"), stageSpawnLocSection.getDouble("y"),
                        stageSpawnLocSection.getDouble("z"), Float.parseFloat(stageSpawnLocSection.getString("yaw")), Float.parseFloat(stageSpawnLocSection.getString("pitch"))), stageChallengeMap, stageSection.getStringList("enter_messages"),
                        stageSection.getStringList("finishing_commands"), stageSection.getBoolean("free_stage"), stageSection.getStringList("finishing_deblocking_stages")));
                MsgUtil.info("关卡 = " + key + " 被载入.");
            }
        });
    }
}
