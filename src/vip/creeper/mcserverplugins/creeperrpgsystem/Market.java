package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.MarketEnterEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.ConfigUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;

import java.io.File;

/**
 * Created by July_ on 2017/7/4.
 */
public class Market {
    private String marketCode;
    private Location spawnLoc;
    private String displayName;
    private boolean enterGiveHorse;
    private String welcomeMsg;

    public Market(String marketCode, Location spawnLoc, String displayName, String welcomeMsg, boolean enterGiveHorse) {
        this.marketCode = marketCode;
        this.spawnLoc = spawnLoc;
        this.displayName = displayName;
        this.welcomeMsg = welcomeMsg;
        this.enterGiveHorse = enterGiveHorse;
    }

    // 得到集市代码
    public String getMarketCode() {
        return marketCode;
    }

    // 得到出生点
    public Location getSpawnLoc() {
        return this.spawnLoc;
    }

    // 得到商店名
    public String getDisplayName() {
        return this.displayName;
    }

    // 得到欢迎信息
    public String getWelcomeMsg() {
        return this.welcomeMsg;
    }

    // 得到是否进入给马
    public boolean getEnterGiveHorse() {
        return this.enterGiveHorse;
    }

    // 传送至关卡出生点
    public void tp(Player player) {
        player.teleport(spawnLoc);
        Bukkit.getPluginManager().callEvent(new MarketEnterEvent(player, this));
    }

    // 设置出生点
    public boolean setSpawnLocation(Location loc) {
        File file = new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "configs" + File.separator + "MarketConfig.yml");
        return ConfigUtil.setLocConfig(file, "markets." + marketCode + ".spawn_loc", loc);
    }
}
