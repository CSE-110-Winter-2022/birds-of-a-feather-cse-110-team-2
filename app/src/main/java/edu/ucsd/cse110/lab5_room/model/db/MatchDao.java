package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.LocalDateTime;
import java.util.List;

import edu.ucsd.cse110.lab5_room.model.Match;

@Dao
public interface MatchDao {
    @Query("select * from `match`;")
    public List<Match> getAll();

    @Query("select * from `match` where saved_at=:dt")
    public List<Match> matchesFrom(long dt);

    @Insert
    public void insert(Match m);

    @Delete
    public void delete(Match m);
}
