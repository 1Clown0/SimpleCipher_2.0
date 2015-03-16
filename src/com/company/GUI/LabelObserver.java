package com.company.GUI;

import javax.swing.*;
import java.util.Observable;

/**
 * Created by Pavel on 15.03.2015.
 */
public class LabelObserver extends Observable {
    JLabel label  = new JLabel();
    public void labelChanged(JLabel l){
        label = l;
        setChanged();
        notifyObservers();
    }

    public JLabel getLabel() {
        return label;
    }

}
