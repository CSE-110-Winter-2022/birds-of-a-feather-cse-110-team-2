package edu.ucsd.cse110.lab5_room.data.match;

public interface Sorter<T> {
    ScoreComparator<T, ?> getComparator();
}
