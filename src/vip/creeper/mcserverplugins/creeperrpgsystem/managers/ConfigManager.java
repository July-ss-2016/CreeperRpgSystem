package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import vip.creeper.mcserverplugins.creeperrpgsystem.ConfigType;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by July_ on 2017/7/7.
 */
public class ConfigManager {
    private static final List<String> CONFIG_SUBFILE_NAMES = Arrays.asList("PluginConfig.yml", "StageConfig.yml", "MarketConfig.yml");
    private static final HashMap<ConfigType, RpgConfig> configs = new HashMap<>();


    // 载入配置
    public static boolean loadConfig(ConfigType configType) {
        if (!configs.containsKey(configType)) {
            return false;
        }

        configs.get(configType).loadConfig();
        return true;
    }

    // 注册配置
    public static void registerConfig(ConfigType configType, RpgConfig config) {
        configs.put(configType, config);
    }

    // 载入所有配置
    public static void loadAllConfig() {
        File configDataFolder = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs");

        if (!configDataFolder.exists()) {
            configDataFolder.mkdirs();
            MsgUtil.info("文件(夹) = " + configDataFolder.getAbsolutePath() + " 被创建.");
        }

        // 拷贝资源
        for (String configFileName : CONFIG_SUBFILE_NAMES) {
            File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + configFileName);

            if (!file.exists()) {
                FileUtil.copySrcFile(configFileName, FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + configFileName);
                MsgUtil.info("文件(夹) = " + file.getAbsolutePath() + " 被创建.");
            }
        }

        for (Map.Entry<ConfigType, RpgConfig> entry : configs.entrySet()) {
            entry.getValue().loadConfig();
        }
    }

    // 得到配置
    public static RpgConfig getConfig(ConfigType configType) {
        return configs.get(configType);
    }
}
