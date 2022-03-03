package edu.ucsd.cse110.lab5_room;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import edu.ucsd.cse110.lab5_room.model.DummyStudent;
import edu.ucsd.cse110.lab5_room.model.Student;
import edu.ucsd.cse110.lab5_room.internal.StudentFilter;

public class FilterUnitTest {

    @Test
    public void filteredFavoritesCorrect() {
        Student[] studentData = {
            new DummyStudent("Alice", "alice.com", new String[]{"CSE 11"}, true, true),
            new DummyStudent("Bob", "bob.net", new String[]{"ENG 3"}, false, false),
            new DummyStudent("Carl", "carl.org", new String[]{"CSE 101"}, true, true)
        };

        List<Student> FilteredStudent = StudentFilter.filterStudents(studentData, StudentFilter.FilterType.FAVORITES, "");
        assertEquals(FilteredStudent.size(), 2);
        assertEquals(FilteredStudent.get(0).getName().equals("Alice"), true);
        assertEquals(FilteredStudent.get(1).getName().equals("Carl"), true);
    }

    @Test
    public void filterClassQuarter() {
        Student[] studentData = {
            new DummyStudent("Alice", "alice.com", new String[]{"2022+Spring+CSE+11"}, true, true),
            new DummyStudent("Bob", "bob.net", new String[]{"2022+Fall+ENG+3"}, false, true),
            new DummyStudent("Carl", "carl.org", new String[]{"2022+Winter+CSE+101"}, true, true)
        };

        List<Student> recentStudents = StudentFilter.filterStudents(studentData, StudentFilter.FilterType.CLASS_RECENT, "CLASS_RECENT?2022+Spring");
        assertEquals(recentStudents.size(), 1);
        assertEquals(recentStudents.get(0).getName().equals("Alice"), true);
    }

}