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
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.MarketManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.RpgPlayerManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by July_ on 2017/7/4.
 */
public class CreeperRpgSystem extends JavaPlugin {
    private static CreeperRpgSystem instance;
    private final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();
    private Settings settings;
    private ConfigManager configManager;
    private MarketManager marketManager;
    private RpgPlayerManager rpgPlayerManager;
    private StageManager stageManager;
    private boolean isFirstLoad = true;

    public void onLoad() {
        //阻止实例发生改变导致FileUtil出错
        if (isFirstLoad) {
            instance = this;
            this.isFirstLoad = false;
        } else {
            CreeperRpgSystem.getInstance().getRpgPlayerManager().unregisterAll();
        }

        MsgUtil.info("插件被重载!");
    }

    public void onEnable() {
        this.settings = new Settings();
        this.configManager = new ConfigManager(this);
        this.marketManager = new MarketManager();
        this.rpgPlayerManager = new RpgPlayerManager();
        this.stageManager = new StageManager();

        MsgUtil.info("版本 = " + PLUGIN_MANAGER.getPlugin("CreeperRpgSystem").getDescription().getVersion());
        MsgUtil.info("创建时间 = " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Util.getPluginCreationDate()));

        File playerDataFolder = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "playerdata");

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }

        //注册PlaceholderAPI
        new PlaceHolderApiExpansion(this).hook();
        registerConfigs();
        MsgUtil.info("配置已注册.");
        this.configManager.loadAllConfig();
        registerCommands();
        MsgUtil.info("命令已注册.");
        registerListeners();
        MsgUtil.info("监听器已注册.");
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("插件已被卸载.");
    }

    public static CreeperRpgSystem getInstance() {
        return instance;
    }

    private void registerCommands() {
        CommandExecutor commandExecutor = new CommandExecutor();

        commandExecutor.registerCommand("market", new MarketCommand());
        commandExecutor.registerCommand("reload", new ReloadCommand());
        commandExecutor.registerCommand("cfg", new ConfigCommand());
        commandExecutor.registerCommand("stage", new StageCommand());
        commandExecutor.registerCommand("inv", new InventoryCommand());
        getCommand("crs").setExecutor(commandExecutor);
    }

    private void registerListeners() {
        this.PLUGIN_MANAGER.registerEvents(new MarketListener(this), this);
        this.PLUGIN_MANAGER.registerEvents(new StageListener(this), this);
        this.PLUGIN_MANAGER.registerEvents(new PlayerListener(this), this);
    }

    private void registerConfigs() {
        this.configManager.registerConfig(ConfigType.CONFIG_PLUGIN, new PluginConfig(this));
        this.configManager.registerConfig(ConfigType.CONFIG_MARKET, new MarketConfig());
        this.configManager.registerConfig(ConfigType.CONFIG_STAGE, new StageConfig());
    }

    public Settings getSettings() {
        return this.settings;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public MarketManager getMarketManager() {
        return this.marketManager;
    }

    public RpgPlayerManager getRpgPlayerManager() {
        return this.rpgPlayerManager;
    }

    public StageManager getStageManager() {
        return this.stageManager;
    }
}
