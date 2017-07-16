package vip.creeper.mcserverplugins.creeperrpgsystem.listeners;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgPlayer;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.StageMobKillingCounter;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.RpgMobKilledByPlayerEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.StageEnterEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.StageFinishedEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.StageLeaveEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.RpgPlayerManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

import java.util.HashMap;

/**
 * Created by July_ on 2017/7/7.
 */
public class StageListener implements Listener {
    private JavaPlugin plugin = CreeperRpgSystem.getInstance();
    private BukkitAPIHelper mythicMobApi = MythicMobs.inst().getAPIHelper();
    private HashMap<String, StageMobKillingCounter> playerStageMobCounters = new HashMap<String, StageMobKillingCounter>();    //玩家名对应怪物单关卡击杀数统计器


    //_触发事件_关卡怪物死亡
    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        LivingEntity target = event.getEntity();
        Entity killer = target.getKiller();

        if (killer == null) {
            return;
        }

        Player player = (Player)killer;
        String playerName = player.getName();

        if (mythicMobApi.isMythicMob(target)) {
            //触发专用事件
            Bukkit.getPluginManager().callEvent(new RpgMobKilledByPlayerEvent(player, mythicMobApi.getMythicMobInstance(target).getType()));
        }
    }

    //_触发事件_离开关卡_切换世界
    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (StageManager.isStageWorld(event.getFrom().getName()) && playerStageMobCounters.containsKey(playerName)) {
            Bukkit.getPluginManager().callEvent(new StageLeaveEvent(player, playerStageMobCounters.get(playerName).getStage()));
        }
    }

    //事件_RPG怪物被击杀
    @EventHandler
    public void onRpgMobKilledByPlayerEvent(RpgMobKilledByPlayerEvent event) {
        Player player = event.getKiller();
        String playerName = player.getName();
        MythicMob mythicMob = event.getMythicMob();

        //这个也没必要
        /*
        if (!StageManager.isStageWorld(player.getWorld().getName())) {
            return;
        }
        */

        String mobCode = mythicMob.getInternalName();

        if (!playerStageMobCounters.containsKey(playerName)) {
            Util.teleportToServerSpawnPoint(player);
            MsgUtil.sendMsg(player, "&c怪物计数器不存在!");
            return;
        }

        //smc成员变量是静态的
        StageMobKillingCounter smk = playerStageMobCounters.get(playerName);
        Stage stage = smk.getStage();

        //这里为了确保安全，判断下是否为非本关卡的怪物
        if (!stage.isChallengeMob(mobCode)) {
            MsgUtil.warring("&c非预料中的怪物.");
            return;
        }

        //添加次数
        smk.addCount(mobCode);

        //任务完成百分比
        double totalFinishingPercent = smk.getTotalFinishingPercent();

        //发送subtitle告知玩家任务进度
        MsgUtil.sendSubTitle(player, "&d任务进度 &b> &d" + (int)(totalFinishingPercent * 100) + "%");

        //完成任务，触发事件，让事件去处理
        if (totalFinishingPercent == 1) {
            Bukkit.getPluginManager().callEvent(new StageFinishedEvent(RpgPlayerManager.getRpgPlayer(playerName), stage));
        }
    }

    //事件_完成任务
    @EventHandler
    public void onStageFinishedEvent(StageFinishedEvent event) {
        RpgPlayer rpgPlayer = event.getRpgPlayer();
        Player bukkitPlayer = rpgPlayer.getBukkitPlayer();
        String playerName = bukkitPlayer.getName();
        Stage stage = event.getStage();
        String stageCode = stage.getStageCode();
        boolean giveFinishedRewardKitResult = false;

        //设置通关印记
        rpgPlayer.setStageState(stageCode, true);
        Util.spawnFirework(bukkitPlayer.getLocation());
        playerStageMobCounters.remove(playerName);
        stage.performFinishedRewardCommands(bukkitPlayer);
        giveFinishedRewardKitResult = stage.giveFinishedRewardItems(bukkitPlayer);
        MsgUtil.sendBroadcastMsg("&d玩家 &b" + playerName + " &d成功通过了关卡 &b" + stageCode + "&d !");
        MsgUtil.sendTitle(bukkitPlayer, "&d任务完成");

        if (giveFinishedRewardKitResult) {
            MsgUtil.sendMsg(bukkitPlayer, "&d任务奖励成功发放!");
        } else {
            MsgUtil.sendMsg(bukkitPlayer, "&d任务奖励发放失败!原因:背包空间不足,StageCode=" + stageCode + ".");
        }

        MsgUtil.sendMsg(bukkitPlayer, "&e5秒后 你将被传送到主城.");
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Bukkit.getScheduler().runTask(plugin, () -> Util.teleportToServerSpawnPoint(bukkitPlayer)), 100L);
        return;
    }

    //事件_进入关卡
    @EventHandler
    public void onStageEnterEvent(StageEnterEvent event) {
        Player player = event.getPlayer();
        Stage stage = event.getStage();
        String playerName = player.getName();

        //不含key，或含key但关卡已变
        if (!playerStageMobCounters.containsKey(playerName) || playerStageMobCounters.containsKey(playerName) && !playerStageMobCounters.get(playerName).getStage().getStageCode().equals(stage.getStageCode())) {
            MsgUtil.warring("c");
            playerStageMobCounters.put(playerName, new StageMobKillingCounter(player, stage));
        }

        MsgUtil.sendTitle(player, "&c开始作战");
    }
}
