package edu.ucsd.cse110.lab5_room.nearby;

import android.content.Context;

import com.google.android.gms.nearby.messages.Message;

import org.apache.commons.lang3.text.WordUtils;

import java.io.StringWriter;
import java.util.Set;

import de.siegmar.fastcsv.writer.CsvWriter;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.Student;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;

public class NearbyBroadcastComposer {
    private static final CsvWriter.CsvWriterBuilder builder = CsvWriter.builder();

    public static Message broadcast(Context c) {
        return broadcast(c, false, null);
    }

    public static Message broadcast(Context c, boolean wave, Student recipient) {
        StringWriter sw = new StringWriter();
        CsvWriter csvWriter = builder.build(sw);

        AppDatabase db = AppDatabase.singleton(c);
        Student me = db.studentDao().getMe();

        csvWriter.writeRow(me.getId().toString(), "", "", "", "");
        csvWriter.writeRow(me.getName(), "", "", "", "");
        csvWriter.writeRow(me.getPhotoURL(), "", "", "", "");

        Set<Course> myCourses = db.courseDao().getMyCourses(c);
        for (Course course : myCourses) {
            String year = Integer.toString(course.getYear());
            String num  = Integer.toString(course.getNumber());

            csvWriter.writeRow(
                    year,
                    course.getQuarter().toString(),
                    course.getDepartment().toString(),
                    num,
                    WordUtils.capitalizeFully(course.getSize().toString())
            );
        }

        if (wave) {
            csvWriter.writeRow(
                recipient.getId().toString(),
                NearbyMessageHandler.MSG_WAVE,
                "", "", ""
            );
        }

        return new Message(sw.toString().getBytes());
    }
}
