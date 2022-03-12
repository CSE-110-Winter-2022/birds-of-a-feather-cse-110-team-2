package edu.ucsd.cse110.lab5_room.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab5_room.internal.Constants;

public class AutoSave extends Worker {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("M/d/uu K:ma");
    private static String last = null;

    private static final String KEY_LIST = "match-list";

    public static void register(Context c, FilterableMatchList list) {
        PeriodicWorkRequest autoSaveRequest = new PeriodicWorkRequest
                .Builder(AutoSave.class, Constants.MINS_AUTOSAVE, TimeUnit.MINUTES)
                .setInputData(
                        new Data.Builder()
                            .putByteArray(
                                KEY_LIST,
                                list.serialize()
                            )
                            .build()
                )
                .build();

        WorkManager.getInstance(c).enqueue(autoSaveRequest);
    }

    public static void deregister(Context c) {
        WorkManager.getInstance(c).cancelAllWork();
    }

    public static void deleteLast() {
        if (last != null) {
            sm.remove(last);
            last = null;
        }
    }

    private static SavedListManager sm;
    public AutoSave(Context context, WorkerParameters params) {
        super(context, params);

        if (sm == null)
            sm = SavedListManager.singleton(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        // get match list passed to worker
        Data inputData = getInputData();
        byte[] matchListBytes = inputData.getByteArray(KEY_LIST);
        FilterableMatchList list = FilterableMatchList.deserialize(
                getApplicationContext(),
                matchListBytes
        );

        // remove old autosave
        deleteLast();

        // add new autosave
        String newName = fmt.format(LocalDateTime.now());
        sm.save(newName, list);
        last = newName;

        return Result.success();
    }
}
