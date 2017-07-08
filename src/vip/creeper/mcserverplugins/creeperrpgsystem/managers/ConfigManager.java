package vip.creeper.mcserverplugins.creeperrpgsystem.managers;

import vip.creeper.mcserverplugins.creeperrpgsystem.Main;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.FileUtil;
import vip.creeper.mcserverplugins.creeperrpgsystem.utils.MsgUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by July_ on 2017/7/7.
 */
public class ConfigManager {
    private static final List<String> CONFIG_SUBFILE_NAME_LIST = Arrays.asList("PluginConfig.yml", "StageConfig.yml");
    private static Main plugin = Main.getInstance();


    public static void loadAllConfig() {
        File configDataFolder = new File(FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "configs");
        if (!configDataFolder.exists()) {
            configDataFolder.mkdirs();
            MsgUtil.info("文件(夹) " + configDataFolder.getAbsolutePath() + " 被创建.");
        }
        //拷贝资源
        for (int i = 0; i < CONFIG_SUBFILE_NAME_LIST.size(); i++) {
            String configFileName = CONFIG_SUBFILE_NAME_LIST.get(i);
            File file = new File(FileUtil.PLUGINDATA_FOLDER_PATH  + File.separator + "configs" + File.separator + configFileName);
            if (!file.exists()) {
                FileUtil.copySrcFile(CONFIG_SUBFILE_NAME_LIST.get(i), FileUtil.PLUGINDATA_FOLDER_PATH + File.separator + "configs" + File.separator + configFileName);
                MsgUtil.info("文件(夹) " + file.getAbsolutePath() + " 被创建.");
            }
        }
        plugin.getPluginConfig().loadConfig();
        plugin.getStageConfig().loadConfig();
    }

}
