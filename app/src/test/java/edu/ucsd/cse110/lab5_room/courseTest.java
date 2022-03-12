package edu.ucsd.cse110.lab5_room;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;

import edu.ucsd.cse110.lab5_room.internal.Course;
import edu.ucsd.cse110.lab5_room.model.Classmate;
import edu.ucsd.cse110.lab5_room.model.Match;

public class courseTest {
    Course cse001 = new Course(Course.Department.CSE, 1, Course.Size.TINY, 0);
    Course cse002 = new Course(Course.Department.CSE, 2, Course.Size.SMALL, 0);
    Course cse003 = new Course(Course.Department.CSE, 3, Course.Size.MEDIUM, 0);
    Course cse004 = new Course(Course.Department.CSE, 4, Course.Size.LARGE,0);
    Course cse005 = new Course(Course.Department.CSE, 5, Course.Size.HUGE,0);
    Course cse006 = new Course(Course.Department.CSE, 6, Course.Size.GIGANTIC,0);
    Course ece007 = new Course(Course.Department.ECE, 7, Course.Size.TINY,0);

    @Test
    public void courseSizeOutput(){
        assertEquals(cse001.getSize(), 1);
        assertEquals(cse001.getSize().toString(), "Tiny");

        assertEquals(cse002.getSize(),0.33);
        assertEquals(cse001.getSize().toString(), "Small");

        assertEquals(cse003.getSize(),0.18);
        assertEquals(cse001.getSize().toString(), "Medium");

        assertEquals(cse004.getSize(),0.1);
        assertEquals(cse001.getSize().toString(), "Large");

        assertEquals(cse005.getSize(),0.08);
        assertEquals(cse001.getSize().toString(), "Huge");

        assertEquals(cse006.getSize(),0.03);
        assertEquals(cse001.getSize().toString(), "Gigantic");

        assertEquals(ece007.getSize(),1);
        assertEquals(cse001.getSize().toString(), "Tiny");

    }
    @Test
    public void courseNameOutput(){
        assertEquals(cse001.getClass(), 1);
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
