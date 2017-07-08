package vip.creeper.mcserverplugins.creeperrpgsystem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by July_ on 2017/7/7.
 */
public class Stage {
    private String stageCode;
    private HashMap<String, Integer> challenges;
    private List<String> confirmMsg;
    private List<String> finishCmd;

    public Stage(String stageCode, HashMap<String, Integer> challenges, List<String> confirmMsg, List<String> finishCmd) {
        this.stageCode = stageCode;
        this.challenges = challenges;
        this.confirmMsg = confirmMsg;
        this.finishCmd = finishCmd;
    }
    public String getStageCode() {
        return this.stageCode;
    }
    public HashMap<String, Integer> getChallenges() {
        return this.challenges;
    }
    public List<String> getConfirmMsg() {
        return this.confirmMsg;
    }
    public List<String> getFinishCmd() {
        return this.finishCmd;
    }
}
