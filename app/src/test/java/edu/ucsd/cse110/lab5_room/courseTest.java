package edu.ucsd.cse110.lab5_room;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;
import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import java.util.Collections;
import java.util.HashSet;

import edu.ucsd.cse110.lab5_room.internal.Course;
import edu.ucsd.cse110.lab5_room.model.Classmate;
import edu.ucsd.cse110.lab5_room.model.Match;

public class courseTest {

    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;

    Course cse001 = new Course(Course.Department.CSE, 1, Course.Size.TINY, 0);
    Course cse002 = new Course(Course.Department.CSE, 2, Course.Size.SMALL, 0);
    Course cse003 = new Course(Course.Department.CSE, 3, Course.Size.MEDIUM, 0);
    Course cse004 = new Course(Course.Department.CSE, 4, Course.Size.LARGE,0);
    Course cse005 = new Course(Course.Department.CSE, 5, Course.Size.HUGE,0);
    Course cse006 = new Course(Course.Department.CSE, 6, Course.Size.GIGANTIC,0);
    Course ece007 = new Course(Course.Department.ECE, 7, Course.Size.TINY,0);

    @Test
    public void SetUp(){
        course1 = new Course(Course.Department.CSE, 10, Course.Size.MEDIUM, 30);
        course2 = new Course(Course.Department.ECE, 26, Course.Size.HUGE, 20);
        course3 = new Course(Course.Department.CSE, 111, Course.Size.GIGANTIC, 46);
        course4 = new Course(Course.Department.ECE, 67, Course.Size.TINY, 16);
    }

    @Test
    public void GetSizeTest(){
        assertEquals(Course.Size.MEDIUM, course1.getSize());
        assertEquals(Course.Size.HUGE, course2.getSize());
        assertEquals(Course.Size.GIGANTIC, course3.getSize());
        assertEquals(Course.Size.TINY, course4.getSize());
    }
    @Test
    public void GetDepartmentTest(){
        assertEquals(Course.Department.CSE, course1.getSize());
        assertEquals(Course.Department.ECE, course2.getSize());
        assertEquals(Course.Department.CSE, course3.getSize());
        assertEquals(Course.Department.ECE, course4.getSize());
    }
    @Test
    public void GetNumberTest(){
        assertEquals(10, course1.getNumber());
        assertEquals(26, course2.getNumber());
        assertEquals(111, course3.getNumber());
        assertEquals(67, course4.getNumber());
    }
    @Test
    public void GetQuartersAgoTest(){
        assertEquals(30, course1.getQuartersAgo());
        assertEquals(20, course2.getQuartersAgo());
        assertEquals(46, course3.getQuartersAgo());
        assertEquals(16, course4.getQuartersAgo());
    }


    @Test
    public void courseSizeOutput(){
        assertEquals(cse001.getSize(), 1);
        assertEquals(cse001.getSize().TINY.toString(), "Tiny");

        assertEquals(cse002.getSize(),0.33);
        assertEquals(cse001.getSize().SMALL.toString(), "Small");

        assertEquals(cse003.getSize(),0.18);
        assertEquals(cse001.getSize().MEDIUM.toString(), "Medium");

        assertEquals(cse004.getSize(),0.1);
        assertEquals(cse001.getSize().LARGE.toString(), "Large");

        assertEquals(cse005.getSize(),0.08);
        assertEquals(cse001.getSize().HUGE.toString(), "Huge");

        assertEquals(cse006.getSize(),0.03);
        assertEquals(cse001.getSize().GIGANTIC.toString(), "Gigantic");

        assertEquals(ece007.getSize(),1);
        assertEquals(cse001.getSize().TINY.toString(), "Tiny");
    }
    @Test
    public void courseNameOutput(){
        assertEquals(cse001.getClass(),1);
        assertEquals(cse002.getClass(),2);
        assertEquals(cse003.getClass(),3);
        assertEquals(cse004.getClass(),4);
        assertEquals(cse005.getClass(),5);
        assertEquals(cse006.getClass(),6);
        assertEquals(ece007.getClass(),7);
    }

    @Test
    public void getDepart(){
        assertEquals(cse001.getDepartment(), "CSE");
        assertEquals(cse002.getDepartment(),"CSE");
        assertEquals(cse003.getDepartment(),"CSE");
        assertEquals(cse004.getDepartment(),"CSE");
        assertEquals(cse005.getDepartment(),"CSE");
        assertEquals(cse006.getDepartment(),"CSE");
        assertEquals(ece007.getDepartment(),"ECE");
    }


}
