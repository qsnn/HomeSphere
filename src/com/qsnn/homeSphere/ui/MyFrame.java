package com.qsnn.homeSphere.ui;

import com.qsnn.homeSphere.HomeSphereSystem;
import com.qsnn.homeSphere.domain.automationScene.AutomationScene;
import com.qsnn.homeSphere.utils.formatter.HtmlRunningLogFormatter;
import com.qsnn.homeSphere.utils.formatter.JsonRunningLogFormatter;
import com.qsnn.homeSphere.utils.formatter.XmlRunningLogFormatter;
import com.qsnn.homeSphere.utils.initialer.Initialer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class MyFrame extends JFrame implements ActionListener {

    //系统
    private HomeSphereSystem system = null;

    //加载本地文件
    JButton loadButton = new JButton("加载本地文件");

    //选择智能场景
    JPanel scenePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JComboBox<String> sceneComboBox;

    //触发按钮
    JButton actionButton = new JButton("触发");

    //三种格式设备运行日志按钮
    JButton JsonButton = new JButton("JSON格式设备运行日志");
    JButton HTMLButton = new JButton("HTML格式设备运行日志");
    JButton XMLButton = new JButton("XML格式设备运行日志");

    //输出区
    JTextArea logTextArea = new JTextArea();

    public MyFrame() {
        //初始化窗口
        initFrame();

        //初始化界面
        initPage();


        SwingUtilities.invokeLater(() -> {
            this.revalidate();
            this.repaint();
            this.setVisible(true);
        });

    }

    private void initSystem() {
        try(BufferedReader br = new BufferedReader(new FileReader("source/household.dat"))) {
            String s;
            while ((s = br.readLine()) != null) {
                Initialer.initialLine(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        system = HomeSphereSystem.getInstance();

    }

    private void initFrame() {
        //设置窗口大小
        this.setSize(900, 600);
        //设置窗口置顶
        this.setAlwaysOnTop(false);
        //设置窗口标题
        this.setTitle(HomeSphereSystem.SYSTEM_NAME);
        //设置窗口居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //写3也可以
        //取消默认的居中位置
        this.setLayout(null);
        //将窗口设置为可见使其显现
        this.setVisible(true);
    }

    private void initPage() {
        //加载本地文件按钮
        loadButton.setBounds(50, 30, 150, 40);
        loadButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        loadButton.addActionListener(this);
        this.add(loadButton);

        sceneComboBox = new JComboBox<>();
        sceneComboBox.setFont(new Font("楷体", Font.PLAIN, 16));

        JLabel sceneStr = new JLabel("选择智能场景:");
        sceneStr.setFont(new Font("宋体", Font.PLAIN, 16));

        scenePanel.add(sceneStr);
        scenePanel.add(sceneComboBox);
        scenePanel.setBounds(250, 30, 400, 40);
        this.add(scenePanel);

        //触发按钮
        actionButton.setBounds(500, 30, 100, 40);
        actionButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        this.add(actionButton);
        actionButton.addActionListener(this);

        //三种格式设备运行日志按钮
        JsonButton.setBounds(100, 100, 210, 50);
        HTMLButton.setBounds(350, 100, 220, 50);
        XMLButton.setBounds(600, 100, 200, 50);

        JsonButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        HTMLButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        XMLButton.setFont(new Font("微软雅黑", Font.BOLD, 16));

        this.add(JsonButton);
        this.add(HTMLButton);
        this.add(XMLButton);

        JsonButton.addActionListener(this);
        HTMLButton.addActionListener(this);
        XMLButton.addActionListener(this);


        // 创建文本区域
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        // 创建滚动面板
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        scrollPane.setBounds(50, 200, 800, 350);
        this.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == loadButton) {
            initSystem();
            for (AutomationScene scene : system.getHousehold().getAutoScenes().values()) {
                sceneComboBox.addItem(scene.getSceneId() + "_" + scene.getName());
            }
        }
        if (system != null) {
            if (obj == actionButton) {
                system.runScene(Integer.parseInt(sceneComboBox.getSelectedItem().toString().split("_")[0]));
            } else if (obj == JsonButton) {
                logTextArea.setText(new JsonRunningLogFormatter().format(system.getHousehold()));
            } else if (obj == HTMLButton) {
                logTextArea.setText(new HtmlRunningLogFormatter().format(system.getHousehold()));
            } else if (obj == XMLButton) {
                logTextArea.setText(new XmlRunningLogFormatter().format(system.getHousehold()));
            }
        }else {
            JOptionPane.showMessageDialog(null,
                    "请先加载数据！",
                    "警告",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
