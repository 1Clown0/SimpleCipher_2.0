package com.company.GUI;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pavel on 07.03.2015.
 */
public class DragAndDrop extends TransferHandler {

    public JLabel label;
    LabelObserver l;

    public DragAndDrop(JLabel label, LabelObserver l)
    {
        super();
        this.label = label;
        this.l = l;

    }
    public boolean canImport(TransferSupport support) {

        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
                || support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {

        Transferable t = support.getTransferable();
        try {

            if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {

                Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

                @SuppressWarnings("unchecked")
                List<File> files = (List<File>) o;


                StringBuilder sb = new StringBuilder("<ul>");
                for (File file : files)
                    sb.append("<li>" + file);
                if (files.size()>1)
                    label.setText("<html> Too mane files");
                else {
                    label.setText("<html>" + files.size() +
                            " files dropped<br>" + sb);

                    if (files.get(0).isFile()) {
                        Form.file = files.get(0);
                        l.labelChanged(label);

                    } else
                        label.setText("<html>It isn' file<br>");
                }
            }
            else if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {

                Object o = t.getTransferData(DataFlavor.stringFlavor);
                String str = o.toString();

                label.setText(
                        "<html>Hell now how many files you trying to drop..<br>" +
                                str);
            }

        } catch (UnsupportedFlavorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return super.importData(support);

    }
}
