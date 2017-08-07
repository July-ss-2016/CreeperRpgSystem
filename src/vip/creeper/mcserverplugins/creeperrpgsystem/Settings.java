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
    public List<String> noDamageWorlds;

    public void setFirstJoinItems(List<String> firstJoinItems) {
        this.firstJoinItems = firstJoinItems;
    }

    public void setServerSpawnLocation(Location loc) {
        this.serverSpawnLocation = loc;
    }

    public void setConfigVersion(String version) {
        this.configVersion = version;
    }

    public void setStageWhitelistCommands(List<String> stageWhitelistCommands) {
        this.stageWhitelistCommands = stageWhitelistCommands;
    }

    public void setNoDamageWorlds(List<String> noDamageWorlds) {
        this.noDamageWorlds = noDamageWorlds;
    }
}
