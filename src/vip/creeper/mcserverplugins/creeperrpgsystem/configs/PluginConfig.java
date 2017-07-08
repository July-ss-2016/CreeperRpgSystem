package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperrpgsystem.IConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;
import vip.creeper.mcserverplugins.creeperrpgsystem.Supermarket;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by July_ on 2017/7/4.
 */
public class PluginConfig implements IConfig {


    public void loadConfig() {
        File file = new File(FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "configs" + File.separator + "PluginConfig.yml");
        /*
        if (!file.exists()) {
            MsgUtil.info("文件 " + file.getAbsolutePath() + " 被创建.");
        }
        */
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        Settings.version = yaml.getString("version");
        Settings.spawnWorld = yaml.getString("world_spawn");
        HashMap<String,Object> marketCfgMap = (HashMap)yaml.getConfigurationSection("world_markets").getValues(false);
        //添加super信息至map
        Iterator <Map.Entry<String, Object>> iter = marketCfgMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            String worldName = entry.getKey();
            ConfigurationSection cfg = (ConfigurationSection)entry.getValue();
            MsgUtil.info("集市 " + worldName + " 被载入.");
            Settings.marketMap.put(worldName, new Supermarket(worldName, cfg.getString("displayName "), cfg.getString("welcomeMsg"), cfg.getBoolean("enterGiveHorse")));
        }
    }

}
