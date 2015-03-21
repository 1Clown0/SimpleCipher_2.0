package com.company.GUI;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import com.company.Coding;


/**
 * Created by Pavel on 28.02.2015.
 */
public class Form implements Observer {
    public Form() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        //JLabel label = new JLabel("Drop stuff here", JLabel.CENTER);
        //DragAndDrop dad = new DragAndDrop(label);
        panelStartInit();

        //panelEncInit();
        //panelDecInit();
        frame.add(panelStart);
        frame.validate();
    }

    public static File file;
    //private File file1;
    private JFrame frame;
    private JPanel panelEnc;
    private JPanel panelDec;
    private JPanel panelStart;

    private void panelStartInit() {
        file = new File("newFile");
        panelStart = new JPanel();
        //JLabel background=new JLabel(new ImageIcon(Form.class.getResource("w2qV7qCs7eo.jpg")));
        //panelStart.add(background);
        //background.setLayout(new FlowLayout());

        panelStart.setBackground(Color.white);
        LabelObserver l = new LabelObserver();
        l.addObserver(this);
        final JLabel label = new JLabel("Drop stuff here");
        panelStart.add(label);
        panelStart.setTransferHandler(new DragAndDrop(label, l));
        panelStart.add(label);
    }


    private void panelEncInit(final JLabel l) {
        panelEnc = new JPanel();
        panelEnc.setBackground(Color.white);
        JLabel lab = new JLabel(l.getText());
        final JLabel label1 = new JLabel("Введите пароль");
        final JPasswordField pass1 = new JPasswordField(15);
        final JLabel label2 = new JLabel("Подтвердите пароль");
        final JPasswordField pass2 = new JPasswordField(15);
        JButton back = new JButton("Назад");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                l.setText("Drop stuff here");
                frame.remove(panelEnc);
                frame.add(panelStart);
                frame.repaint();
            }
        });
        JButton start = new JButton("Пуск");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Arrays.equals(pass1.getPassword(), pass2.getPassword())) {

                    label1.setText("Good");
                    try {
                        Coding.encryption(file, new String(pass1.getPassword()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        panelEnc.add(lab, BorderLayout.NORTH);
        Container c = new Container();
        c.setLayout(new GridLayout(2, 2));
        c.add(label1);
        c.add(pass1);
        c.add(label2);
        c.add(pass2);

        panelEnc.add(c, BorderLayout.CENTER);
        panelEnc.add(start, BorderLayout.SOUTH);
        panelEnc.add(back, BorderLayout.SOUTH);
    }

    private void panelDecInit(final JLabel l) {
        panelDec = new JPanel();
        panelDec.setBackground(Color.white);
        JLabel lab = new JLabel(l.getText());
        final JLabel label1 = new JLabel("Введите пароль");
        final JPasswordField pass1 = new JPasswordField(10);
        final JLabel label2 = new JLabel("Подтвердите пароль");
        final JPasswordField pass2 = new JPasswordField(10);
        final JLabel labelError = new JLabel("");
        JButton back = new JButton("Назад");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                l.setText("Drop stuff here");
                frame.remove(panelDec);
                frame.add(panelStart);
                frame.repaint();
            }
        });
        JButton start = new JButton("Пуск");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Arrays.equals(pass1.getPassword(), pass2.getPassword())) {
                    label1.setText("Good");
                    try {
                        Coding.decryption(file, new String(pass1.getPassword()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                        labelError.setText("Не удалось расшифровать файл, неверный пароль");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Container c1 = new Container();
        c1.setLayout(new GridLayout(2, 1));
        c1.add(lab);
        c1.add(labelError);

        Container c2 = new Container();

        c2.setLayout(new GridLayout(2, 2));
        c2.add(label1);
        c2.add(pass1);
        c2.add(label2);
        c2.add(pass2);

        Container c3 = new Container();
        c3.setLayout(new GridLayout(1,2));
        c3.add(start);
        c3.add(back);
        panelDec.add(c1);
        panelDec.add(c2);
        panelDec.add(c3);

    }

    public boolean equalsPass(char[] ch1, char[] ch2) {
        if (ch1.length != ch2.length)
            return false;
        for (int i = 0; i < ch1.length; i++)
            if (ch1[i] != ch2[i])
                return false;
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (((LabelObserver) o).getLabel().getText() != "Drop stuff here") {
            try {
                if (!Coding.checkFile(file)) {
                    panelEncInit(((LabelObserver) o).getLabel());
                    frame.remove(panelStart);
                    frame.add(panelEnc, BorderLayout.CENTER);
                    frame.validate();
                } else {
                    frame.remove(panelStart);
                    panelDecInit(((LabelObserver) o).getLabel());
                    frame.add(panelDec, BorderLayout.CENTER);
                    frame.validate();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
