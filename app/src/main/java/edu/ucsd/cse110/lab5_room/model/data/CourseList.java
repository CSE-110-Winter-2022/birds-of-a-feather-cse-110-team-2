package edu.ucsd.cse110.lab5_room.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.model.Course;

public class CourseList implements Parcelable {
    Set<Integer> courseIds;

    public CourseList() {
        courseIds = new HashSet<>();
    }

    protected CourseList(Parcel in) {
        Integer[] ids = (Integer[]) in.readArray(Integer.TYPE.getClassLoader());
        courseIds = new HashSet<>(Arrays.asList(ids));
    }

    public boolean contains(Course c) {
        return courseIds.contains(c.getId());
    }

    public void add(Course c) {
        courseIds.add(c.getId());
    }

    public static final Creator<CourseList> CREATOR = new Creator<CourseList>() {
        @Override
        public CourseList createFromParcel(Parcel in) {
            return new CourseList(in);
        }

        @Override
        public CourseList[] newArray(int size) {
            return new CourseList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeArray(courseIds.toArray(new Integer[0]));
    }
}
