package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by July_ on 2017/7/12.
 */
public class ConfigUtil {


    public static boolean setLocConfig(final File file, final String path, final Location loc) {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

        yml.set(path + ".world", loc.getWorld().getName());
        yml.set(path + ".x", loc.getX());
        yml.set(path + ".y", loc.getY());
        yml.set(path + ".z", loc.getZ());
        yml.set(path + ".yaw", loc.getYaw());
        yml.set(path + ".pitch", loc.getPitch());

        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
