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

    public void setFirstJoinItems(final List<String> firstJoinItems) {
        this.firstJoinItems = firstJoinItems;
    }

    public void setServerSpawnLocation(final Location loc) {
        this.serverSpawnLocation = loc;
    }

    public void setConfigVersion(final String version) {
        this.configVersion = version;
    }

    public void setStageWhitelistCommands(final List<String> stageWhitelistCommands) {
        this.stageWhitelistCommands = stageWhitelistCommands;
    }

    public void setNoDamageWorlds(final List<String> noDamageWorlds) {
        this.noDamageWorlds = noDamageWorlds;
    }
}
