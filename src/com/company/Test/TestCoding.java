package com.company.Test;

import com.company.Coding;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;

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
    public void testCreateFileFullName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(decrypted).txt") == true);
    }

    @Test
    public void testNotCreateFileFullName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("") == false);
    }

    @Test
    public void testNotCreateFileName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("(decrypted).txt") == false);
    }

    @Test
    public void testNotCreateFileMode() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test().txt") == false);
    }

    @Test
    public void testNotCreateFileExtension() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(decrypted).") == false);
    }

    @Test
    public void testCreateInvalidFileName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("xxx(decrypted).txt") == false);
    }

    @Test
    public void testCreateInvalidFileMode() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(xxx).txt") == false);
    }

    @Test
    public void testCreateInvalidFileExtension() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(decrypted).xxx") == false);
    }

    @Test
    public void testCreateFileFullNameWithoutDot() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(decrypted)txt") == false);
    }

}