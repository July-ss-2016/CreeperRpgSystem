package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import org.bukkit.Bukkit;
import vip.creeper.mcserverplugins.creeperrpgsystem.ConfigType;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by July_ on 2017/7/7.
 */
public class ConfigManager {
    private static CreeperRpgSystem plugin = CreeperRpgSystem.getInstance();
    private static final HashMap<ConfigType, RpgConfig> configs = new HashMap<>();

    //注册配置
    public static void registerConfig(final ConfigType configType, final RpgConfig config) {
        configs.put(configType, config);
    }

    //载入所有配置
    public static void loadAllConfig() {
        for (Map.Entry<ConfigType, RpgConfig> entry : configs.entrySet()) {
            Bukkit.getScheduler().runTask(plugin, () -> entry.getValue().loadConfig());
        }
    }

    //得到配置
    public static RpgConfig getConfig(final ConfigType configType) {
        return configs.get(configType);
    }

    //载入单个配置
    public static boolean loadConfig(final ConfigType configType) {
        RpgConfig config = getConfig(configType);

        if (config != null) {
            config.loadConfig();
            return true;
        }

        return false;
    }
}
