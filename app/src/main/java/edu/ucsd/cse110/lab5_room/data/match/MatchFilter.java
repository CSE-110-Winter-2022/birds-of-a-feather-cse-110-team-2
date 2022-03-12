package edu.ucsd.cse110.lab5_room.data.match;

import androidx.annotation.NonNull;

import edu.ucsd.cse110.lab5_room.internal.Constants;

@SuppressWarnings("unused")
public enum MatchFilter implements Filter {
    DEFAULT("Default", null),
    CURRENT("This Quarter Only", (s, c) ->
            (c.getYear() == Constants.CURR_YEAR) && (c.getQuarter() == Constants.CURR_QUARTER)
    ),
    FAVORITES("Favorites Only", (s, c) -> s.getFavorite());

    public final String name;
    public final FilterProvider filter;

    MatchFilter(String name, FilterProvider f) {
        this.name = name;
        this.filter = f;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public FilterProvider getFilter() {
        return this.filter;
    }
}
