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
import org.bukkit.event.player.PlayerRespawnEvent;
import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;
import vip.creeper.mcserverplugins.creeperrpgsystem.RpgPlayer;
import vip.creeper.mcserverplugins.creeperrpgsystem.Stage;
import vip.creeper.mcserverplugins.creeperrpgsystem.StageMobKillingCounter;
import vip.creeper.mcserverplugins.creeperrpgsystem.events.*;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.RpgPlayerManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.managers.StageManager;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.Util;

import java.util.HashMap;

/**
 * Created by July_ on 2017/7/7.
 */
public class StageListener implements Listener {
    private CreeperRpgSystem plugin = CreeperRpgSystem.getInstance();
    private BukkitAPIHelper mythicMobApi = MythicMobs.inst().getAPIHelper();
    private HashMap<String, StageMobKillingCounter> playerStageMobCounters = new HashMap<>(); //玩家名对应怪物单关卡击杀数统计器

    //事件_RPG怪物被击杀
    @EventHandler
    public void onRpgMobKilledByPlayerEvent(RpgMobKilledByPlayerEvent event) {
        Player player = event.getKiller();
        String playerName = player.getName();
        MythicMob mythicMob = event.getMythicMob();
        String mobCode = mythicMob.getInternalName();

        //为了安全先判断玩家是否有计数器
        if (!playerStageMobCounters.containsKey(playerName)) {
            Util.teleportToServerSpawnPoint(player);
            MsgUtil.sendMsg(player, "&c系统错误:怪物计数器不存在.");
            return;
        }


        StageMobKillingCounter stageMobKillingCounter = playerStageMobCounters.get(playerName);
        Stage stage = stageMobKillingCounter.getStage();

        //这里为了确保安全，判断下是否为非本关卡的怪物
        if (!stage.isChallengeMob(mobCode)) {
            MsgUtil.sendMsg(player, "&c系统错误:非预料中的怪物.");
            return;
        }

        //添加次数
        stageMobKillingCounter.addCount(mobCode);

        //任务完成百分比
        double totalFinishingPercent = stageMobKillingCounter.getTotalFinishingPercent();

        //发送SubTitle告知玩家任务进度
        MsgUtil.sendSubTitle(player, "&d任务进度 &b> &d" + (int)(totalFinishingPercent * 100) + "%");

        //完成任务，触发事件，让指定事件去处理
        if (totalFinishingPercent == 1) {
            Bukkit.getPluginManager().callEvent(new StageFinishedEvent(RpgPlayerManager.getRpgPlayer(playerName), stage));
        } else {
            //提示需要下一个攻击的目标
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Bukkit.getScheduler().runTask(plugin, () -> {
                String firstNeedKillMobCode = stageMobKillingCounter.getFirstNeedKillMobCode();

                if (firstNeedKillMobCode != null) {
                    MythicMob firstNeedKillMob = mythicMobApi.getMythicMob(firstNeedKillMobCode);

                    if (firstNeedKillMob != null) {
                        MsgUtil.sendSubTitle(player, "&c目标 &b> &c" + firstNeedKillMob.getDisplayName());
                    } else {
                        MsgUtil.sendMsg(player,"&c系统错误:配置中存在不存在的怪物!");
                    }
                } else {
                    MsgUtil.sendMsg(player,"&c系统错误:First need killing mobs not exist.");
                }
            }), 30L); //1.5s
        }
    }

    //事件_完成任务
    @EventHandler
    public void onStageFinishedEvent(StageFinishedEvent event) {
        RpgPlayer rpgPlayer = event.getRpgPlayer();
        Player bukkitPlayer = rpgPlayer.getPlayer();
        String playerName = bukkitPlayer.getName();
        Stage stage = event.getStage();
        String stageCode = stage.getStageCode();

        //解锁关卡
        for (String deblockingStage : stage.getFinishedDeblockingStages()) {
            if (!rpgPlayer.getStageState(deblockingStage)) {

                if (rpgPlayer.setStageState(deblockingStage, true)) {
                    MsgUtil.sendBroadcastMsg("&d玩家 &b" + playerName + " &d解锁了关卡 &b" + deblockingStage + "&d !");
                } else {
                    MsgUtil.sendMsg(bukkitPlayer, "§c系统错误:解锁关卡失败.");
                }

            }
        }

        Util.spawnFirework(bukkitPlayer.getLocation()); //释放烟花
        playerStageMobCounters.get(playerName).resetCounts(); //清空次数
        stage.performFinishedRewardCommands(bukkitPlayer); //执行指令

        //发放奖励
        if (!stage.isNoFinishedRewardItem()) { //需要排除没有奖励的关卡
            if (stage.giveFinishedRewardItems(bukkitPlayer)) {
                MsgUtil.sendMsg(bukkitPlayer, "&d已获得任务奖励!");
            } else {
                MsgUtil.sendMsg(bukkitPlayer, "&d任务奖励发放失败!原因:背包空间不足,关卡代码=" + stageCode + ".");
            }
        }

        MsgUtil.sendTitle(bukkitPlayer, "&d任务完成");

        //判断是否需要确认过后才能回主城，而不是自动的
        if (stage.isFinishedConfirmSpawn()) {
            MsgUtil.sendMsg(bukkitPlayer, "&e您已经通过了本关卡,你可以选择马上回到主城,也可以选择继续刷怪集齐目标数量的RPG怪物掉落物~");
            MsgUtil.sendRawMsg(bukkitPlayer, "[\"\",{\"text\":\"" + MsgUtil.HEAD_MSG + "『点我回主城』\",\"color\":\"yellow\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/spawn\"}}]");
        } else {
            bukkitPlayer.setHealth(bukkitPlayer.getMaxHealth()); //恢复满血
            bukkitPlayer.setNoDamageTicks(200); //无敌10s
            MsgUtil.sendMsg(bukkitPlayer, "&e6秒后 你将被传送到主城.");

            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Bukkit.getScheduler().runTask(plugin, () -> Util.teleportToServerSpawnPoint(bukkitPlayer)), 120L);
        }

    }

    // 事件_进入关卡
    @EventHandler
    public void onStageEnterEvent(StageEnterEvent event) {
        Player player = event.getPlayer();
        Stage stage = event.getStage();
        String playerName = player.getName();

        // 不含key，或含key但关卡已变
        if (!playerStageMobCounters.containsKey(playerName) || playerStageMobCounters.containsKey(playerName) && !playerStageMobCounters.get(playerName).getStage().getStageCode().equals(stage.getStageCode())) {
            //存储计数器
            playerStageMobCounters.put(playerName, new StageMobKillingCounter(player, stage));
        }

        MsgUtil.sendTitle(player, "&c开始作战");
    }

    //事件_关卡死亡重生
    @EventHandler
    public void onStagePlayerRespawnEvent(StagePlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Stage stage = event.getStage();

        Bukkit.getScheduler().runTask(plugin, () -> MsgUtil.sendRawMsg(player, "[\"\",{\"text\":\"" + MsgUtil.HEAD_MSG + "§e" + "『点我返回关卡』\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/crs stage tp " + stage.getStageCode() + "\"}}]"));
    }

    //_含触发事件_StagePlayerDeathEvent
    @EventHandler
    public void onTriggerPlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (!StageManager.isStageWorld(player.getWorld().getName())) {
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> Util.teleportToServerSpawnPoint(player));

        if (playerStageMobCounters.containsKey(playerName)) {
            Bukkit.getPluginManager().callEvent(new StagePlayerRespawnEvent(player, playerStageMobCounters.get(playerName).getStage()));
        }
    }

    //_含触发事件_StageMobKilledByPlayerEvent
    @EventHandler
    public void onTriggerEntityDeathEvent(EntityDeathEvent event) {
        LivingEntity target = event.getEntity();
        Entity killer = target.getKiller();

        if (killer == null) {
            return;
        }

        Player player = (Player) killer;

        if (mythicMobApi.isMythicMob(target) && StageManager.isStageWorld(player.getWorld().getName())) {
            //触发专用事件
            Bukkit.getPluginManager().callEvent(new RpgMobKilledByPlayerEvent(player, mythicMobApi.getMythicMobInstance(target).getType()));
        }
    }

    //_含触发事件_StageLeaveEvent
    @EventHandler
    public void onTriggerPlayerChangeWorldEvent(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (StageManager.isStageWorld(event.getFrom().getName()) && playerStageMobCounters.containsKey(playerName)) {
            Bukkit.getPluginManager().callEvent(new StageLeaveEvent(player, playerStageMobCounters.get(playerName).getStage()));
        }
    }
}
