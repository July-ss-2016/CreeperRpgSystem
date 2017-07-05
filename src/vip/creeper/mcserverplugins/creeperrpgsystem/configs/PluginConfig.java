package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import org.bukkit.configuration.file.YamlConfiguration;
import vip.creeper.mcserverplugins.creeperrpgsystem.Main;
import vip.creeper.mcserverplugins.creeperrpgsystem.Supermarket;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MessageUtil;

import java.io.File;
import java.util.HashMap;

/**
 * Created by July_ on 2017/7/4.
 */
public class PluginConfig {
    private static Main plugin = Main.getInstance();
    public static String worldSpawn;
    public static String wolrd;
    //world,super
    public static HashMap<String,Supermarket>

    public static boolean loadConfig() {
        File file = new File(FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "configs" + File.separator + "PluginConfig.yml");
        if (!file.exists()) {
            MessageUtil.info(file.getAbsolutePath() + " 被创建.");
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        System.out.println(yaml.getA);
        return false;
    }

}
