package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import org.bukkit.Location;
import vip.creeper.mcserverplugins.creeperrpgsystem.ConfigType;
import vip.creeper.mcserverplugins.creeperrpgsystem.impls.ConfigImpl;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.ConfigUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.*;

/**
 * Created by July_ on 2017/7/7.
 */
public class ConfigManager {
    private static final List<String> CONFIG_SUBFILE_NAMES = Arrays.asList("PluginConfig.yml", "StageConfig.yml", "MarketConfig.yml");
    private static final HashMap<ConfigType, ConfigImpl> configs = new HashMap<>();


    public static boolean loadConfig(ConfigType configType) {
        if (!configs.containsKey(configType)) {
            return false;
        }

        configs.get(configType).loadConfig();
        return true;
    }

    public static void registerConfig(ConfigType configType, ConfigImpl config) {
        configs.put(configType, config);
    }

    public static void loadAllConfig() {
        File configDataFolder = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs");

        if (!configDataFolder.exists()) {
            configDataFolder.mkdirs();
            MsgUtil.info("文件(夹) = " + configDataFolder.getAbsolutePath() + " 被创建.");
        }

        //拷贝资源
        for (String configFileName : CONFIG_SUBFILE_NAMES) {
            File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + configFileName);

            if (!file.exists()) {
                FileUtil.copySrcFile(configFileName, FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + configFileName);
                MsgUtil.info("文件(夹) = " + file.getAbsolutePath() + " 被创建.");
            }
        }

        for (Map.Entry<ConfigType, ConfigImpl> entry : configs.entrySet()) {
            entry.getValue().loadConfig();
        }
    }

    public static boolean setServerSpawnLoc(Location loc) {
        File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "PluginConfig.yml");

        return ConfigUtil.setLocConfig(file, "server_spawn_loc", loc);
    }

}
