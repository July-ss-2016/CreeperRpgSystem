package vip.creeper.mcserverplugins.creeperrpgsystem;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by July_ on 2017/7/12.
 */
public class StageMobKillingCounter {
    private Player player;
    private Stage stage;
    private int totalChallengeCount;
    private HashMap<String, Integer> counter ;

    public StageMobKillingCounter(final Player player, final Stage stage) {
        this.player = player;
        this.stage = stage;
        this.totalChallengeCount = 0;
        this.counter = new HashMap<>();

        for (Map.Entry<String, Integer> entry : stage.getChallenges().entrySet()) {
            this.totalChallengeCount += entry.getValue();
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    //添加次数
    public void addCount(final String mobName) {
        int current = this.counter.getOrDefault(mobName, 0);

        this.counter.put(mobName, current + 1);
    }

    //得到次数
    public int getCount(final String mobName) {
        return this.counter.getOrDefault(mobName, 0);
    }

    //重置次数
    public void resetCounts() {
        this.counter.clear();
    }

    //重置次数
    public void resetCount(final String mobName) {
        this.counter.remove(mobName);
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

    //单个怪物击杀数
    public int getSingleMobKillingCount(final String mobName) {
        return this.counter.getOrDefault(mobName, 0);
    }

    //单个怪物击杀完成比 0.x
    public double getSingleMobFinishingPercent(final String mobName) {
        int killingCount = getCount(mobName);
        int challengeCount = getChallengeCount(mobName);

        return ((double)killingCount / (double)challengeCount);
    }

    //单个怪物目标击杀数
    public int getChallengeCount(final String mobName) {
        return this.stage.getChallenges().get(mobName);
    }

    public String getFirstNeedKillMobCode() {
        Iterator iter = stage.getChallenges().entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next();
            String mobName = entry.getKey();
            int challengeCount = entry.getValue();

            if (getSingleMobKillingCount(mobName) < challengeCount) {
                return mobName;
            }
        }
        return null;
    }
}
