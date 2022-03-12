package edu.ucsd.cse110.lab5_room.data.match;

import androidx.annotation.NonNull;

import edu.ucsd.cse110.lab5_room.model.Course;

@SuppressWarnings("unused")
public enum MatchSorter implements Sorter<Course> {
    DEFAULT("Sort", null),

    CLASS_SIZE("Smallest First",
            new ScoreComparator<Course, Double>() {
                @Override
                public boolean ordering(Double score0, Double score1) {
                    return (score0 > score1);
                }

                @Override
                protected Double getScore(Course course) {
                    return course.getSize().weight();
                }

                @Override
                protected Double combine(Double score0, Double score1) {
                    return (score0 + score1);
                }
            }),

    CLASS_RECENT("Recent First",
            new ScoreComparator<Course, Integer>() {
                @Override
                public boolean ordering(Integer score0, Integer score1) {
                    return (score0 < score1);
                }

                @Override
                protected Integer getScore(Course course) {
                    int timeWeight = 5 - Course.quartersAgo(course.getQuarter(), course.getYear());
                    if (timeWeight < 1)  timeWeight = 1;

                    return timeWeight;
                }

                @Override
                protected Integer combine(Integer score0, Integer score1) {
                    return (score0 + score1);
                }
            });

    private final String name;
    private final ScoreComparator<Course, ?> cmp;
    MatchSorter(String name, ScoreComparator<Course, ?> cmp) {
        this.name = name;
        this.cmp  = cmp;
    }

    @Override @NonNull
    public String toString() {
        return this.name;
    }

    @Override
    public ScoreComparator<Course, ?> getComparator() {
        return this.cmp;
    }
}