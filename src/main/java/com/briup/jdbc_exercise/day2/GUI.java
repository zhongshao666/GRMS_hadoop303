package com.briup.jdbc_exercise.day2;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.JButton;
/*
 * 创建窗体
 * 创建中间容器
 * 内部嵌套两个登录标签
 * 网格布局两个标签及输入的文本
 * 边界布局两个按钮
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI extends JFrame {
    public GUI() {
        JTextField text1, text2;
        this.setTitle("登录");
        this.setLayout(new GridLayout(3, 1));
        this.setSize(300, 200);
        this.setLocation(550, 200);
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JButton b1 = new JButton("登录");
        JButton b2 = new JButton("退出");
        panel.add(b1);
        panel.add(b2);
        Label lable1 = new Label("name:");
        Label lable2 = new Label("password:");
        text1 = new JTextField(13);
        text2 = new JPasswordField(14);
        panel1.add(lable1);
        panel1.add(text1);
        panel2.add(lable2);
        panel2.add(text2);
        this.add(panel1);
        this.add(panel2);
        this.add(panel, BorderLayout.SOUTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

