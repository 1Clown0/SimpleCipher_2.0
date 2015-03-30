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
 * <p> Класс Form отвечает за создание рабочего окна с несколькими панелями panelStart, panelEnc, panelDec.
 * Внутри этого класса происходит вызов всех остальных функций программы.</p>
 * Created by Pavel on 28.02.2015.
 * @author Pavel
 *
 */
public class Form implements Observer {
    public Form() {
        /**
         * <p> Конструктор фрейма, здесь создаётся окно размером 600 на 200 пикселей.
         * и инициализируется стартовая панель</p>
         */
        frame = new JFrame();
        frame.setTitle("SimpleCipher 2.0");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
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

    public static File file; /** {@value}  file хранит объект типа File, то есть файл, с которым работаем */
    /**
     * Основной фрейм в котором происходит переключение панелей
     */
    private JFrame frame;
    /**
     * Панель шифрования
     */
    private JPanel panelEnc;
    /**
     * Панель расшифровки
     */
    private JPanel panelDec;
    /**
     * Стартовая панель
     */
    private JPanel panelStart;

    /**
     *  <p>Инициализация стартовой панели </p>
     */
    private void panelStartInit() { /**Инициализация стартовой панели */
        file = new File("newFile");
        panelStart = new JPanel();
        panelStart.setBackground(Color.white);
        LabelObserver l = new LabelObserver();
        l.addObserver(this);
        final JLabel label = new JLabel("Перетащите файл в окно");
        panelStart.add(label, BorderLayout.CENTER);
        panelStart.setTransferHandler(new DragAndDrop(label, l)); /** Панель принимает файлы */
        panelStart.add(label);
    }

    /**
     *<p> Инициализация панели шифрования</p>
     * @param l Лэйбл содержит название расшифруемого файла
     */
    private void panelEncInit(final JLabel l) { /** Инициализация панели шифрования */
        panelEnc = new JPanel();
        panelEnc.setBackground(Color.white);
        JLabel lab = new JLabel(l.getText());
        final JLabel label1 = new JLabel("Введите пароль");
        final JPasswordField pass1 = new JPasswordField(15);
        final JLabel label2 = new JLabel("Подтвердите пароль");
        final JPasswordField pass2 = new JPasswordField(15);
        final JLabel labelError = new JLabel("ШИФРОВАНИЕ");
        JButton back = new JButton("Назад");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                l.setText("Перетащите файл в окно");
                frame.remove(panelEnc);
                frame.add(panelStart);
                frame.repaint();
            }
        });
        JButton start = new JButton("Пуск"); /** Вызывает методы для шифрования файла */
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Arrays.equals(pass1.getPassword(), pass2.getPassword())) {
                    try {
                        if (!Coding.encryption(file, new String(pass1.getPassword())))
                            labelError.setText("Что-то пошло не так");
                        else
                            labelError.setText("Файл успешно зашифрован");
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
                else
                    labelError.setText("Введённые пароли не совпадают");
            }
        });
        panelEnc.add(labelError);
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

    /**
     *<p> Инициализация панели расшифровки</p>
     * @param l Лэйбл содержит название расшифруемого файла
     */
    private void panelDecInit(final JLabel l) { /** Инициализация панели расшифрования */
        panelDec = new JPanel();
        panelDec.setBackground(Color.white);
        JLabel lab = new JLabel(l.getText());
        final JLabel label1 = new JLabel("Введите пароль");
        final JPasswordField pass1 = new JPasswordField(10);
        final JLabel label2 = new JLabel("Подтвердите пароль");
        final JPasswordField pass2 = new JPasswordField(10);
        final JLabel labelError = new JLabel("РАСШИФРОВКА");
        JButton back = new JButton("Назад");
        back.addActionListener(new ActionListener() { /** Возвращение на стартовую панель */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                l.setText("Перетащите файл в окно");
                frame.remove(panelDec);
                frame.add(panelStart);
                frame.repaint();
            }
        });
        JButton start = new JButton("Пуск"); /** Вызывает методы для расшифрования файла */
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Arrays.equals(pass1.getPassword(), pass2.getPassword())) {
                    try {
                        if (!Coding.decryption(file,new String(pass1.getPassword())))
                        {
                            labelError.setText("Не удалось расшифровать файл, не верный пароль");
                        }
                        else
                            labelError.setText("Файл успешно расшифрован");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        labelError.setText("Не удалось расшифровать файл, не верный пароль");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }

                }
                else
                    labelError.setText("Введённые пароли не совпадают");
            }
        });

        panelDec.add(labelError);
        panelDec.add(lab, BorderLayout.NORTH);
        Container c = new Container();
        c.setLayout(new GridLayout(2, 2));
        c.add(label1);
        c.add(pass1);
        c.add(label2);
        c.add(pass2);

        panelDec.add(c, BorderLayout.CENTER);
        panelDec.add(start, BorderLayout.SOUTH);
        panelDec.add(back, BorderLayout.SOUTH);
    }

    /**
     *
     * <p> Проверка на совпадение паролей с разных полей</p>
     * @param ch1 Пароль с первого поля для паролей
     * @param ch2 Пароль со второго поля для паролей
     * @return true если пароли совпадают и false иначе
     */
    public boolean equalsPass(char[] ch1, char[] ch2) {
        if (ch1.length != ch2.length)
            return false;
        for (int i = 0; i < ch1.length; i++)
            if (ch1[i] != ch2[i])
                return false;

        return true;
    }

    /**
     * <p> Обыкновенный Observer, то бишь наблюдение за изменением содержимого панели.
     * То есть вызивается при перетаскивании файла в стартовую панель и название файла переносится в лэйбл</p>
     * @param o Наблюдатель
     * @param arg хрен знает что
     *
     */
    @Override
    public void update(Observable o, Object arg) {
        if (((LabelObserver) o).getLabel().getText() != "Перетащите файл в окно") {
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
