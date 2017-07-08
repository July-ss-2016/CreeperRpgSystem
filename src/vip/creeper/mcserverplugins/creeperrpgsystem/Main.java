package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.commands.PlayerCommand;
import vip.creeper.mcserverplugins.creeperrpgsystem.configs.PluginConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.configs.StageConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.listeners.MarketListener;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.ConfigManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by July_ on 2017/7/4.
 */
public class Main extends JavaPlugin {
    private IConfig pluginConfig;
    private IConfig stageConfig;
    private static Main instance;

    /*
    public static void main(String[] args) {

    }
    */

    public void onEnable() {
        pluginConfig = new PluginConfig();
        stageConfig = new StageConfig();
        instance = this;
        init();//初始化操作
        getLogger().info("插件初始化完毕!");
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);//注销本插件所有线程
        //删除集市中现有的马
        Iterator<Map.Entry<String, Supermarket>> iter = Settings.marketMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Supermarket> entry = iter.next();
            MsgUtil.info(entry.getKey());
            World world = Bukkit.getWorld(entry.getKey());
            List<Entity> entityList = world.getEntities();
            for (int i = 0; i < entityList.size(); i++) {
                Entity entity = entityList.get(i);
                if (entity.getType() == EntityType.HORSE) {
                    entity.remove();
                }
            }
        }
        getLogger().info("插件已被卸载.");
    }

    public void init() {
        File playerDataFolder = new File(FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
            MsgUtil.info("文件(夹) " + playerDataFolder.getAbsolutePath() + " 被创建.");
        }
        ConfigManager.loadAllConfig();
        /*
        //删除集市中现有的马
        Iterator<Map.Entry<String, Supermarket>> iter = Settings.marketMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Supermarket> entry = iter.next();
            MsgUtil.info(entry.getKey());
            World world = Bukkit.getWorld(entry.getKey());
            List<Entity> entityList = world.getEntities();
            for (int i = 0; i < entityList.size(); i++) {
                Entity entity = entityList.get(i);
                if (entity.getType() == EntityType.HORSE) {
                    entity.remove();
                }
            }
        }
        */
        Bukkit.getPluginManager().registerEvents(new MarketListener(), this);
        getCommand("crs").setExecutor(new PlayerCommand());
    }
    //提供公开方法以获得主类实例
    public static Main getInstance() {
        return instance;
    }

    public IConfig getPluginConfig() {
        return pluginConfig;
    }

    public IConfig getStageConfig() {
        return stageConfig;
    }
}
