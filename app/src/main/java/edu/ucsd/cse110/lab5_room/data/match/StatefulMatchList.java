package edu.ucsd.cse110.lab5_room.data.match;

import android.content.Context;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.RosterEntry;
import edu.ucsd.cse110.lab5_room.model.Student;

public class StatefulMatchList {
    private Filter         filter = MatchFilter.DEFAULT;
    private Sorter<Course> sort   = MatchSorter.DEFAULT;

    private final ArrayList<RosterEntry> rosterEntries = new ArrayList<>();
    private final Map<Student, Set<Course>> classmates = new HashMap<>();
    private SortedSet<Student> sorted = new TreeSet<>(sort.getComparator());

    public static StatefulMatchList deserialize(Context c, byte[] serialized) {
        ArrayList<RosterEntry> roster = SerializationUtils.deserialize(serialized);
        return new StatefulMatchList(c, roster);
    }

    public StatefulMatchList(Context c, ArrayList<RosterEntry> rosterEntries) {
        // find out where in each array students belong
        for (RosterEntry m : rosterEntries)
            add(c, m);
    }

    public void setFilter(Filter f) {
        this.filter = f;
    }

    public void setSorter(Sorter<Course> s) {
        this.sort = s;
        if (this.sort.getComparator() != null) {
            this.sorted = new TreeSet<>(s.getComparator());
            this.sorted.addAll(this.classmates.keySet());
        }
    }

    public void favorite(Context c, Student s, boolean favorite) {
        Student s2 = new Student(
                s.getId(),
                s.getIsMe(),
                s.getName(),
                s.getPhotoURL(),
                favorite,
                s.getWaveReceived()
        );

        Set<Course> courses = this.classmates.get(s);
        this.classmates.remove(s);
        this.classmates.put(s2, courses);
    }

    public void add(Context c, RosterEntry m) {
        Student classmate = m.getClassmate(c);
        Course  course    = m.getCourse(c);

        rosterEntries.add(m);

        if (this.classmates.containsKey(classmate)) {
            Set<Course> courses = this.classmates.get(classmate);
            courses.add(course);
        }
        else {
            this.classmates.put(classmate, Collections.singleton(course));
        }

        try {
            this.sort.getComparator().compute(classmate, course);
            this.sorted.add(classmate);
        }
        catch (NullPointerException ignored) {}
    }

    public Student[] toArray() {
        // get array, sorted or unsorted
        List<Student> arr;
        if (this.sort.getComparator() == null) {
            arr = new ArrayList<>(this.classmates.keySet());
        }
        else {
            arr = new ArrayList<>(this.sorted);
        }

        // now filter array if necessary
        if (this.filter.getFilter() == null) {
            return arr.toArray(new Student[0]);
        }
        else {
            List<Student> filteredStudents = new ArrayList<>();
            for (Student s : arr) {
                for (Course c : classmates.get(s)) {
                    Filter.FilterProvider f = filter.getFilter();
                    if (f.filter(s, c))
                        filteredStudents.add(s);
                }
            }

            return filteredStudents.toArray(new Student[0]);
        }
    }

    public byte[] serialize() {
        return SerializationUtils.serialize(this.rosterEntries);
    }

    @Override @NonNull
    public String toString() {
        return Arrays.deepToString(classmates.keySet().toArray(new Student[0]));
    }

}
