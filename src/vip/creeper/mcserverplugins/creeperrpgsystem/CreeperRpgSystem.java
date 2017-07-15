package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.commands.*;
import vip.creeper.mcserverplugins.creeperrpgsystem.configs.MarketConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.configs.PluginConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.configs.StageConfig;
import vip.creeper.mcserverplugins.creeperrpgsystem.listeners.MarketListener;
import vip.creeper.mcserverplugins.creeperrpgsystem.listeners.PlayerListener;
import vip.creeper.mcserverplugins.creeperrpgsystem.listeners.StageListener;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.CommandManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.ConfigManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.tests.ListenerTest;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by July_ on 2017/7/4.
 */
public class CreeperRpgSystem extends JavaPlugin {
    private  boolean firstLoad = true;
    private  final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();
    private static CreeperRpgSystem instance;


    public void onEnable() {
        //阻止实例发生改变导致FileUtil出错
        if (firstLoad) {
            instance = this;
        }
        //进行初始化操作
        init();
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("插件已被卸载.");
    }

    private void init() {
        MsgUtil.info("版本 = " + PLUGIN_MANAGER.getPlugin("CreeperRpgSystem").getDescription().getVersion());
        MsgUtil.info("创建时间 = " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Util.getPluginCreationDate()));

        File playerDataFolder = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "playerdata");

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
            MsgUtil.info("文件(夹) = " + playerDataFolder.getAbsolutePath() + " 被创建.");
        }

        //注册PlaceholderAPI
        new PlaceHolderApiExpansion(instance).hook();

        registerConfigs();
        MsgUtil.info("配置已注册.");
        ConfigManager.loadAllConfig();

        getCommand("crs").setExecutor(new CommandHandler());
        registerCommands();
        MsgUtil.info("命令已注册.");

        registerListeners();
        MsgUtil.info("监听器已注册.");

        /*
        //删除集市中现有的马
        for (Map.Entry<String, Market> entry : MarketManager.getMarkets().entrySet()) {
            World world = entry.getValue().getSpawnLoc().getWorld();
            List<Entity> entityList = world.getEntities();
            for (Entity entity : entityList) {
                if (entity.getType() == EntityType.HORSE) {
                    entity.remove();
                }
            }
        }
        */
        //initTest();
    }
    //提供公开方法以获得主类实例
    public static CreeperRpgSystem getInstance() {
        return instance;
    }

    public void registerCommands() {
        CommandManager.registerCommand("market", new MarketCommand());
        CommandManager.registerCommand("reload", new ReloadCommand());
        CommandManager.registerCommand("op", new OpCommand());
        CommandManager.registerCommand("cfg", new ConfigCommand());
        CommandManager.registerCommand("stage", new StageCommand());
        CommandManager.registerCommand("inv", new InventoryCommand());
    }

    public void registerListeners() {
        PLUGIN_MANAGER.registerEvents(new MarketListener(), this);
        PLUGIN_MANAGER.registerEvents(new StageListener(), this);
        PLUGIN_MANAGER.registerEvents(new PlayerListener(), this);
    }

    public void registerConfigs() {
        ConfigManager.registerConfig(ConfigType.CONFIG_PLUGIN, new PluginConfig());
        ConfigManager.registerConfig(ConfigType.CONFIG_MARKET, new MarketConfig());
        ConfigManager.registerConfig(ConfigType.CONFIG_STAGE, new StageConfig());
    }

    public void initTest() {
        PLUGIN_MANAGER.registerEvents(new ListenerTest(), this);
    }
}
