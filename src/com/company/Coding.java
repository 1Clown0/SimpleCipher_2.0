package com.company;

import javafx.scene.shape.Arc;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Pavel on 28.02.2015.
 */
public class Coding {
    public Coding() {}

    public static boolean encryption(File file, String password) throws IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        File zipFile = Archiving.makeZip(file);
        byte[] bytes = getBytesFromFile(zipFile);
        zipFile.delete();
        byte[] mark = new byte[10];
        for (int i=0;i<mark.length;i++)
            mark[i]=-128;
        String tmp = createFileName(file.getName(), "encrypted");
        File newFile = new File(file.getParent(),tmp);
        newFile.createNewFile();
        FileOutputStream out = new FileOutputStream(newFile);
        out.write(mark);
        AesCrypt aes = new AesCrypt(password);
        byte[] temp;
        temp = aes.encrypt(bytes).orThrow();
        out.write(temp);
        out.close();
        return true;
    }
    public static boolean decryption(File file, String password) throws IOException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        byte[] bytes = getBytesFromFile(file);
        byte[] forWrite = new byte[bytes.length-10];
        for (int i = 0; i < forWrite.length; i++) {
            forWrite[i]=bytes[i+10];
        }
        //String tmp = createFileName(file.getName(), "decrypted") + "." + "docx";
        //File newFile = new File(file.getParent(),tmp);
       // newFile.createNewFile();
        //FileOutputStream out = new FileOutputStream(newFile);
        File zipFile = new File(file.getParent(),"tempZip.zip");
        FileOutputStream out = new FileOutputStream(zipFile);
        AesCrypt aes = new AesCrypt(password);
        try {
            byte[] temp = aes.decrypt(forWrite).orThrow();
            out.write(temp);
            File newFile = Archiving.makeFile(zipFile);
            System.out.println(newFile.getPath());

        }
        catch (BadPaddingException e)
        {
            out.close();
            System.out.println("error");
            zipFile.delete();
            return false;
        }
        out.close();
        zipFile.delete();
        return true;
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            return null;
        }

        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static String createFileName(String fileName, String mode) {
        int n;
        for (n = 0; n < fileName.length() && fileName.charAt(n) != '.'; n++) ;
        char[] filename1 = new char[n];
        for (int i = 0; i < n; i++)
            filename1[i] = fileName.charAt(i);
        String firstPart = new String(filename1);
        char[] filename2 = new char[fileName.length()-n];
        for (int i=0;i<filename2.length;i++)
            filename2[i]=fileName.charAt(i+n);
        String secondPart = new String(filename2);
        if (mode.equals("encrypted"))
            return firstPart + "(" + mode + ").cipher";
        else
            return firstPart + "(" + mode + ")"+secondPart;
    }

    static public boolean checkName(File file) {
        int n;
        for (n = 0; n < file.getName().length() && file.getName().charAt(n) != '.'; n++) ;
        char[] buf = new char[file.getName().length() - n-1];
        file.getName().getChars(n+1, file.getName().length(), buf, 0);
        String tmp = new String(buf);
        if (tmp.equals("cipher")) {
            return true;
        } else
            return false;
    }

    static public boolean checkFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        long length=file.length();
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && offset<length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        boolean flag = true;
        if (bytes.length<=10)
            return false;
        for (int i=0;i<10;i++)
            if (bytes[i]!=-128)
                flag = false;
        return flag;

    }

}
