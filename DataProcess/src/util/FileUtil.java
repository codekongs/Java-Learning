package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import config.Config;

/**
 * 文件相关处理的工具类
 */

public class FileUtil {

    /**
     * 将指定路径下的指定文件读取为一个LinkedHashMap
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @return
     */
    public static LinkedHashMap<String, Double> readDataFile(String filePath, String fileName) {
        if (filePath == null && fileName == null) {
            return null;
        }
        LinkedHashMap<String, Double> dataRes = new LinkedHashMap<>();
        File dataFile = new File(filePath, fileName);

        //文件不存在
        if (!dataFile.exists()) {
            return null;
        }

        BufferedReader br = null;
        FileReader fr = null;
        String line = "";
        String[] splitRes = null;
        try {
            fr = new FileReader(dataFile);
            br = new BufferedReader(fr);

            //跳过第一行的表头
            br.readLine();
            //逐行读取
            while ((line = br.readLine()) != null) {
                line = line.trim();
                //将前面部分和最后的data数据部分分隔开存储
                dataRes.put(line.substring(0, line.lastIndexOf(Config.DATA_SEPARATOR_NAME)).trim(),
                        Double.parseDouble(line.substring(line.lastIndexOf(Config.DATA_SEPARATOR_NAME) + 1).trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭文件读取
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fr != null) {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataRes;
    }


    /**
     * 将指定的数据写入到指定的文件中
     *
     * @param filePath 文件夹路径
     * @param fileName 文件夹名称
     * @param data     需要写入的数据
     */
    public static void writeDataFile(String filePath, String fileName, LinkedHashMap<String, Double> data) {
        if (filePath == null || fileName == null) {
            return;
        }
        File resFile = new File(filePath, fileName);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(resFile);
            bw = new BufferedWriter(fw);

            //先写一行表头
            bw.write(Config.DATA_FILE_TABLE_HEAD + Config.SYSTEM_LINE_SEPARATOR);
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                bw.write(entry.getKey() + Config.DATA_SEPARATOR_NAME + entry.getValue() + Config.SYSTEM_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fw != null) {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
