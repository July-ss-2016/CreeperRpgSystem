package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperrpgsystem.IConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by July_ on 2017/7/7.
 */
public class StageConfig implements IConfig {


    public void loadConfig() {
        File file = new File(FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "configs" + File.separator + "StageConfig.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        HashMap<String,Object> stageMap = (HashMap<String, Object>) yaml.getMapList("stages");
        Iterator<Map.Entry<String, Object>> iter = stageMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            String stageCode = entry.getKey();
            ConfigurationSection cfg = (ConfigurationSection)entry.getValue();
            Settings.stageMap.put(stageCode, new Stage(stageCode, cfg.getMapList("challenges"), ))
        }
    }
}
