package vip.creeper.mcserverplugins.creeperrpgsystem.configs;

import vip.creeper.mcserverplugins.creeperrpgsystem.Main;

/**
 * Created by July_ on 2017/7/4.
 */
public class PluginConfig {
    private static Main plugin = Main.getInstance();
    public static String worldSpawn;
    public static String wolrd;


    public static boolean loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        return false;
    }

}
