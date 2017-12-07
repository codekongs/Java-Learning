package test;

import java.util.LinkedHashMap;
import java.util.List;

import util.DataProcUtil;

/**
 * 测试了类
 */

public class TestFunc {
    public static void main(String[] args) {
//        LinkedHashMap<String, Double> map = FileUtil.readDataFile("/home/szh/文档/Money/data/a", "a2016080103.txt");
//        for (Map.Entry<String, Double> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + "||"+ entry.getValue());
//            String line = "G1010\t113.225\t23.274\t3.032129862";
//        }
//
//        FileUtil.writeDataFile("/home/szh/文档/Money/res", "res.txt", map);
//        String dateStr = "2016081103";
//        for (int i = 0; i < 11; i++) {
//            System.out.println("a" + DateUtil.getBeforeNDays(dateStr, Config.DATA_ITEM_NUM - i - 1) + Config.DATA_FILE_SUFFIX);
//        }

        System.out.println(DataProcUtil.loadData("2016081103", "/home/szh/文档/Money/data/a", "a").size());
        List<LinkedHashMap<String, Double>> maps = DataProcUtil.loadData("2016081103", "/home/szh/文档/Money/data/a", "a");

    }
}
