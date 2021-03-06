package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;
import vip.creeper.mcserverplugins.creeperrpgsystem.Config;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.ConfigUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;

/**
 * Created by July_ on 2017/7/4.
 */
public class PluginConfig implements Config {
    private Settings settings;

    public PluginConfig(final CreeperRpgSystem plugin) {
        this.settings = plugin.getSettings();
    }

    public void loadConfig() {
        File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "config.yml");

        if (!file.exists()) {
            FileUtil.copySrcFile("config.yml", file.getAbsolutePath());
        }

        YamlConfiguration rootYml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection spawnLocSection = rootYml.getConfigurationSection("server_spawn_loc");//服务器出生点Sec
        this.settings.configVersion = rootYml.getString("version");
        this.settings.serverSpawnLocation = new Location(Bukkit.getWorld(spawnLocSection.getString("world")), spawnLocSection.getDouble("x"), spawnLocSection.getDouble("y"), spawnLocSection.getDouble("z"),
                Float.parseFloat(spawnLocSection.getString("yaw")), Float.parseFloat(spawnLocSection.getString("pitch")));
        this.settings.stageWhitelistCommands = rootYml.getStringList("stage_whitelist_commands");
        this.settings.firstJoinItems = rootYml.getStringList("first_join_items");
        this.settings.noDamageWorlds = rootYml.getStringList("no_damage_worlds");
        MsgUtil.info("插件配置被载入.");
    }

    //设置服务器出生点
    public boolean setServerSpawnLoc(final Location loc) {
        File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "config.yml");

        return ConfigUtil.setLocConfig(file, "server_spawn_loc", loc);
    }
}
