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
        FileOutputStream out = new FileOutputStream(file);
        out.write(bytes);
        out.close();
    }

    public static void decryption(File file) throws IOException {
        byte[] bytes = getBytesFromFile(file);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]--;
        }

        FileOutputStream out = new FileOutputStream(file);
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
}
