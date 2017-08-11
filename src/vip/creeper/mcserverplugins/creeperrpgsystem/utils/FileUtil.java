package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import vip.creeper.mcserverplugins.creeperrpgsystem.CreeperRpgSystem;

import java.io.*;

/**
 * Created by July_ on 2017/7/5.
 */
public class FileUtil {
    private static CreeperRpgSystem plugin = CreeperRpgSystem.getInstance();
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String PLUGIN_DATA_FOLDER_PATH = plugin.getDataFolder().getAbsolutePath();

    //从jar包复制文件
    public static boolean copySrcFile(final String srcFilePath, final String localFilePath) {
        try {
            InputStream is = plugin.getClass().getClassLoader().getResourceAsStream(srcFilePath); //读取文件内容
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            StringBuilder sb = new StringBuilder();

            while ((lineText = bufferedReader.readLine()) != null){
                sb.append(lineText).append(LINE_SEPARATOR);
            }

            bufferedReader.close();
            reader.close();
            return writeFile(localFilePath, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //写文件
    private static boolean writeFile(final String path, final String data) {
        File file = new File(path);

        try {
            FileWriter fw = new FileWriter(file);

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
            }

            fw.write(data);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //得到玩家数据file
    public static File getPlayerDataFile(final String player) {
        return new File(FileUtil.PLUGIN_DATA_FOLDER_PATH + File.separator + "playerdata" + File.separator + player + ".yml");
    }
}
