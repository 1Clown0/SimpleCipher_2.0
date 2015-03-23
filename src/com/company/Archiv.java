package com.company;

import java.io.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Pavel on 23.03.2015.
 */
public class Archiv {
    public static File makeZip(File file) throws IOException {
        return add(file.getParent()+"\\tempZip.zip",file.getPath());
    }
    public static File makeFile(File file) throws IOException {
        return unpack(file.getPath(),file.getParent());
    }
    static public File add(String zipFileName, String fileName) throws IOException {
        File zipFile = new File(zipFileName);
        zipFile.createNewFile();
        File tmpFile = File.createTempFile("zip", "tmp");
        File newFile = new File(fileName);

        byte[] buffer = new byte[8192];
        int readed;

        ZipOutputStream zipOutputStream = new ZipOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(tmpFile)));

        try {
            if (!zipFile.exists()) {
                ZipInputStream zipInputStream = new ZipInputStream(
                        new BufferedInputStream(
                                new FileInputStream(zipFile)));

                try {
                    ZipEntry entry;

                    while ((entry = zipInputStream.getNextEntry()) != null){
                        if (entry.getName().equals(newFile.getName())) {
                            continue;
                        }

                        ZipEntry newEntry = new ZipEntry(entry);
                        zipOutputStream.putNextEntry(newEntry);

                        while ((readed = zipInputStream.read(buffer)) > 0) {
                            zipOutputStream.write(buffer, 0, readed);
                        }

                        zipOutputStream.closeEntry();
                    }
                }
                finally {
                    zipInputStream.close();
                }
            }

            InputStream fileInputStream = new BufferedInputStream(
                    new FileInputStream(newFile));

            try {
                System.out.printf("Adding %s\n", fileName);

                ZipEntry newEntry = new ZipEntry(newFile.getName());
                newEntry.setSize(newFile.length());
                newEntry.setTime(newFile.lastModified());

                zipOutputStream.putNextEntry(newEntry);

                while ((readed = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, readed);
                }

                zipOutputStream.closeEntry();
            }
            finally {
                fileInputStream.close();
            }
        }
        finally {
            zipOutputStream.close();
        }

        if (zipFile.exists()) {
            zipFile.delete();
        }

        tmpFile.renameTo(zipFile);
        //System.out.println(unpack(zipFile.getPath(), zipFile.getParent()));
        tmpFile.delete();
        return zipFile;
    }
    public static File unpack(String path, String dir_to) throws IOException {

        ZipFile zip = new ZipFile(path);
        Enumeration entries = zip.entries();
        LinkedList<ZipEntry> zfiles = new LinkedList<ZipEntry>();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (entry.isDirectory()) {
                new File(dir_to+"/"+entry.getName()).mkdir();
            } else {
                zfiles.add(entry);
            }
        }

        File outfile = null;
        for (ZipEntry entry : zfiles) {
            InputStream in = zip.getInputStream(entry);
            outfile = new File(dir_to+"/"+Coding.createFileName(entry.getName(),"decrypted"));
            OutputStream out = new FileOutputStream(outfile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) >= 0)
                out.write(buffer, 0, len);
            in.close();
            out.close();
        }
        zip.close();
        return outfile;
    }
}
