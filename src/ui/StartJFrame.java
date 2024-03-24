package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class StartJFrame extends JFrame implements ActionListener {
    // 定义按钮
    private final JButton BOTTOM = new JButton();
    public StartJFrame(){
        initStartJFrame();
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

        ImageIcon icon = new ImageIcon("data/img/girl/kit.jpg");
        JLabel jLabel = new JLabel(icon);
        jLabel.setBounds(87,90,174,174);
        getContentPane().add(jLabel);

        BOTTOM.setText("Go");
        BOTTOM.setBounds(135,280,80,30);
        getContentPane().add(BOTTOM);

        // 刷新窗口
        getContentPane().repaint();
    }

    /**
     * 初始化窗口
     */
    private void initStartJFrame() {
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
        // 点击按钮后打开设置界面, 关闭本界面
        if (Objects.equals(source, BOTTOM)) {
            SettingJFrame settingJFrame = new SettingJFrame();
            dispose();
        }
    }
}
