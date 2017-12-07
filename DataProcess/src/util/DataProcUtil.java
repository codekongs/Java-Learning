package util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import config.Config;

/**
 * 数据处理工具类
 */

public class DataProcUtil {

    /**
     * 根据输入的日期字符串从文件中载入数据(载入到内存中)
     *
     * @param dateStr    日期字符串
     * @param filePath   文件路径
     * @param filePrefix 文件前缀(a,b,c)
     * @return 读取到的数据
     */
    public static List<LinkedHashMap<String, Double>> loadData(String dateStr, String filePath, String filePrefix) {
        if (dateStr == null || "".equals(dateStr) || filePath == null) {
            return null;
        }

        File fileDir = new File(filePath);

        //所给路径不存在或者不是个目录
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            return null;
        }

        //需要读取的文件的数目(a类文件10个,b和c类文件11个)
        int fileNum = 0;
        if ("a".equals(filePrefix)) {
            fileNum = Config.DATA_ITEM_NUM - 1;
        } else if ("b".equals(filePrefix) || "c".equals(filePrefix)) {
            fileNum = Config.DATA_ITEM_NUM;
        }

        //存储一类文件的数据
        List<LinkedHashMap<String, Double>> dataSet = new ArrayList<>();
        String fileName = "";
        for (int i = 0; i < fileNum; i++) {
            fileName = filePrefix + DateUtil.getBeforeNDays(dateStr, Config.DATA_ITEM_NUM - i - 1) + Config.DATA_FILE_SUFFIX;
            LinkedHashMap<String, Double> map = FileUtil.readDataFile(filePath, fileName);
            dataSet.add(map);
        }
        return dataSet;
    }


    /**
     * 构建数据,计算载入内存中的数据,算出最终结果
     * @param listMapa
     * @param listMapb
     * @param listMapc
     * @return
     */
    public static LinkedHashMap<String, Double> buildData(List<LinkedHashMap<String, Double>> listMapa,
                                                          List<LinkedHashMap<String, Double>> listMapb,
                                                          List<LinkedHashMap<String, Double>> listMapc) {
        if (listMapa == null || listMapb == null || listMapc == null){
            return null;
        }
        List<List<Double>> lista = getDoubleData(listMapa);
        List<List<Double>> listb = getDoubleData(listMapb);
        List<List<Double>> listc = getDoubleData(listMapc);

        //将矩阵进行转置
        List<List<Double>> transLista = transposeMatrix(lista);
        List<List<Double>> transListb = transposeMatrix(listb);
        List<List<Double>> transListc = transposeMatrix(listc);

        //最终计算得到的data
        List<Double> calcResList = new ArrayList<>();

        //对每一行数据进行处理
        for (int i = 0; i < transLista.size(); i++) {
            calcResList.add(DataProcUtil.calcResult(transLista.get(i), transListb.get(i), transListc.get(i)));
        }

        //组装数据
        LinkedHashMap<String, Double> dataRes = new LinkedHashMap<>();

        int index = 0;
        //取第一个文件的格式
        LinkedHashMap<String, Double> aMap = listMapa.get(0);
        for (Map.Entry<String, Double> entry : aMap.entrySet()){
            dataRes.put(entry.getKey(), calcResList.get(index));
            index++;
        }
        return dataRes;
    }

    /**
     * 从数据中提取出data数据项
     * @param listMap
     * @return
     */
    public static List<List<Double>> getDoubleData(List<LinkedHashMap<String, Double>> listMap){
        List<List<Double>> listRes = new ArrayList<>();
        for (LinkedHashMap<String, Double> dataMap : listMap){
            List<Double> list = new ArrayList<>();
            for (Map.Entry<String, Double> entry : dataMap.entrySet()){
                list.add(entry.getValue());
            }
            listRes.add(list);
        }
        return listRes;
    }


    /**
     * 根据a,b,c从32个文件中获取到的数据按公式进行计算
     * 计算公式:X = avg(a1...a10) + [(b11 - avg(b1...b10)) + (c11 - avg(a1...a10)] * 0.5
     *
     * @param lista 存储某一个obtid的a类文件的data参数
     * @param listb 存储某一个obtid的b类文件的data参数
     * @param listc 存储某一个obtid的c类文件的data参数
     * @return 计算结果
     */
    public static Double calcResult(List<Double> lista, List<Double> listb, List<Double> listc) {
        if (listb.size() != Config.DATA_ITEM_NUM || listc.size() != Config.DATA_ITEM_NUM) {
            return 0.0;
        }
        //计算结果
        return avg(lista, 0, lista.size() - 1)
                + (listb.get(Config.DATA_ITEM_NUM - 1) - avg(listb, 0, listb.size() - 2)
                + listc.get(Config.DATA_ITEM_NUM - 1) - avg(listc, 0, listc.size() - 2)) * 0.5;
    }


    /**
     * 计算一个double数组中一段数据[start, end]的平均值
     *
     * @param list  需要计算的double数组
     * @param start 开始下标
     * @param end   结束下标
     * @return 平均值
     */
    private static Double avg(List<Double> list, int start, int end) {
        if (list == null || list.size() == 0) {
            return 0.0;
        }

        if (start < 0 || end < 0 || start > end) {
            return 0.0;
        }

        if (start >= list.size() || end >= list.size()) {
            return 0.0;
        }

        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += list.get(i);
        }
        return sum / (end - start + 1);
    }

    /**
     * 转置一个矩阵
     * @param lists
     * @return
     */
    private static List<List<Double>> transposeMatrix(List<List<Double>> lists){
        List<List<Double>> resList = new ArrayList<>();

        for (int i = 0; i < lists.get(0).size(); i++) {
            List<Double> list = new ArrayList<>();
            for (int j = 0; j < lists.size(); j++){
                list.add(lists.get(j).get(i));
            }
            resList.add(list);
        }
        return resList;
    }
}
