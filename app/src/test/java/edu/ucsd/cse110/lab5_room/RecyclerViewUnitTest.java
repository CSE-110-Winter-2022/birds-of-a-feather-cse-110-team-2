package edu.ucsd.cse110.lab5_room;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;

import edu.ucsd.cse110.lab5_room.internal.Course;
import edu.ucsd.cse110.lab5_room.model.Classmate;
import edu.ucsd.cse110.lab5_room.model.Match;

public class RecyclerViewUnitTest {

    Course cse011 = new Course(Course.Department.CSE, 11, Course.Size.SMALL, 0);
    Course ece003 = new Course(Course.Department.ECE, 3, Course.Size.LARGE, 0);
    Course cse101 = new Course(Course.Department.CSE, 101, Course.Size.GIGANTIC, 0);

    @Test
    public void ItemCountIsCorrect() {
        Match[] matchData = {
                new Classmate("Alice", "alice.com", new HashSet<>(Collections.singletonList(cse011))),
                new Classmate("Bob", "bob.net", new HashSet<>(Collections.singletonList(ece003))),
                new Classmate("Carl", "carl.org", new HashSet<>(Collections.singletonList(cse101)))
        };
        PersonsViewAdapter adapter = new PersonsViewAdapter(matchData);
        assertEquals(adapter.getItemCount(), 3);
    }

    @Test
    public void IsBobCloseCorrect() {
        Match[] matchData = {
                new Classmate("Alice", "alice.com", new HashSet<>(Collections.singletonList(cse011))),
                new Classmate("Bob", "bob.net", new HashSet<>(Collections.singletonList(ece003))),
                new Classmate("Carl", "carl.org", new HashSet<>(Collections.singletonList(cse101)))
        };
        PersonsViewAdapter adapter = new PersonsViewAdapter(matchData);
//        assertFalse(matchData[1].isClose());
    }


}
