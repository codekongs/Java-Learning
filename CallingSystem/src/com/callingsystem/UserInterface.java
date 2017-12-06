package com.callingsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * 用户界面类
 */

public class UserInterface {

    //窗口宽度和高度
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 500;

    //Label的宽度和高度
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 50;

    //左边偏移
    private static final int LEFT_MARGIN = (WINDOW_WIDTH - LABEL_WIDTH * 3) / 4;
    //顶部偏移
    private static final int TOP_MARGIN = LABEL_HEIGHT;
    //当前正在被服务的人
    private JButton[] servicedPeopleBtnArray;
    //休息区椅子数组
    private JButton[] restAreaChairBtnArray;

    //站着等待区的人
    private List<JLabel> mStandUpPeopleList;

    private JPanel mJPanel;

    //进入的人显示
    private JLabel enterFlagLabel;

    //目前站着的人数
    private JLabel standOnNumLabel;
    /**
     * 初始化界面
     */
    public UserInterface() {
        servicedPeopleBtnArray = new JButton[3];
        restAreaChairBtnArray = new JButton[10];
        mStandUpPeopleList = new ArrayList<>();

        // 创建JFrame
        JFrame frame = new JFrame("银行叫号系统");
        // 设置尺寸
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel jPanel = new JPanel();

        //将panel暴露出去,便于添加空闲的没有座位的人
        mJPanel = jPanel;

        enterFlagLabel = new JLabel();
        enterFlagLabel.setBounds(WINDOW_WIDTH / 2, 20, LABEL_WIDTH, LABEL_HEIGHT);
        standOnNumLabel = new JLabel();
        standOnNumLabel.setBounds(WINDOW_WIDTH / 2 + LABEL_WIDTH * 2, 20, LABEL_WIDTH, LABEL_HEIGHT);
        jPanel.add(enterFlagLabel);
        jPanel.add(standOnNumLabel);

        jPanel.setLayout(null);
        frame.add(jPanel);
        for (int i = 0; i < 3; i++) {
            JLabel jLabel = new JLabel("服务窗口 " + (i + 1), JLabel.CENTER);
            jLabel.setBounds(LEFT_MARGIN + i * 200, TOP_MARGIN, LABEL_WIDTH, LABEL_HEIGHT);
            jPanel.add(jLabel);

            JButton jButton = new JButton();
            jButton.setBounds(LEFT_MARGIN + i * 200, TOP_MARGIN * 2, LABEL_WIDTH, LABEL_HEIGHT);
            jPanel.add(jButton);
            servicedPeopleBtnArray[i] = jButton;
        }

        JLabel restAreaLabel = new JLabel("休息区", JLabel.CENTER);
        restAreaLabel.setBounds(0, TOP_MARGIN * 4, WINDOW_WIDTH, 50);
        jPanel.add(restAreaLabel);

        int restAreaChairWidth = WINDOW_WIDTH / 11;

        //产生第一排座位
        for (int i = 0; i < 5; i++) {
            JButton jButton = new JButton();
            jButton.setBounds(restAreaChairWidth + i * restAreaChairWidth * 2, TOP_MARGIN * 5,
                    restAreaChairWidth / 3 * 2, restAreaChairWidth / 3 * 2);
            jPanel.add(jButton);
            restAreaChairBtnArray[i] = jButton;
        }

        //产生第二排座位
        for (int i = 0; i < 5; i++) {
            JButton jButton = new JButton();
            jButton.setBounds(restAreaChairWidth + i * restAreaChairWidth * 2, TOP_MARGIN * 7,
                    restAreaChairWidth / 3 * 2, restAreaChairWidth / 3 * 2);
            jPanel.add(jButton);
            restAreaChairBtnArray[i + 5] = jButton;
        }

        // JFrame在屏幕居中
        frame.setLocationRelativeTo(null);
        // JFrame关闭时的操作
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 显示JFrame
        frame.setVisible(true);
    }


