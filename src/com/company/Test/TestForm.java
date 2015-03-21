package com.company.Test;

import com.company.GUI.Form;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;


/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 28.02.15
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */

@RunWith(BlockJUnit4ClassRunner.class)

public class TestForm extends TestCase {

    @Test
    public void testEqualsPass() throws Exception {
        Form form = new Form();
        assertTrue(form.equalsPass(new char[]{'a', 'b', 'c'}, new char[]{'a', 'b', 'c'}) == true);
    }

    @Test
    public void testNotEqualsPass() throws Exception {
        Form form = new Form();
        assertTrue(form.equalsPass(new char[]{'a', 'b', 'c'}, new char[]{'x', 'y', 'z'}) == false);
    }

    @Test
    public void testNotEqualsPassFirstItem() throws Exception {
        Form form = new Form();
        assertTrue(form.equalsPass(new char[]{'a', 'b', 'c'}, new char[]{'x', 'b', 'c'}) == false);
    }

    @Test
    public void testNotEqualsPassSecondItem() throws Exception {
        Form form = new Form();
        assertTrue(form.equalsPass(new char[]{'a', 'b', 'c'}, new char[]{'a', 'x', 'c'}) == false);
    }

    @Test
    public void testNotEqualsPassLastItem() throws Exception {
        Form form = new Form();
        assertTrue(form.equalsPass(new char[]{'a', 'b', 'c'}, new char[]{'a', 'b', 'x'}) == false);
    }

    @Test
    public void testNotEqualsPassLowercase() throws Exception {
        Form form = new Form();
        assertTrue(form.equalsPass(new char[]{'a', 'b', 'c'}, new char[]{'A', 'B', 'C'}) == false);
    }

    @Test
    public void testNotEqualsPassUppercase() throws Exception {
        Form form = new Form();
        assertTrue(form.equalsPass(new char[]{'A', 'B', 'C'}, new char[]{'a', 'b', 'c'}) == false);
    }
}
