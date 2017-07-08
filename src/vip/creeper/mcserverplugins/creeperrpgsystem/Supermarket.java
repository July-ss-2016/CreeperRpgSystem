package vip.creeper.mcserverplugins.creeperrpgsystem;

/**
 * Created by July_ on 2017/7/4.
 */
public class Supermarket {
    private String worldName;
    private String displayName;
    private boolean enterGiveHorse;
    private String welcomeMsg;

    public Supermarket(String worldName, String displayName, String welcomeMsg, boolean enterGiveHorse) {
        this.worldName = worldName;
        this.displayName = displayName;
        this.welcomeMsg = welcomeMsg;
        this.enterGiveHorse = enterGiveHorse;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean getEnterGiveHorse() {
        return this.enterGiveHorse;
    }

    public String getWelcomeMsg() {
        return this.welcomeMsg;
    }
}
