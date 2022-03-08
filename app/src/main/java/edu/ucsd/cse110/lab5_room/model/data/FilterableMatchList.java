package edu.ucsd.cse110.lab5_room.model.data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.RosterEntry;
import edu.ucsd.cse110.lab5_room.model.Student;

public class FilterableMatchList {
    public enum SortType {
        DEFAULT {
            @NonNull
            @Override
            public String toString() {
                return "Filter";
            }
        },
        FAVORITES {
            @NonNull
            @Override
            public String toString() {
                return "Favorites Only";
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

    private final List<RosterEntry> rosterEntries;
    private final Set<Student> classmates;
    private final Set<Student> favorites;
    private SortedSet<Student> sizeSort;
    private SortedSet<Student> timeSort;

    private final Map<Student, Double>  sizeScores;
    private final Map<Student, Integer> timeScores;

    public FilterableMatchList(Context c, List<RosterEntry> rosterEntries) {
        this.rosterEntries = rosterEntries;
        this.classmates = new LinkedHashSet<>();
        this.favorites  = new LinkedHashSet<>();
        this.sizeScores = new HashMap<>();
        this.timeScores = new HashMap<>();
        init(c);
    }

    private void init(Context c) {
        this.sizeSort = new TreeSet<>((Student m0, Student m1) -> {
            double s0 = this.sizeScores.get(m0);
            double s1 = this.sizeScores.get(m1);

            if (s0 == s1) return 0;
            return (s0 < s1) ? 1 : -1;
        });

        this.timeSort = new TreeSet<>((Student m0, Student m1) -> {
            int s0 = this.timeScores.get(m0);
            int s1 = this.timeScores.get(m1);

            if (s0 == s1) return 0;
            return (s0 > s1) ? 1 : -1;
        });

        // find out where in each array students belong
        for (RosterEntry m : rosterEntries)
            add(c, m);
    }

    public void add(Context c, RosterEntry m) {
        Student classmate = m.getClassmate(c);
        Course  course    = m.getCourse(c);

        int timeWeight = 5 - Course.quartersAgo(course.getQuarter(), course.getYear());
        if (timeWeight < 1)  timeWeight = 1;

        computeScores(classmate, course.getSize().weight(), timeWeight);
        addStudent(classmate);
    }

    private void addStudent(Student classmate) {
        this.classmates.add(classmate);
        this.sizeSort.add(classmate);
        this.timeSort.add(classmate);

        if (classmate.getFavorite())
            this.favorites.add(classmate);
    }

    private void computeScores(Student student, double sizeWeight, int timeWeight) {
        if (this.sizeScores.containsKey(student)) {
            double sizeScore = this.sizeScores.get(student);
            int    timeScore = this.timeScores.get(student);

            this.sizeScores.put(student, sizeScore + sizeWeight);
            this.timeScores.put(student, timeScore + timeWeight);
        }
        else {
            this.sizeScores.put(student, sizeWeight);
            this.timeScores.put(student, timeWeight);
        }
    }

    public Student[] sort(SortType sort) {
        switch (sort) {
            case DEFAULT:
                return this.classmates.toArray(new Student[0]);

            case FAVORITES:
                return this.favorites.toArray(new Student[0]);

            case CLASS_SIZE:
                return this.sizeSort.toArray(new Student[0]);

            case CLASS_RECENT:
                return this.timeSort.toArray(new Student[0]);
        }

        return null;
    }
}
