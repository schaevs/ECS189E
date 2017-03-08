import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexanderschaevitz on 3/7/17.
 * tests iinstructor
 */
public class TestInstructor {

    private IInstructor instructor;
    private IStudent student;
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    //TESTING addHomework()----------------------------------------------
    // --should succeed
    @Test
    public void test0() {

        admin.createClass("class",2017,"instructor",15);
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        assertTrue(instructor.homeworkExists("class",2017,"hw"));
    }

    //different instructor -- should fail
    @Test
    public void test1() {
        admin.createClass("class",2017,"diffinstructor",15);
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        assertFalse(instructor.homeworkExists("class",2017,"hw"));
    }

    //different year -- should fail
    @Test
    public void test2() {
        admin.createClass("class",2018,"instructor",15);
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        assertFalse(instructor.homeworkExists("class",2017,"hw"));
    }

    //no class "class" -- should fail
    @Test
    public void test10() {
        admin.createClass("diffclass",2017,"instructor",15);
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        assertFalse(instructor.homeworkExists("class",2017,"hw"));
    }

    //no class "class" -- should fail
    @Test
    public void test3() {
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        assertFalse(instructor.homeworkExists("class",2017,"hw"));
    }


    //TESTING assignGrade-------------------
    //--should succeed
    @Test
    public void test4(){
        admin.createClass("class",2017,"instructor",15);
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        student.registerForClass("student","class",2017);
        student.submitHomework("student","hw","a","class",2017);
        instructor.assignGrade("instructor","class",2017,"hw","student",100);
        assertTrue(100 == instructor.getGrade("class", 2017, "hw", "student"));
    }

    //class doesn't exist -- fail
    @Test
    public void test5(){
        //admin.createClass("class",2017,"instructor",15);
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        student.registerForClass("student","class",2017);
        student.submitHomework("student","hw","a","class",2017);
        instructor.assignGrade("instructor","class",2017,"hw","student",100);
        assertNull(instructor.getGrade("class", 2017, "hw", "student"));
    }

    //different instructor assigns grade -- fail
    @Test
    public void test11(){
        admin.createClass("class",2017,"diffinstructor",15);
        instructor.addHomework("diffinstructor","class",2017,"hw","hwd");
        student.registerForClass("student","class",2017);
        student.submitHomework("student","hw","a","class",2017);
        instructor.assignGrade("instructor","class",2017,"hw","student",100);
        assertNull(instructor.getGrade("class", 2017, "hw", "student"));
    }

    //homework doesn't exist -- fail
    @Test
    public void test6(){
        admin.createClass("class",2017,"instructor",15);
        //instructor.addHomework("instructor","class",2017,"hw","hwd");
        student.registerForClass("student","class",2017);
        student.submitHomework("student","hw","a","class",2017);
        instructor.assignGrade("instructor","class",2017,"hw","student",100);
        assertNull(instructor.getGrade("class", 2017, "hw", "student"));
    }

    //student didn't submit
    @Test
    public void test7(){
        admin.createClass("class",2017,"instructor",15);
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        student.registerForClass("student","class",2017);
        //student.submitHomework("student","hw","a","class",2017);
        instructor.assignGrade("instructor","class",2017,"hw","student",100);
        assertFalse(100 == instructor.getGrade("class", 2017, "hw", "student"));
    }

    //TESTING homework exists
    //incorrect year -- should fail
    @Test
    public void test8() {
        admin.createClass("class",2017,"instructor",15);
        instructor.addHomework("instructor","class",2018,"hw","hwd");
        assertFalse(instructor.homeworkExists("class",2017,"hw"));
    }
    @Test
    public void test9() {
        admin.createClass("class",2017,"instructor",15);
        instructor.addHomework("instructor","class",2017,"hw","hwd");
        assertFalse(instructor.homeworkExists("class",2018,"hw"));
    }
}
