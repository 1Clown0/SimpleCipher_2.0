package com.company.Test;

import com.company.Coding;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 28.02.15
 * Time: 17:13
 * To change this template use File | Settings | File Templates.
 */

@RunWith(BlockJUnit4ClassRunner.class)

public class TestCoding extends TestCase {

    @Test
    public void testOkCheckName() throws Exception {
        Coding coding = new Coding();
        File newFile = new File("test.cipher");
        assertTrue(coding.checkName(newFile) == true);
    }

    @Test
    public void testFailCheckName() throws Exception {
        Coding coding = new Coding();
        File newFile = new File("test.txt");
        assertTrue(coding.checkName(newFile) == false);
    }

    @Test
    public void testCreateDecryptedFileFullName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(decrypted).txt") == true);
    }

    @Test
    public void testCreateEncryptedFileFullName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "encrypted").equalsIgnoreCase("test(encrypted).cipher") == true);
    }

    @Test
    public void testNotCreateDecryptedFileFullName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("") == false);
    }

    @Test
    public void testNotCreateEncryptedFileFullName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "encrypted").equalsIgnoreCase("") == false);
    }

    @Test
    public void testNotCreateDecryptedFileName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("(decrypted).txt") == false);
    }

    @Test
    public void testNotCreateEncryptedFileName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "encrypted").equalsIgnoreCase("(encrypted).cipher") == false);
    }

    @Test
    public void testNotCreateDecryptedFileMode() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test().txt") == false);
    }

    @Test
    public void testNotCreateEncryptedFileMode() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "encrypted").equalsIgnoreCase("test().cipher") == false);
    }

    @Test
    public void testNotCreateDecryptedFileExtension() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(decrypted)") == false);
    }

    @Test
    public void testNotCreateEncryptedFileExtension() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "encrypted").equalsIgnoreCase("test(encrypted)") == false);
    }

    @Test
    public void testCreateInvalidDecryptedFileName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("xxx(decrypted).txt") == false);
    }

    @Test
    public void testCreateInvalidEncryptedFileName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "encrypted").equalsIgnoreCase("xxx(encrypted).cipher") == false);
    }

    @Test
    public void testCreateInvalidDecryptedFileMode() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(xxx).txt") == false);
    }

    @Test
    public void testCreateInvalidEncryptedFileMode() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "encrypted").equalsIgnoreCase("test(xxx).cipher") == false);
    }

    @Test
    public void testCreateInvalidDecryptedFileExtension() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(decrypted).xxx") == false);
    }

    @Test
    public void testCreateInvalidEncryptedFileExtension() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "encrypted").equalsIgnoreCase("test(encrypted).xxx") == false);
    }

    @Test
    public void testGetBytesFromFile() throws Exception {
        File file = new File("testFile.txt");
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        out.write("abc".getBytes());
        out.close();
        assertTrue(new String(Coding.getBytesFromFile(file)).equals("abc") == true);
        file.delete();
    }

    @Test
    public void testGetInvalidBytesFromFile() throws Exception {
        File file = new File("testFile.txt");
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        out.write("abc".getBytes());
        out.close();
        assertTrue(new String(Coding.getBytesFromFile(file)).equals("xyz") == false);
        file.delete();
    }
}