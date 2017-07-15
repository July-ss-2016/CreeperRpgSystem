package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;

import java.io.*;

/**
 * Created by July_ on 2017/7/5.
 */
public class FileUtil {
    private static final CreeperRpgSystem plugin = CreeperRpgSystem.getInstance();
    public static final String PLUGIN_DATA_FOLDER_PATH = plugin.getDataFolder().getAbsolutePath();

    //从jar包复制文件
    public static boolean copySrcFile(String srcFilePath, String localFilePath) {
        try {
            InputStream is = plugin.getClass().getClassLoader().getResourceAsStream(srcFilePath); //读取文件内容
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            StringBuilder sb = new StringBuilder();

            while ((lineText = bufferedReader.readLine()) != null){
                sb.append(lineText + MsgUtil.LINE_SEPARATOR);
            }

            bufferedReader.close();
            reader.close();
            writeFile(localFilePath, sb.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //写文件
    public static boolean writeFile(String path, String data) {
        File file = new File(path);

        try {
            FileWriter fw = new FileWriter(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            fw.write(data);
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //得到玩家数据file
    public static File getPlayerDataFile(String player) {
        return new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "playerdata" + File.separator + player + ".yml");
    }
}
