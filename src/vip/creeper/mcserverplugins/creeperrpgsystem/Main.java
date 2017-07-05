package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.configs.PluginConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MessageUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by July_ on 2017/7/4.
 */
public class Main extends JavaPlugin {
    public static final List<String> CONFIG_SUBFILE_NAME_LIST = Arrays.asList("PluginConfig.yml");
    private static Main instance;

    public void onEnable() {
        instance = this;
        init();//初始化操作
        PluginConfig.loadConfig();
        getLogger().info("插件初始化完毕!");
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);//注销本插件所有线程
        getLogger().info("插件已被卸载.");
    }

    public void init() {
        File configDataFolder = new File(FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "configs");
        if (!configDataFolder.exists()) {
            configDataFolder.mkdirs();
            MessageUtil.info(configDataFolder.getAbsolutePath() + " 被创建.");
        }
        //拷贝资源
        for (int i = 0; i < CONFIG_SUBFILE_NAME_LIST.size(); i++) {
            String configFileName = CONFIG_SUBFILE_NAME_LIST.get(i);
            File file = new File(FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "configs" + File.separator + configFileName);
            if (!file.exists()) {
                FileUtil.copySrcFile(CONFIG_SUBFILE_NAME_LIST.get(i), FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "configs" + File.separator + configFileName);
            }
        }
    }
    //提供公开方法以获得主类实例
    public static Main getInstance() {
        return instance;
    }
}
