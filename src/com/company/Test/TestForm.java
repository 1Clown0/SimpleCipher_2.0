package com.company.Test;

import com.company.GUI.Form;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 28.02.15
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */

@RunWith(BlockJUnit4ClassRunner.class)

public class TestForm extends TestCase {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testEqualsPass() throws Exception {
        Form form = new Form();
        assertTrue(form.equalsPass(new char[]{'a', 'b', 'c'}, new char[]{'a', 'b', 'c'}) == true);
    }
}
