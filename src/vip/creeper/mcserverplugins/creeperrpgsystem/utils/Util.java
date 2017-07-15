package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;

import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 * Created by July_ on 2017/7/5.
 */
public class Util {
    private static final Random random = new Random();


    //得到随机值
    public static int getRandomValue(final int min, final int max) {
        return random.nextInt(max - min +1) + min;
    }

    //判断是否为玩家
    public static boolean isPlayer(final CommandSender cs) {
        return (cs instanceof Player);
    }

    //判断是否为管理员
    public static boolean isAdmin(Player player) {
        return player.hasPermission("crs.admin");
    }

    //回主城
    public static void teleportToSpawnPoint(Player player) {
        player.teleport(Settings.spawnLoc);
    }

    //产生烟花
    public static void spawnFirework(Location loc) {
        Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        FireworkEffect fireworkEffect = FireworkEffect.builder().withColor(Color.GREEN).withColor(Color.PURPLE).withColor(Color.RED).withColor(Color.ORANGE).with(FireworkEffect.Type.STAR).withFade(Color.YELLOW).withFade(Color.RED).withTrail().build();

        fireworkMeta.addEffect(fireworkEffect);
        fireworkMeta.setPower(0);
        firework.setFireworkMeta(fireworkMeta);

        //firework.detonate();
    }

    //得到插件创建时间
    public static Date getPluginCreationDate() {
        File file = new File(CreeperRpgSystem.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        return (new Date(file.lastModified()));
    }
}
