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
    private int totalChallengeCount;
    private static HashMap<String, Integer> counter ;


    public StageMobKillingCounter(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;

        this.totalChallengeCount = 0;

        for (Map.Entry<String, Integer> entry : stage.getChallenges().entrySet()) {
            totalChallengeCount += entry.getValue();
        }

        counter = new HashMap<>();
    }

    public Player getPlayer() {
        return this.player;
    }

    //添加次数
    public void addCount(String mobName) {
        int current = counter.getOrDefault(mobName, 0);

        counter.put(mobName, current + 1);
    }

    //得到次数
    public int getCount(String mobName) {
        return counter.getOrDefault(mobName, 0);
    }

    //重置次数
    public void resetCount() {
        resetCount();
    }

    //重置次数
    public void resetCount(String mobName) {
        counter.remove(mobName);
    }

    //得到关卡
    public Stage getStage() {
        return this.stage;
    }

    //任务完成比(总) 1为完成
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
