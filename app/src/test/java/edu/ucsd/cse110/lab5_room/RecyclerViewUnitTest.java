package edu.ucsd.cse110.lab5_room;

import org.junit.Test;

import static org.junit.Assert.*;

import edu.ucsd.cse110.lab5_room.model.DummyStudent;
import edu.ucsd.cse110.lab5_room.model.Student;

public class RecyclerViewUnitTest {

    @Test
    public void ItemCountIsCorrect() {
        Student[] studentData = {
                new DummyStudent("Alice", "alice.com", new String[]{"CSE 11"}, true),
                new DummyStudent("Bob", "bob.net", new String[]{"ENG 3"}, false),
                new DummyStudent("Carl", "carl.org", new String[]{"CSE 101"}, true)
        };
        PersonsViewAdapter adapter = new PersonsViewAdapter(studentData);
        assertEquals(adapter.getItemCount(), 3);
    }


}
