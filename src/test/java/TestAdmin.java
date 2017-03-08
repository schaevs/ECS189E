import api.IAdmin;
import api.core.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexanderschaevitz on 3/7/17.
 * testing iadmin
 */
public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }
    //TESTING createClass()----------------------------------------------------

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    //if year < 2017
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    //if !(capacity > 0)
    @Test
    public void testMakeClass3() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    //negative capacity
    @Test
    public void testMakeClass8() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    //instructor should have no more than 2 classes
    public void testMakeClass4() {
        this.admin.createClass("Test0", 2017, "Instructor", 15);
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test2", 2017));
    }


    //TESTING changeCapacity()----------------------------------------------------

    //if change capacity works given correct specs
    @Test
    public void testMakeClass5() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 16);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 16);
    }


    //can't make capacity smaller
    @Test
    public void testMakeClass6() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 14);
        assertFalse(this.admin.getClassCapacity("Test", 2017) == 14);
    }

    //can't make capacity the same ...?

    //TESTING corner bugs

    //same instructor different years
    @Test
    public void testMakeClass7() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2018, "Instructor", 16);
        assertTrue(this.admin.classExists("Test",2017));
        assertTrue(this.admin.classExists("Test",2018));
        this.admin.changeCapacity("Test",2017,17);
        assertTrue(this.admin.getClassCapacity("Test",2017) == 17);
        assertTrue(this.admin.getClassCapacity("Test",2018) == 16);
    }
}
