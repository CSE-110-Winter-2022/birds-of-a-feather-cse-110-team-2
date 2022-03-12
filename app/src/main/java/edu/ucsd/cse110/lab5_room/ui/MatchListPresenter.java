package edu.ucsd.cse110.lab5_room.ui;

import android.util.ArraySet;
import android.util.Log;

import java.util.Set;

import edu.ucsd.cse110.lab5_room.data.match.Filter;
import edu.ucsd.cse110.lab5_room.data.match.MatchListObserver;
import edu.ucsd.cse110.lab5_room.data.match.Sorter;
import edu.ucsd.cse110.lab5_room.data.match.StatefulMatchList;
import edu.ucsd.cse110.lab5_room.model.Course;

public class MatchListPresenter {
    private StatefulMatchList list;
    private final Set<MatchListObserver> obs = new ArraySet<>();
    public MatchListPresenter(StatefulMatchList list) {
        setList(list);
    }

    public void register(MatchListObserver o) {
        this.obs.add(o);
    }

    public void setList(StatefulMatchList list) {
        Log.d("TAG", "SETLIST");
        this.list = list;
        notifyObservers();
    }

    public void setFilter(Filter filter) {
        this.list.setFilter(filter);
        notifyObservers();
    }

    public void setSorter(Sorter<Course> sorter) {
        this.list.setSorter(sorter);
        notifyObservers();
    }

    public void notifyObservers() {
        for (MatchListObserver o : this.obs)
            o.update(this.list);
    }
}
