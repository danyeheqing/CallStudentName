package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SettingJFrame extends JFrame implements ActionListener {
    // 定义按钮
    private final JButton BOTTOM = new JButton();
    // 定义输入框
    private final JTextField notNameText = new JTextField();
    private final JTextField timesText = new JTextField();
    private final JTextField ordNameText = new JTextField();

    public SettingJFrame() {
        initSettingJFrame();
        initData();
        initImage();
        setVisible(true);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        BOTTOM.addActionListener(this);
    }


    /**
     * 展示窗口内图形组件
     */
    private void initImage() {
        // 清空已加入的组件
        getContentPane().removeAll();

        JLabel jLabel = new JLabel("作弊时刻");
        // 设置字体为:  华文行楷, 粗体, 36号
        jLabel.setFont(new Font("华文行楷", Font.BOLD, 36));
        jLabel.setBounds(90, 50, 150, 60);
        getContentPane().add(jLabel);

        jLabel = new JLabel("不会被点的人: ");
        jLabel.setFont(new Font("楷体", Font.BOLD, 20));
        jLabel.setBounds(20, 150, 150, 30);
        getContentPane().add(jLabel);
        notNameText.setBounds(160, 150, 150, 30);
        getContentPane().add(notNameText);

        jLabel = new JLabel("第");
        jLabel.setFont(new Font("楷体", Font.BOLD, 20));
        jLabel.setBounds(20, 220, 30, 30);
        getContentPane().add(jLabel);
        timesText.setBounds(40, 220, 30, 30);
        getContentPane().add(timesText);
        jLabel = new JLabel("次, 必定点到: ");
        jLabel.setFont(new Font("楷体", Font.BOLD, 20));
        jLabel.setBounds(70, 220, 200, 30);
        getContentPane().add(jLabel);
        ordNameText.setBounds(210, 220, 100, 30);
        getContentPane().add(ordNameText);

        BOTTOM.setText("确定");
        BOTTOM.setBounds(135, 300, 80, 30);
        getContentPane().add(BOTTOM);

        // 刷新窗口
        getContentPane().repaint();
    }

    /**
     * 初始化窗口
     */
    private void initSettingJFrame() {
        setSize(360, 500);
        setTitle("点名器 v1.0");

        // 界面居中
        setLocationRelativeTo(null);
        // 关闭模式
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 关闭组件默认居中
        setLayout(null);
    }

    // 点击事件
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        // 点击按钮后校验信息, 打开点名界面
        if (Objects.equals(source, BOTTOM)) {
            String notName = notNameText.getText();
            String times = timesText.getText();
            String ordName = ordNameText.getText();
            if (judgeData(notName, times, ordName)) {
                // 传出数据
                String[] data = {notName,times,ordName};
                CallJFrame callJFrame = new CallJFrame(data);
                dispose();
            } else {
                // 弹窗提示输入格式不对
                dumpDialog();
            }
        }
    }

    /**
     * 弹窗提示输入错误
     */
    private static void dumpDialog() {
        JDialog jDialog = new JDialog();
        jDialog.setSize(220, 230);
        jDialog.setTitle("输入提示");
        jDialog.setLocationRelativeTo(null);
        jDialog.setLayout(null);

        JLabel jLabel = new JLabel("输入格式错误");
        // 修改文本颜色
        jLabel.setForeground(Color.red);
        jLabel.setBounds(10, 10, 100, 30);
        jDialog.getContentPane().add(jLabel);
        jLabel = new JLabel("不被点到的人名用逗号隔开");
        jLabel.setBounds(10, 40, 150, 30);
        jDialog.getContentPane().add(jLabel);
        jLabel = new JLabel("次数只能是正整数");
        jLabel.setBounds(10, 70, 150, 30);
        jDialog.getContentPane().add(jLabel);
        jLabel = new JLabel("特别点中的人名只能有一个,");
        jLabel.setBounds(10, 100, 180, 30);
        jDialog.getContentPane().add(jLabel);
        jLabel = new JLabel("不能有逗号");
        jLabel.setBounds(10, 130, 180, 30);
        jDialog.getContentPane().add(jLabel);
        jLabel = new JLabel("次数和特别点人不能单独为空");
        jLabel.setBounds(10, 160, 180, 30);
        jDialog.getContentPane().add(jLabel);

        jDialog.setVisible(true);
    }

    /**
     * 判断输入参数是否符合条件
     * @param notName 不抽取的名字
     * @param times 特殊抽取为第几次
     * @param ordName   特殊抽取的名字
     * @return boolean
     */
    private boolean judgeData(String notName, String times, String ordName) {

        // 允许输入为空
        // 匹配名字, 可用中英文逗号隔开
        if (!notName.matches("(.+([,，]))*(.+)?")) return false;
        if (!times.matches("[1-9]?[0-9]*")) return false;
        // 匹配一个名字
        if (!ordName.matches("[^,，]*")) {
            System.out.println("32");
            return false;
        }
        // 次数和名字同为空或都有数据才返回true
        return times.isEmpty() == ordName.isEmpty();
    }
}
