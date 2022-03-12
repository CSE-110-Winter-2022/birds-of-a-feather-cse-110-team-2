package edu.ucsd.cse110.lab5_room;


import org.junit.Test;
import static org.junit.Assert.*;

import edu.ucsd.cse110.lab5_room.model.Course;

public class QuarterBetweenTest {

    @Test
    public void QuarterDifferenceIsCorrect()
    {
        assertEquals(Course.quartersAgo(Course.Quarter.SPRING, 2021), 3);
        assertEquals(Course.quartersAgo(Course.Quarter.WINTER, 2022), 0);
    }

}
