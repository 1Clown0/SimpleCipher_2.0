package com.company.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.company.Coding;


/**
 * Created by Pavel on 28.02.2015.
 */
public class Form {
    public Form() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        panelStartInit();
        frame.add(panelStart);
    }


    private File file;
    private JFrame frame;
    private JPanel panelEnc;
    private JPanel panelDec;
    private JPanel panelStart;
    private JPanel panelChoose;

    private void panelStartInit() {
        panelStart = new JPanel();
        Button buttonStart = new Button("Выберите файл");
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Выберите файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file = fileopen.getSelectedFile();
                    panelChooseInit();
                    frame.remove(panelStart);
                    frame.add(panelChoose);
                    frame.validate();
                }
            }
        });
        panelStart.add(buttonStart);
    }

    private void panelChooseInit() {
        panelChoose = new JPanel();
        JLabel label = new JLabel(file.getName());
        Button buttonEnc = new Button("Зашивровать файл");
        Button buttonDec = new Button("Расшивровать файл");
        buttonEnc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelEncInit();
                frame.remove(panelChoose);
                frame.add(panelEnc);
                frame.validate();
            }
        });
        buttonDec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelDecInit();
                frame.remove(panelChoose);
                frame.add(panelDec);
                frame.validate();
            }
        });
        panelChoose.add(label);
        panelChoose.add(buttonEnc);
        panelChoose.add(buttonDec);

    }

    private void panelEncInit() {
        panelEnc = new JPanel();
        final JLabel label = new JLabel("Введите пароль для шифрования");
        final JPasswordField pass1 = new JPasswordField(10);
        pass1.setToolTipText("Введите пароль");
        final JPasswordField pass2 = new JPasswordField(10);
        pass2.setToolTipText("Повторите пароль");
        JButton start = new JButton("пуск");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Arrays.equals(pass1.getPassword(), pass2.getPassword())) {
                    label.setText("Good");
                    try {
                        Coding.encryption(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    label.setText("Bad");
            }
        });
        panelEnc.add(label);
        panelEnc.add(pass1);
        panelEnc.add(pass2);
        panelEnc.add(start);
    }

    private void panelDecInit() {
        panelDec = new JPanel();
        final JLabel label = new JLabel("Введите пароль");
        final JPasswordField pass1 = new JPasswordField(10);
        pass1.setToolTipText("Введите пароль");
        final JPasswordField pass2 = new JPasswordField(10);
        pass2.setToolTipText("Повторите пароль");
        JButton start = new JButton("пуск");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (equals1(pass1.getPassword(), pass2.getPassword())) {
                    label.setText("Good");
                    try {
                        Coding.decryption(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    label.setText("Bad");
            }
        });
        panelDec.add(label);
        panelDec.add(pass1);
        panelDec.add(pass2);
        panelDec.add(start);
    }

    public boolean equals1(char[] ch1, char[] ch2) {
        if (ch1.length != ch2.length)
            return false;
        for (int i = 0; i < ch1.length; i++)
            if (ch1[i] != ch2[i])
                return false;
        return true;
    }
}
