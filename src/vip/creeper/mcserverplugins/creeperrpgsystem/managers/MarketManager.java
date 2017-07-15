package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import vip.creeper.mcserverplugins.creeperrpgsystem.Market;

import java.util.HashMap;

/**
 * Created by July_ on 2017/7/9.
 */
public class MarketManager {
    private static HashMap<String, Market> stageCodeForMarkets = new HashMap<>();
    private static HashMap<String, Market> worldNameForMarkets = new HashMap<>();

    //注册集市
    public static void registerMarket(Market market) {
        String marketCode = market.getMarketCode();

        stageCodeForMarkets.put(marketCode, market);
        worldNameForMarkets.put(market.getSpawnLoc().getWorld().getName(), market);
    }

    //判断是否存在集市
    public static boolean isExistsMarket(String marketCode) {
        return stageCodeForMarkets.containsKey(marketCode);
    }

    //通过世界名获得集市
    public static Market getMarketByWorldName(String worldName) {
        return worldNameForMarkets.getOrDefault(worldName, null);
    }

    //通过集市代码获得集市
    public static Market getMarketByMarketCode(String marketCode) {
        return stageCodeForMarkets.getOrDefault(marketCode, null);
    }

    //得到所有集市
    public static HashMap<String, Market> getMarkets() {
        return stageCodeForMarkets;
    }

    //根据世界名判断是否为集市世界
    public static boolean isMarketWorld(String worldName) {
        return worldNameForMarkets.containsKey(worldName);
    }

    //注销所有集市
    public static void unregisterAllMarket() {
        stageCodeForMarkets.clear();
        worldNameForMarkets.clear();
    }
}


