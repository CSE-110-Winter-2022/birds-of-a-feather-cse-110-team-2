package edu.ucsd.cse110.lab5_room.data;

import android.content.Context;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.ucsd.cse110.lab5_room.Constants;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.RosterEntry;
import edu.ucsd.cse110.lab5_room.model.Student;

public class FilterableMatchList {
    public enum SortType {
        DEFAULT {
            @NonNull
            @Override
            public String toString() {
                return "Sort";
            }
        },
        CLASS_SIZE {
            @NonNull
            @Override
            public String toString() {
                return "Smallest First";
            }
        },
        CLASS_RECENT {
            @NonNull
            @Override
            public String toString() {
                return "Recent First";
            }
        }
    }

    public enum StandardFilter {
        NONE("Default", (s, c) -> true),
        CURRENT("This quarter only", (s, c) ->
            (c.getYear() == Constants.CURR_YEAR) && (c.getQuarter() == Constants.CURR_QUARTER)
        ),
        FAVORITES("Favorites only", (s, c) -> s.getFavorite());

        public final String name;
        public final Filter filter;
        StandardFilter(String name, Filter f) {
            this.name   = name;
            this.filter = f;
        }

        @NonNull @Override
        public String toString() {
            return this.name;
        }
    }

    public interface Filter {
        boolean filter(Student s, Course c);
    }

    private final ArrayList<RosterEntry> rosterEntries;
    private final Map<Student, Set<Course>> classmates;

    private final SizeComparator sizeComparator = new SizeComparator();
    private final SortedSet<Student> sizeSort   = new TreeSet<>(sizeComparator);

    private final TimeComparator timeComparator = new TimeComparator();
    private final SortedSet<Student> timeSort   = new TreeSet<>(timeComparator);

    public static FilterableMatchList deserialize(Context c, byte[] serialized) {
        ArrayList<RosterEntry> roster = SerializationUtils.deserialize(serialized);
        return new FilterableMatchList(c, roster);
    }

    public FilterableMatchList(Context c, ArrayList<RosterEntry> rosterEntries) {
        this.rosterEntries = new ArrayList<>();
        this.classmates    = new HashMap<>();

        // find out where in each array students belong
        for (RosterEntry m : rosterEntries)
            add(c, m);
    }

    public void add(Context c, RosterEntry m) {
        Student classmate = m.getClassmate(c);
        Course  course    = m.getCourse(c);

        rosterEntries.add(m);
        sizeComparator.compute(classmate, course);
        timeComparator.compute(classmate, course);

        if (this.classmates.containsKey(classmate)) {
            Set<Course> courses = this.classmates.get(classmate);
            courses.add(course);
        }
        else {
            this.classmates.put(classmate, Collections.singleton(course));
        }
        this.sizeSort.add(classmate);
        this.timeSort.add(classmate);
    }

    public Student[] sort(SortType sort) {
        switch (sort) {
            case DEFAULT:
                return this.classmates.keySet().toArray(new Student[0]);

            case CLASS_SIZE:
                return this.sizeSort.toArray(new Student[0]);

            case CLASS_RECENT:
                return this.timeSort.toArray(new Student[0]);
        }

        return null;
    }

    public Student[] generate(SortType sort, Filter filter) {
        Student[] sorted = sort(sort);

        List<Student> filteredStudents = new ArrayList<>();
        for (Student s : sorted) {
            for (Course c : classmates.get(s)) {
                if (filter.filter(s, c))
                    filteredStudents.add(s);
            }
        }

        return filteredStudents.toArray(new Student[0]);
    }

    public byte[] serialize() {
        return SerializationUtils.serialize(this.rosterEntries);
    }

    @Override @NonNull
    public String toString() {
        return Arrays.deepToString(classmates.keySet().toArray(new Student[0]));
    }

    private static class TimeComparator extends ScoreComparator<Course, Integer> {
        @Override
        public Integer getScore(Course course) {
            int timeWeight = 5 - Course.quartersAgo(course.getQuarter(), course.getYear());
            if (timeWeight < 1)  timeWeight = 1;

            return timeWeight;
        }

        @Override
        protected Integer combine(Integer score0, Integer score1) {
            return (score0 + score1);
        }

        @Override
        public boolean ordering(Integer score0, Integer score1) {
            return (score0 < score1);
        }
    }

    private static class SizeComparator extends ScoreComparator<Course, Double> {
        @Override
        public Double getScore(Course course) {
            return course.getSize().weight();
        }

        @Override
        protected Double combine(Double score0, Double score1) {
            return (score0 + score1);
        }

        @Override
        public boolean ordering(Double score0, Double score1) {
            return (score0 > score1);
        }
    }

    // score calculation should be a function of T and should return a value of type S
    // tells us how to rank our SortedSets
    private static abstract class ScoreComparator<T, S extends Number>
                        implements Comparator<Student>, Serializable {
        private final Map<Student, S> scores = new HashMap<>();

        public void compute(Student student, T source) {
            S score = getScore(source);

            if (this.scores.containsKey(student)) {
                S oldScore = this.scores.get(student);
                this.scores.put(student, combine(oldScore, score));
            }
            else {
                this.scores.put(student, score);
            }
        }

        // return true if score0 should come first, or false if score1 should come first
        public abstract boolean ordering(S score0, S score1);
        protected abstract S getScore(T source);
        protected abstract S combine(S score0, S score1);

        @Override
        public int compare(Student s0, Student s1) {
            S score0 = this.scores.get(s0);
            S score1 = this.scores.get(s1);

            // should never return null
            if ((score0 == null) || (score1 == null) || score0.equals(score1))
                return 0;

            return ordering(score0, score1) ? 1 : -1;
        }
    }
}
