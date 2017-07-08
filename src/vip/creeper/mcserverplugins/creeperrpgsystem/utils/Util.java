package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import vip.creeper.mcserverplugins.creeperrpgsystem.Settings;

import java.util.Random;

/**
 * Created by July_ on 2017/7/5.
 */
public class Util {
    private static Random random = new Random();

    public static boolean isSupermarketWorld(final String world) {
        return Settings.marketMap.containsKey(world);
    }

    public static int getRandomValue(final int min, final int max) {
        return random.nextInt(max - min +1) + min;
    }

}
