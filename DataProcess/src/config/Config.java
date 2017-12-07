package config;

import java.text.SimpleDateFormat;

/**
 * 配置类,配置一些可能会变的参数
 */

public class Config {
    //存储数据的文件夹名称
    public static final String DATA_FOLDER_NAME = "data";
    //第一个数据文件夹的名字
    public static final String FIRST_FOLDER_NAME = "a";
    //第二个数据文件夹的名字
    public static final String SECOND_FOLDER_NAME = "b";
    //第三个数据文件夹的名字
    public static final String THIRD_FOLDER_NAME = "b";

    //数据结果保存文件夹名字
    public static final String DATA_RES_FOLDER_NAME = "res";

    //数据之间的分隔符,默认为空格
    public static final String DATA_SEPARATOR_NAME = "\t";

    //系统换行符
    public static final String SYSTEM_LINE_SEPARATOR = System.getProperty("line.separator");

    //文件名称日期格式
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHH");

    //数据文件的表头
    public static final String DATA_FILE_TABLE_HEAD = "OBTID\tLONGITUDE\tLATITUDE\tdata";

    //b类和c类一次处理的数据项的数目(默认为11)
    public static final int DATA_ITEM_NUM = 11;

    //数据文件的后缀
    public static final String DATA_FILE_SUFFIX = ".txt";


}
