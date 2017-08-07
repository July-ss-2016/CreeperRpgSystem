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
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.ConfigManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.RpgPlayerManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by July_ on 2017/7/4.
 */
public class CreeperRpgSystem extends JavaPlugin {
    private static final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();
    private boolean firstLoad = true;
    private static CreeperRpgSystem instance;
    private Settings settings;

    public void onLoad() {
        // 阻止实例发生改变导致FileUtil出错
        if (firstLoad) {
            instance = this;
        }

        MsgUtil.info("插件被重载!");
        RpgPlayerManager.unregisterAll();
    }

    public void onEnable() {
        settings = new Settings();

        MsgUtil.info("版本 = " + PLUGIN_MANAGER.getPlugin("CreeperRpgSystem").getDescription().getVersion());
        MsgUtil.info("创建时间 = " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Util.getPluginCreationDate()));

        File playerDataFolder = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "playerdata");

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
            MsgUtil.info("文件(夹) = " + playerDataFolder.getAbsolutePath() + " 被创建.");
        }

        // 注册PlaceholderAPI
        new PlaceHolderApiExpansion(instance).hook();

        registerConfigs();
        MsgUtil.info("配置已注册.");
        ConfigManager.loadAllConfig();

        registerCommands();
        MsgUtil.info("命令已注册.");

        registerListeners();
        MsgUtil.info("监听器已注册.");
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("插件已被卸载.");
    }

    public Settings getSettings() {
        return settings;
    }

    public static CreeperRpgSystem getInstance() {
        return instance;
    }

    private void registerCommands() {
        CommandExecutor commandExecutor = new CommandExecutor();

        commandExecutor.registerCommand("market", new MarketCommand());
        commandExecutor.registerCommand("reload", new ReloadCommand());
        // commandExecutor.registerCommand("op", new OpCommand());
        commandExecutor.registerCommand("cfg", new ConfigCommand());
        commandExecutor.registerCommand("stage", new StageCommand());
        commandExecutor.registerCommand("inv", new InventoryCommand());
        getCommand("crs").setExecutor(commandExecutor);
    }

    private void registerListeners() {
        PLUGIN_MANAGER.registerEvents(new MarketListener(), this);
        PLUGIN_MANAGER.registerEvents(new StageListener(), this);
        PLUGIN_MANAGER.registerEvents(new PlayerListener(), this);
    }

    private void registerConfigs() {
        ConfigManager.registerConfig(ConfigType.CONFIG_PLUGIN, new PluginConfig());
        ConfigManager.registerConfig(ConfigType.CONFIG_MARKET, new MarketConfig());
        ConfigManager.registerConfig(ConfigType.CONFIG_STAGE, new StageConfig());
    }
}
