import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by alexanderschaevitz on 3/7/17.
 * tests istudent
 */
public class TestStudent {

    private IStudent student;
    private IInstructor instructor;
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    //TESTING registerForClass -----------------------------------
    //should work
    @Test
    public void test0() {
        this.admin.createClass("class", 2017, "instructor", 1);
        this.student.registerForClass("student", "class", 2017);
        assertTrue(this.student.isRegisteredFor("student", "class", 2017));
    }

    //full class -- should fail
    @Test
    public void test1() {
        this.admin.createClass("class", 2017, "instructor", 1);
        this.student.registerForClass("earlierstudent", "class", 2017);
        this.student.registerForClass("student", "class", 2017);
        assertFalse(this.student.isRegisteredFor("student", "class", 2017));
    }

    //class dne  -- should fail
    @Test
    public void test2() {
        //admin.createClass("class",2017,"instructor",1);
        this.student.registerForClass("student", "class", 2017);
        assertFalse(this.student.isRegisteredFor("student", "class", 2017));
    }

    //wrong year -- should fail
    @Test
    public void test3() {
        this.admin.createClass("class", 2017, "instructor", 1);
        this.student.registerForClass("student", "class", 2018);
        assertFalse(this.student.isRegisteredFor("student", "class", 2017));
    }

    @Test
    //testing dropClass
    public void test4() {
        this.admin.createClass("class", 2017, "instructor", 1);
        this.student.registerForClass("student", "class", 2017);
        this.student.dropClass("student", "class", 2017);
        assertFalse(this.student.isRegisteredFor("student", "class", 2017));
    }
    @Test
    //testing dropClass -- wrong year
    public void test6() {
        this.admin.createClass("class", 2017, "instructor", 1);
        this.student.registerForClass("student", "class", 2017);
        this.student.dropClass("student", "class", 2018);
        assertTrue(this.student.isRegisteredFor("student", "class", 2017));
    }

    //testing submitHomework
    //correct Use
    @Test
    public void test5() {
        this.admin.createClass("class", 2017, "instructor", 15);
        this.instructor.addHomework("instructor", "class", 2017, "hw", "hwd");
        this.student.registerForClass("student", "class", 2017);
        this.student.submitHomework("student", "hw", "a", "class", 2017);
        assertTrue(this.student.hasSubmitted("student","hw","class",2017));
    }
    //incorrect HW name
    @Test
    public void test7() {
        this.admin.createClass("class", 2017, "instructor", 15);
        this.instructor.addHomework("instructor", "class", 2017, "hw", "hwd");
        this.student.registerForClass("student", "class", 2017);
        this.student.submitHomework("student", "hw_", "a", "class", 2017);
        assertFalse(this.student.hasSubmitted("student","hw","class",2017));
    }
//incorrect className
    @Test
    public void test8() {
        this.admin.createClass("class", 2017, "instructor", 15);
        this.instructor.addHomework("instructor", "class", 2017, "hw", "hwd");
        this.student.registerForClass("student", "class", 2017);
        this.student.submitHomework("student", "hw", "a", "class_", 2017);
        assertFalse(this.student.hasSubmitted("student","hw","class",2017));
    }
    //incorrect year
    @Test
    public void test9() {
        this.admin.createClass("class", 2017, "instructor", 15);
        this.instructor.addHomework("instructor", "class", 2017, "hw", "hwd");
        this.student.registerForClass("student", "class", 2017);
        this.student.submitHomework("student", "hw", "a", "class", 2018);
        assertFalse(this.student.hasSubmitted("student","hw","class",2017));
    }
}