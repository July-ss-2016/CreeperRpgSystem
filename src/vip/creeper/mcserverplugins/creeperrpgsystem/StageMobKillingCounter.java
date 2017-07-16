package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by July_ on 2017/7/12.
 */
public class StageMobKillingCounter {
    private Player player;
    private Stage stage;
    private int totalChallengeCount = 0;
    private static HashMap<String, Integer> counter ;


    public StageMobKillingCounter(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;

        for (Map.Entry<String, Integer> entry : stage.getChallenges().entrySet()) {
            totalChallengeCount += entry.getValue();
        }

        counter = new HashMap<String, Integer>();
    }

    public void addCount(String mobName) {
        int current = counter.getOrDefault(mobName, 0);

        counter.put(mobName, current + 1);
    }

    public int getCount(String mobName) {
        return counter.getOrDefault(mobName, 0);
    }

    public void resetCount(String mobName) {
        counter.remove(mobName);
    }

    public Stage getStage() {
        return this.stage;
    }

    //检查当前关卡任务是否已完成
    public boolean isFinishedChallenge() {
        for (Map.Entry<String, Integer> entry : stage.getChallenges().entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            //目标数 - 已杀数
            if (!counter.containsKey(key) || value - counter.get(key) > 0) {
                return false;
            }
        }
        return true;
    }

    //任务完成比(总)
    public double getTotalFinishingPercent() {
        //玩家真实杀怪总数，不会溢出
        int playerRealTotalMobKillingCount = 0;

        for (Map.Entry<String, Integer> entry : stage.getChallenges().entrySet()) {
            String mobName = entry.getKey();
            int maxChallengeMobKillingCount = stage.getChallenges().get(mobName);
            int playerMobKillingCount = getCount(mobName);

            //要考虑玩家击杀怪物数超过任务目标数的情况
            playerRealTotalMobKillingCount += playerMobKillingCount > maxChallengeMobKillingCount ? maxChallengeMobKillingCount : playerMobKillingCount;
        }

        return ((double)playerRealTotalMobKillingCount / (this.totalChallengeCount));
    }

    //单个怪物击杀完成比 0.x
    public double getSingleMobFinishingPercent(String mobName) {
        int killingCount = getCount(mobName);
        int challengeCount = getChallengeCount(mobName);

        return ((double)killingCount / (double)challengeCount);
    }
    //单个怪物目标击杀数
    public int getChallengeCount(String mobName) {
        return this.stage.getChallenges().get(mobName);
    }
}
