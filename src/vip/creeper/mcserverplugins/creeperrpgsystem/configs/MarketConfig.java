package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Market;
import vip.creeper.mcserverplugins.creeperrpgsystem.impls.ConfigImpl;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.MarketManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by July_ on 2017/7/9.
 */
public class MarketConfig implements ConfigImpl {
    private static CreeperRpgSystem plugin = CreeperRpgSystem.getInstance();


    public void loadConfig() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            MarketManager.unregisterAllMarket();

            File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "MarketConfig.yml");
            YamlConfiguration rootYml = YamlConfiguration.loadConfiguration(file);
            HashMap<String, Object> marketSections = (HashMap<String, Object>) rootYml.getConfigurationSection("markets").getValues(false);//集市SecMap

            for (Map.Entry<String, Object> entry : marketSections.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                ConfigurationSection marketSection = (ConfigurationSection) value;//集市Sec
                ConfigurationSection marketSpawnLocSection = marketSection.getConfigurationSection("spawn_loc");//出生点位置Sec
                String worldName = marketSpawnLocSection.getString("world");//出生点位置
                Location marketSpawnLoc = new Location(Bukkit.getWorld(worldName), marketSpawnLocSection.getDouble("x"), marketSpawnLocSection.getDouble("y"), marketSpawnLocSection.getDouble("z"),
                        Float.parseFloat(marketSpawnLocSection.getString("yaw")), Float.parseFloat(marketSpawnLocSection.getString("pitch")));

                MarketManager.registerMarket(new Market(key, marketSpawnLoc, marketSection.getString("display_name"), marketSection.getString("welcome_msg"), marketSection.getBoolean("enter_give_horse")));
                MsgUtil.info("集市 = " + key + " 被载入.");
            }
        });
    }
}
