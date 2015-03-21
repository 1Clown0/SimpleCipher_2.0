package com.company;

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
    public Coding() {

    }

    public static void encryption(File file) throws IOException{
        byte[] bytes = getBytesFromFile(file);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]++;
        }

        byte[] mark = new byte[10];
        for (int i=0;i<mark.length;i++)
            mark[i]=-128;
        String tmp = createFileName(file.getName(), "encrypted") + "." + "cipher";
        File newFile = new File(file.getParent(),tmp);
        newFile.createNewFile();
        FileOutputStream out = new FileOutputStream(newFile);
        out.write(mark);
        out.write(bytes);
        out.close();
    }
    public static void decryption(File file) throws IOException {
        byte[] bytes = getBytesFromFile(file);
        byte[] forWrite = new byte[bytes.length-10];
        for (int i = 0; i < forWrite.length; i++) {
            bytes[i+10]--;
            forWrite[i]=bytes[i+10];
        }
        String tmp = createFileName(file.getName(), "decrypted") + "." + "txt";
        File newFile = new File(file.getParent(),tmp);
        newFile.createNewFile();
        FileOutputStream out = new FileOutputStream(newFile);
        out.write(forWrite);
        out.close();
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
        return firstPart + "(" + mode + ")";
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
