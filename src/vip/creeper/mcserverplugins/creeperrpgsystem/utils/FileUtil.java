package vip.creeper.mcserverplugins.creeperrpgsystem.utils;

import vip.creeper.mcserverplugins.creeperrpgsystem.Main;

import java.io.*;

/**
 * Created by July_ on 2017/7/5.
 */
public class FileUtil {
    private static Main plugin = Main.getInstance();
    public static final String PLUGINDATA_FOLDER_PATH = plugin.getDataFolder().getAbsolutePath();

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
}
