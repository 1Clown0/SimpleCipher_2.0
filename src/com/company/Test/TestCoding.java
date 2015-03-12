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
    public void testCreateFileName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test(decrypted).txt") == true);
    }

    @Test
    public void testNotCreateFileName() throws Exception {
        Coding coding = new Coding();
        assertTrue(coding.createFileName("test.txt", "decrypted").equalsIgnoreCase("test") == false);
    }

}