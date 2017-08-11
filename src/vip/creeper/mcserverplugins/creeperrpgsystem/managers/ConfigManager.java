package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import org.bukkit.Bukkit;
import vip.creeper.mcserverplugins.creeperrpgsystem.ConfigType;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by July_ on 2017/7/7.
 */
public class ConfigManager {
    private CreeperRpgSystem plugin;
    private HashMap<ConfigType, Config> configs;

    public ConfigManager(final CreeperRpgSystem plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
    }

    //注册配置
    public void registerConfig(final ConfigType configType, final Config config) {
        configs.put(configType, config);
    }

    //载入所有配置
    public void loadAllConfig() {
        for (Map.Entry<ConfigType, Config> entry : configs.entrySet()) {
            Bukkit.getScheduler().runTask(plugin, () -> entry.getValue().loadConfig());
        }
    }

    //得到配置
    public Config getConfig(final ConfigType configType) {
        return configs.get(configType);
    }

    //载入单个配置
    public boolean loadConfig(final ConfigType configType) {
        Config config = getConfig(configType);

        if (config != null) {
            Bukkit.getScheduler().runTask(plugin, config::loadConfig);
            return true;
        }

        return false;
    }
}