    /**
     * 更新被服务的用户的编号
     *
     * @param windowNum   窗口编号
     * @param servicedNum 被服务人员的编号
     */
    public synchronized void updateServicedPeopleNum(int windowNum, int servicedNum) {
        servicedPeopleBtnArray[windowNum].setText(servicedNum + "");
    }


    /**
     * 将指定编号的人员放在空闲的椅子上
     *
     * @param peopleNum 人员编号
     * @return res       如果人员可以被安排在空椅子上返回true,否则返回false
     */
    public synchronized boolean updateRestAraeChairNum(int peopleNum) {
        //当前这个人是不是已经在被服务了
        for (int i = 0; i < 3; i++) {
            if (servicedPeopleBtnArray[i].getText().equals(peopleNum + "")){
                return true;
            }
        }


        standOnNumLabel.setText("当前有 " + mStandUpPeopleList.size() + "人站着");
        System.out.println("站着的人有 = " + mStandUpPeopleList.size());
        boolean res = false;
        for (int i = 0; i < 10; i++) {
            if ("".equals(restAreaChairBtnArray[i].getText())) {
                System.out.println("为 " + peopleNum + "号顾客安排座位");

                //以前有人是站着的,优先让站着的人坐下
                if (mStandUpPeopleList.size() > 0){
                    //让站着的第一个人优先坐下
                    restAreaChairBtnArray[i].setText(mStandUpPeopleList.get(0).getText());

                    //将坐下的人
                    removePeopleNumFromPlace(Integer.parseInt(mStandUpPeopleList.get(0).getText()));

                    //让产生的人站着,目前还没有座位,将其放到站着的人最后等待
                    addNoChairPeople(peopleNum);
                    mStandUpPeopleList.add(mStandUpPeopleList.size() - 1, generatorRandomPosLabel(peopleNum));
                }else{
                    //目前座位是空的,没有人站着
                    restAreaChairBtnArray[i].setText(peopleNum + "");
                }
                res = true;
                break;
            }
        }
        return res;
    }


    //将站着等待区的人移除
    public void removePeopleNumFromPlace(int peopleNum) {
        System.out.println("将要移除 " + peopleNum);
        for (JLabel aStandUpPeop : mStandUpPeopleList) {
            if (aStandUpPeop.getText().equals(peopleNum + "")) {

                System.out.println("移除 " + peopleNum);
                mStandUpPeopleList.remove(aStandUpPeop);
                //移除元素并重绘
                aStandUpPeop.setVisible(false);
                mJPanel.remove(aStandUpPeop);
                mJPanel.revalidate();
                break;
            }
        }
    }

    /**
     * 从椅子上移除指定编号的人
     *
     * @param peopleNum
     */
    public void removeNumFromChair(int peopleNum) {
        for (int i = 0; i < 10; i++) {
            if (restAreaChairBtnArray[i].getText().equals(peopleNum + "")){
                restAreaChairBtnArray[i].setText("");
                break;
            }
        }
    }


    /**
     * 将没有椅子的人添加到最下面的区
     *
     * @param peopleNum
     */
    public synchronized void addNoChairPeople(int peopleNum) {
        JLabel jLabel = generatorRandomPosLabel(peopleNum);
        mJPanel.add(jLabel);
        mStandUpPeopleList.add(jLabel);
        //界面重绘
        mJPanel.validate();
        mJPanel.repaint();
    }

    //产生一个随机位置的Label(人)
    public JLabel generatorRandomPosLabel(int peopleNum){
        Random random = new Random();
        int x = random.nextInt(WINDOW_WIDTH) % (WINDOW_WIDTH + 1);
        int y = random.nextInt(WINDOW_HEIGHT) % (WINDOW_HEIGHT - TOP_MARGIN * 8 + 1) + TOP_MARGIN * 8;
        JLabel jLabel = new JLabel(peopleNum + "");
        jLabel.setBounds(x, y, LABEL_WIDTH, LABEL_HEIGHT);
        System.out.println(peopleNum + "号顾客站在 x: " + x + "y: " + y);
        return jLabel;
    }

    //
    public void setEnterText(int id) {
        enterFlagLabel.setText(id + " 号Enter");
    }
}
