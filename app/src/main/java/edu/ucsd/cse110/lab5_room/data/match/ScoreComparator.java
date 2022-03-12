package edu.ucsd.cse110.lab5_room.data.match;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.lab5_room.model.Student;

public abstract class ScoreComparator<T, S extends Number>
        implements Comparator<Student> {
    private final Map<Student, S> scores = new HashMap<>();

    public void compute(Student student, T source) {
        S score = getScore(source);

        if (this.scores.containsKey(student)) {
            S oldScore = this.scores.get(student);
            this.scores.put(student, combine(oldScore, score));
        } else {
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
