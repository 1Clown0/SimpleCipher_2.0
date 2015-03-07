package com.company;

import java.io.*;

/**
 * Created by Pavel on 28.02.2015.
 */
public class Coding {
    public Coding() {

    }

    public static void encryption(File file) throws IOException {
        byte[] bytes = getBytesFromFile(file);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]++;
        }
        File newFile = new File(file.getParent(), createFileName(file.getName(), "encrypted"));
        newFile.createNewFile();
        FileOutputStream out = new FileOutputStream(newFile);
        out.write(bytes);
        out.close();
    }

    public static void decryption(File file) throws IOException {
        byte[] bytes = getBytesFromFile(file);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]--;
        }
        File newFile = new File(file.getParent(), createFileName(file.getName(), "decrypted"));
        newFile.createNewFile();
        FileOutputStream out = new FileOutputStream(newFile);
        out.write(bytes);
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
        char[] filename2 = new char[fileName.length() - n - 1];
        for (int i = n + 1; i < fileName.length(); i++)
            filename2[i - n - 1] = fileName.charAt(i);
        String secondPart = new String(filename2);
        return firstPart + "(" + mode + ")" + "." + secondPart;
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
}
