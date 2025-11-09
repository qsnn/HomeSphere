package com.qsnn.homeSphere.ui;

import com.qsnn.homeSphere.HomeSphereSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {

    //加载本地文件
    JButton loadButton = new JButton("加载本地文件");

    //选择智能场景
    JPanel panel = new JPanel(new BorderLayout());

    // 左侧文字标签
    JLabel label = new JLabel("选择智能场景");

    //触发按钮
    JButton actionButton = new JButton("触发");

    //三种格式设备运行日志按钮
    JButton[] logButtons = new JButton[3];



    public MyFrame() {
        //初始化窗口
        initFrame();

        //初始化界面
        initPage();




        //将窗口设置为可见使其显现
        this.setVisible(true);
    }

    private void initFrame() {
        //设置窗口大小
        this.setSize(900, 600);
        //设置窗口置顶
        this.setAlwaysOnTop(true);
        //设置窗口标题
        this.setTitle(HomeSphereSystem.SYSTEM_NAME);
        //设置窗口居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //写3也可以
        //取消默认的居中位置
        this.setLayout(null);
    }

    private void initPage() {
        //加载本地文件按钮
        loadButton.setBounds(50, 50, 150, 50);
        this.add(loadButton);

        //选择智能场景面板
        panel.setBounds(250, 50, 400, 300);
        panel.setBorder(BorderFactory.createTitledBorder("选择智能场景"));
        this.add(panel);

        //左侧文字标签
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        //触发按钮
        actionButton.setBounds(700, 50, 100, 50);
        this.add(actionButton);

        //三种格式设备运行日志按钮
        JButton JsonButton = new JButton("JSON格式设备运行日志");
        JButton HTMLButton = new JButton("HTML格式设备运行日志");
        JButton XMLButton = new JButton("XML格式设备运行日志");

        JsonButton.setBounds(50, 400, 200, 50);
        HTMLButton.setBounds(300, 400, 200, 50);
        XMLButton.setBounds(550, 400, 200, 50);

        this.add(JsonButton);
        this.add(HTMLButton);
        this.add(XMLButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
