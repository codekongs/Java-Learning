import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import util.DataProcUtil;
import util.FileUtil;

/**
 * 运行主类
 */

public class Main {

    //a,b,c类文件存储路径
    public static final String A_DATA_FILE_PATH = "./data/a";
    public static final String B_DATA_FILE_PATH = "./data/b";
    public static final String C_DATA_FILE_PATH = "./data/c";
    public static final String RES_DATA_FILE_PATH = "./res";

    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
        //用户输入的日期
        String dateStr = null;

        System.out.println("请输入日期:");

        dateStr = input.nextLine().trim();

        System.out.println("开始文件处理...");

        List<LinkedHashMap<String, Double>> listMapa = DataProcUtil.loadData(dateStr, A_DATA_FILE_PATH, "a");
        List<LinkedHashMap<String, Double>> listMapb = DataProcUtil.loadData(dateStr, B_DATA_FILE_PATH, "b");
        List<LinkedHashMap<String, Double>> listMapc = DataProcUtil.loadData(dateStr, C_DATA_FILE_PATH, "c");

        LinkedHashMap<String, Double> list = DataProcUtil.buildData(listMapa, listMapb, listMapc);
        FileUtil.writeDataFile(RES_DATA_FILE_PATH, dateStr + ".txt", list);

        System.out.println("文件处理完成,结果文件存储在: " + RES_DATA_FILE_PATH + File.separator + dateStr + ".txt");
    }
}
