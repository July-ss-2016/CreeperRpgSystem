package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.Location;

import java.util.List;

/**
 * Created by July_ on 2017/7/7.
 */
public class Settings {
    public Location serverSpawnLocation;
    public String configVersion;
    public List<String> stageWhitelistCommands;
    public List<String> firstJoinItems;

    public void setFirstJoinItems(List<String> firstJoinItems) {
        this.firstJoinItems = firstJoinItems;
    }

    public void setServerSpawnLocation(Location loc) {
        this.serverSpawnLocation = loc;
    }

    public void setConfigVersion(String version) {
        this.configVersion = version;
    }

    public void setStageWhitelistCommands(List<String> list) {
        this.stageWhitelistCommands = list;
    }

    public Location getServerSpawnLocation() {
        return this.serverSpawnLocation;
    }

    public String getConfigVersion() {
        return this.configVersion;
    }

    public List<String> getStageWhitelistCommands() {
        return this.stageWhitelistCommands;
    }

    public List<String> getFirstJoinItems() {
        return this.firstJoinItems;
    }
}
