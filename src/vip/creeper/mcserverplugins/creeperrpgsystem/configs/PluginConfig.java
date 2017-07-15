package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.impls.ConfigImpl;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;

/**
 * Created by July_ on 2017/7/4.
 */
public class PluginConfig implements ConfigImpl {
    private JavaPlugin plugin = CreeperRpgSystem.getInstance();


    public void loadConfig() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "PluginConfig.yml");
            YamlConfiguration rootYml = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection spawnLocSection = rootYml.getConfigurationSection("server_spawn_loc");//服务器出生点Sec

            Settings.version = rootYml.getString("version");
            Settings.spawnLoc = new Location(Bukkit.getWorld(spawnLocSection.getString("world")), spawnLocSection.getDouble("x"), spawnLocSection.getDouble("y"), spawnLocSection.getDouble("z"),
                    Float.parseFloat(spawnLocSection.getString("yaw")), Float.parseFloat(spawnLocSection.getString("pitch")));
            Settings.stageWhitelistCommands = rootYml.getStringList("stage_whitelist_commands");
            MsgUtil.info("插件配置已载入.");
        });
    }

}
