package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by July_ on 2017/7/4.
 */
public class Main extends JavaPlugin {
    private static Main instance;

    public void onEnable() {
        instance = this;
        getLogger().info("插件初始化完毕!");
    }
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("插件已被卸载.");
    }
    //提供公开方法以获得主类实例
    public static Main getInstance() {
        return null;
    }
}
