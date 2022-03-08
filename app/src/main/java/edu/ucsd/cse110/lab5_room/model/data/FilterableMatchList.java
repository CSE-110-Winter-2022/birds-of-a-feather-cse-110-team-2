package edu.ucsd.cse110.lab5_room.model.data;

import android.content.Context;

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
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.model.db.RosterDao;

public class FilterableMatchList {
    public enum SortType {
        DEFAULT,
        FAVORITES,
        CLASS_SIZE,
        CLASS_RECENT
    }

    private final List<RosterEntry> rosterEntries;
    private Set<Student> classmates;
    private Set<Student> favorites;
    private SortedSet<Student> sizeSort;
    private SortedSet<Student> timeSort;

    private Map<Student, Double>  sizeScores;
    private Map<Student, Integer> timeScores;

    public FilterableMatchList(Context c, List<RosterEntry> rosterEntries) {
        this.rosterEntries = rosterEntries;
        init(c);
    }

//    public FilterableMatchList(Context c, long date) {
//        RosterDao rosterDao = AppDatabase.singleton(c).rosterDao();
//        this.rosterEntries = rosterDao.matchesFrom(date);
//        init(c);
//    }

    private void init(Context c) {
        this.classmates = new LinkedHashSet<>();
        this.favorites  = new LinkedHashSet<>();

        this.sizeScores = new HashMap<>();
        this.sizeSort = new TreeSet<>((Student m0, Student m1) -> {
            double s0 = this.sizeScores.get(m0);
            double s1 = this.sizeScores.get(m1);

            if (s0 == s1) return 0;
            return (s0 > s1) ? 1 : -1;
        });

        this.timeScores = new HashMap<>();
        this.timeSort = new TreeSet<>((Student m0, Student m1) -> {
            int s0 = this.timeScores.get(m0);
            int s1 = this.timeScores.get(m1);

            if (s0 == s1) return 0;
            return (s0 > s1) ? 1 : -1;
        });

        // find out where in each array students belong
        for (RosterEntry m : rosterEntries)
            add(c, m);

        // now add all students
        this.sizeSort.addAll(this.classmates);
        this.timeSort.addAll(this.classmates);
    }

    public void add(Context c, RosterEntry m) {
        // TODO add students to favorites if match is a favorite
        Student classmate = m.getClassmate(c);
        Course  course    = m.getCourse(c);

        this.classmates.add(classmate);

        int timeWeight = 5 - Course.quartersAgo(course.getQuarter(), course.getYear());
        if (timeWeight < 1)  timeWeight = 1;

        addMatch(classmate, course.getSize().weight(), timeWeight);
    }

    private void addMatch(Student student, double sizeWeight, int timeWeight) {
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
