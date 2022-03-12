package edu.ucsd.cse110.lab5_room.data.match;

import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.Student;

public interface Filter {
    FilterProvider getFilter();

    interface FilterProvider {
        boolean filter(Student s, Course c);
    }
}