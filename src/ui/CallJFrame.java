package ui;

import dom.Person;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CallJFrame extends JFrame implements ActionListener {
    // 按钮
    private JButton BOTTOM;
    private String bottomText;
    // 图片
    private final String PATH = "data/img/girl/";
    private ImageIcon icon;
    // 抽取次数
    private int count;
    // 作弊
    private int times;
    private String ordName;

    // 倒计时
    private double theTime;
    // 用来定时刷新倒计时的定时器
    private Timer timer;
    // 随机抽取的时间间隔(毫秒)
    private final int TIME_INTERVAL = 200;
    // 抽中者名字
    private String theName;
    // 名字与图片对应保存
    private ArrayList<Person> persons;
    // 可随机到的学生
    private ArrayList<Person> randomPersons;
    // 可随机的学生的概率分布(线段截取法)
    private double[] randomArr;

    public CallJFrame() {
        // 默认作弊数据为空值
        this(new String[]{null, null, null});
    }

    public CallJFrame(String[] data) {
        initCallJFrame();
        initData(data);
        initImage();
        setVisible(true);
    }

    /**
     * 初始化数据
     */
    private void initData(String[] data) {
        // 按钮初始化
        BOTTOM = new JButton();
        BOTTOM.addActionListener(this);
        bottomText = "开始";
        // 获取作弊信息
        // 作弊变量
        String[] notName;
        if (data[0] == null) notName = null;
        else notName = data[0].split("[,，]");
        if (data[1] == null || data[1].isEmpty()) times = 0;
        else times = Integer.parseInt(data[1]);
        ordName = data[2];
        // 记录随机次数
        count = 0;
        // 初始化图片姓名
        icon = new ImageIcon(PATH + "logo.jpg");
        theName = "XXX";
        // 读取所有学生姓名
        // 读取的所有名字
        ArrayList<String> nameList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/StudentNames.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                nameList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        persons = new ArrayList<>();
        randomPersons = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            // 姓名和图片对应保存
            Person newPerson = new Person(nameList.get(i), i + 1, 1);
            persons.add(newPerson);
            // 记录可随机的人
            boolean sign = true;
            if (notName != null) for (String notStu : notName) {
                if (nameList.get(i).equals(notStu)) {
                    sign = false;
                    break;
                }
            }
            if (sign) randomPersons.add(newPerson);
        }
        // 初始化概率数组
        randomArr = new double[randomPersons.size()];
        changeRandom();
    }

    /**
     * 展示窗口内图形组件
     */
    private void initImage() {
        // 清空已加入的组件
        getContentPane().removeAll();

        JLabel jLabel = new JLabel("倒计时: ");
        jLabel.setFont(new Font("黑体", Font.BOLD, 36));
        jLabel.setBounds(50, 30, 200, 50);
        getContentPane().add(jLabel);
        // 设置倒计时的秒数
        int time = (int) Math.ceil(theTime);
        jLabel = new JLabel(time == 0 ? "---" : time + " 秒");
        jLabel.setFont(new Font("黑体", Font.BOLD, 36));
        jLabel.setBounds(220, 30, 200, 50);
        getContentPane().add(jLabel);

        jLabel = new JLabel("中奖选手: ");
        jLabel.setFont(new Font("黑体", Font.BOLD, 20));
        jLabel.setBounds(60, 100, 200, 50);
        getContentPane().add(jLabel);
        jLabel = new JLabel(theName);
        jLabel.setFont(new Font("黑体", Font.BOLD, 20));
        jLabel.setBounds(180, 100, 200, 50);
        getContentPane().add(jLabel);

        jLabel = new JLabel(icon);
        jLabel.setBounds(26, 150, 291, 210);
        getContentPane().add(jLabel);

        BOTTOM.setText(bottomText);
        BOTTOM.setBounds(120, 380, 100, 50);
        getContentPane().add(BOTTOM);

        // 刷新窗口
        getContentPane().repaint();
    }

    /**
     * 初始化窗口
     */
    private void initCallJFrame() {
        setSize(360, 500);
        setTitle("点名器 v1.0");

        // 界面居中
        setLocationRelativeTo(null);
        // 关闭模式
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 关闭组件默认居中
        setLayout(null);
    }

    /**
     * 点击确定后执行事件
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (Objects.equals(source, BOTTOM)) {
            // 抽取学生期间不可点击
            BOTTOM.setEnabled(false);
            bottomText = "随机抽取中";
            initImage();
            count++;

            System.out.println("randomNameList: " + randomPersons);
            System.out.println("times: " + times + "     ordName: " + ordName);

            theTime = 5.0;
            // 定时器每隔一段时间触发随机
            timer = new Timer(TIME_INTERVAL, e1 -> {
                // 随机选择学生
                double r = Math.random();   // 0 ~ 1之间的随机数
                int index = Arrays.binarySearch(randomArr, r);   // 二分查找获取 下标 或 -下标-1
                if (index < 0) index = -index - 2;  // 转化为可抽取学生列表的下标
                changeStudent(index);
                // 修改倒计时剩余时间
                theTime -= TIME_INTERVAL / 1000.0;

                // 倒计时结束
                if (theTime <= 0) {
                    // 判断是否必中指定学生
                    if (count == times) {
                        for (int i = 0; i < randomPersons.size(); i++) {
                            if (randomPersons.get(i).getName().equals(ordName)) index = i;
                        }
                    } else {
                        // 未到必中指定学生时, 被抽中者权重降低一半
                        Person thePerson = randomPersons.get(index);
                        thePerson.setWeight(thePerson.getWeight() / 2);
                        changeRandom();
                    }
                    changeStudent(index);
                    // 恢复抽取按钮
                    BOTTOM.setEnabled(true);
                    bottomText = "开始";
                    initImage();
                    // 停止随机
                    timer.stop();

                    System.out.println();
                    System.out.println("第" + count + "次随机: " + theName);
                }
            });
            // 启动定时器
            timer.start();
        }
    }

    private void changeStudent(int index) {
        Person thePerson = randomPersons.get(index);
        theName = thePerson.getName();
        icon = new ImageIcon(PATH + thePerson.getImgID() + ".jpg");
        System.out.print(theName + " ");
        // 刷新窗口
        initImage();
    }


    /**
     * 根据权重修改概率数组中的概率分布
     */
    private void changeRandom() {
        double allWeight = 0;
        for (Person randomPerson : randomPersons) {
            allWeight += randomPerson.getWeight();
        }

        System.out.println();
        System.out.print("当前概率分布: ");
        for (int i = 0; i < randomPersons.size(); i++) {
            if (i == 0) randomArr[i] = 0.0;
            else {
                randomArr[i] = randomPersons.get(i - 1).getWeight() / allWeight + randomArr[i - 1];
            }
            System.out.print((i + 1) + " - " + String.format("%.3f", randomArr[i]) + " ");
        }
        System.out.println();
    }

}
