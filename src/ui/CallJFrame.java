package ui;

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
    // 图片路径
    private final String PATH = "data/img/girl/";
    private ImageIcon icon;
    // 抽取次数
    private int count;
    // 作弊变量
    private String[] notName;
    private int times;
    private String ordName;

    // 倒计时变量
    private double theTime;
    // 用来定时刷新倒计时的定时器
    private Timer timer;
    // 倒数时随机刷新时间间隔(毫秒)
    private final int TIME_INTERVAL = 200;
    // 抽中者名字
    private String theName;
    // 读取的所有名字
    private ArrayList<String> nameList;
    // 名字与图片对应
    private Map<String, String> nameIconMap;
    // 可随机到的名字
    private ArrayList<String> randomNameList;

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
        if (data[0] == null) notName = null;
        else notName = data[0].split("[,，]");
        if(data[1] == null || data[1].isEmpty()) times = -1;
        else times = Integer.parseInt(data[1]);
        ordName = data[2];
        // 记录随机次数
        count = 0;
        // 初始化图片姓名
        icon = new ImageIcon(PATH + "logo.jpg");
        theName = "XXX";
        // 读取所有学生姓名
        nameList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/StudentNames.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                nameList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 姓名和图片对应
        nameIconMap = new HashMap<>();
        for (int i = 0; i < nameList.size(); i++) {
            nameIconMap.put(nameList.get(i), (i + 1) + ".jpg");
        }
        // 记录可随机的姓名
        randomNameList = new ArrayList<>();
        boolean sign;
        for (String stu : nameList) {
            sign = true;
            if(notName != null) for (String notStu : notName) {
                if (stu.equals(notStu)) {
                    sign = false;
                    break;
                }
            }
            if(sign) randomNameList.add(stu);
        }
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

            System.out.println("randomNameList: " + randomNameList);
            System.out.println("times: " + times + "     ordName: " + ordName);
            theTime = 5.0;
            // 定时器每隔一段时间触发随机
            timer = new Timer(TIME_INTERVAL, e1 -> {
                // 随机选择学生
                Random random = new Random();
                int index = random.nextInt(randomNameList.size());
                changeStudent(index);
                // 修改倒计时剩余时间
                theTime -= TIME_INTERVAL / 1000.0;

                // 停止定时器条件
                if (theTime <= 0) {
                    // 判断是否必中指定学生
                    if(count == times) {
                        for (int i = 0; i < randomNameList.size(); i++) {
                            if (randomNameList.get(i).equals(ordName)) index = i;
                        }
                    }
                    changeStudent(index);
                    // 恢复抽取按钮
                    BOTTOM.setEnabled(true);
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
        theName = randomNameList.get(index);
        icon = new ImageIcon(PATH + nameIconMap.get(theName));
        System.out.print(theName + " ");
        // 刷新窗口
        initImage();
    }
}
