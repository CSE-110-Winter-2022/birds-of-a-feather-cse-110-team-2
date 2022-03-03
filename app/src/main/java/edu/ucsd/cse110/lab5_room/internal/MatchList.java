package edu.ucsd.cse110.lab5_room.internal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.MatchListView;
import edu.ucsd.cse110.lab5_room.model.Match;

public class MatchList {
    private List<Match> matches;
    private PriorityQueue<Match> courseSize;
    private PriorityQueue<Match> matchAge;

    // rank items by course size
    private Comparator<Match> courseSizeComp = (Match m0, Match m1) -> {
        Set<Course> c0 = m0.getClasses();
        Set<Course> c1 = m1.getClasses();

        double t0 = 0;
        double t1 = 0;

        for (Course c : c0) {
//            if (!myCourses.contains(c))
//                continue;

            t0 += c.getSize().weight();
        }

        for (Course c : c1) {
//            if (!myCourses.contains(c))
//                continue;

            t1 += c.getSize().weight();
        }

        if (t0 == t1) return 0;
        return (t0 > t1) ? 1 : -1;
    };

    // rank items by how many quarters ago class was taken
    private Comparator<Match> ageComp = (Match m0, Match m1) -> {
        Set<Course> c0 = m0.getClasses();
        Set<Course> c1 = m1.getClasses();

        int t0 = 0;
        int t1 = 0;

        for (Course c : c0) {
//            if (!myCourses.contains(c))
//                continue;

            int i0 = 5 - c.getQuartersAgo();
            if (i0 < 0)
                i0 = 1;

            t0 += i0;
        }

        for (Course c : c1) {
//            if (!myCourses.contains(c))
//                continue;

            int i1 = 5 - c.getQuartersAgo();
            if (i1 < 0)
                i1 = 1;

            t1 += i1;
        }

        if (t0 == t1) return 0;
        return (t0 > t1) ? 1 : -1;
    };

    public MatchList() {
        new MatchList(new ArrayList<>());
    }

    public MatchList(List<Match> matches) {
        this.matches = matches;

        this.courseSize = new PriorityQueue<>(this.matches.size(), courseSizeComp);
        this.courseSize.addAll(matches);

        this.matchAge = new PriorityQueue<>(this.matches.size(), ageComp);
        this.matchAge.addAll(matches);
    }

    public void add(Match m) {
        this.matches.add(m);
        this.courseSize.offer(m);
        this.matchAge.offer(m);
    }

    public Match get(int pos, MatchListView.SortType filter) {
        switch (filter) {
            case CLASS_SIZE:
                // TODO ew
                return this.courseSize.toArray(new Match[0])[pos];

            case CLASS_RECENT:
                return this.matchAge.toArray(new Match[0])[pos];

            default:
            case DEFAULT:
                return this.matches.get(pos);
        }
    }

    public int size() {
        return this.matches.size();
    }
}
