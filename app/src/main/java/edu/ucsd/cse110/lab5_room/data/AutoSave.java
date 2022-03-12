package edu.ucsd.cse110.lab5_room.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;

public class AutoSave extends Worker {
    private SavedListManager sm;
    public AutoSave(Context context, WorkerParameters params) {
        super(context, params);
        this.sm = SavedListManager.singleton(context);
    }
    @NonNull
    @Override
    public Result doWork() {
        Data inputData = getInputData();
        byte[] matchListBytes = inputData.getByteArray("match-list");
        FilterableMatchList matchListDeSerial = FilterableMatchList.deserialize(getApplicationContext(), matchListBytes);
        Calendar currDate = Calendar.getInstance();
        String currTime = currDate.get(Calendar.MONTH)+"/"+currDate.get(Calendar.DAY_OF_MONTH) +"/"+currDate.get(Calendar.YEAR) + " " + currDate.getTime();
        this.sm.save(currTime,matchListDeSerial);
        return Result.success();
    }
}
