package edu.ucsd.cse110.lab5_room.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"department", "number", "quarter", "year"}, unique = true)})
public class Course implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "department")
    private final Department department;

    @ColumnInfo(name = "number")
    private final int number;

    @ColumnInfo(name = "size")
    private final Size size;

    @ColumnInfo(name = "quarter")
    private final Quarter quarter;

    @ColumnInfo(name = "year")
    private final int year;

    public enum Size {
        TINY {
            public double weight() { return 1; }

            @NonNull
            public String toString() { return "Tiny (0-40)"; }
        },
        SMALL {
            public double weight() { return 0.33; }

            @NonNull
            public String toString() { return "Small (41-75)"; }
        },
        MEDIUM {
            public double weight() { return 0.18; }

            @NonNull
            public String toString() { return "Medium (76-150)"; }
        },
        LARGE {
            public double weight() { return 0.10; }

            @NonNull
            public String toString() { return "Large (151-250)"; }
        },
        HUGE {
            public double weight() { return 0.08; }

            @NonNull
            public String toString() { return "Huge (251-400)"; }
        },
        GIGANTIC {
            public double weight() { return 0.03; }

            @NonNull
            public String toString() { return "Gigantic (400+)"; }
        };

        public abstract double weight();
    }

    public enum Department {
        CSE {
            @NonNull
            public String toString() { return "CSE"; }
        },
        ECE {
            @NonNull
            public String toString() { return "ECE"; }
        }
    }

    public enum Quarter {
        FA {
            @NonNull
            public String toString() { return "Fall"; }
        },
        WI {
            @NonNull
            public String toString() { return "Winter"; }
        },
        SP {
            @NonNull
            public String toString() { return "Spring"; }
        },
        SS1 {
            @NonNull
            public String toString() { return "Summer Session 1"; }
        },
        SS2 {
            @NonNull
            public String toString() { return "Summer Session 2"; }
        }
    }

    public Course(int id, Department department, int number, Size size, Quarter quarter, int year) {
        this(department, number, size, quarter, year);
        this.id = id;
    }

    @Ignore
    public Course(Department department, int number, Size size, Quarter quarter, int year) {
        this.department = department;
        this.number = number;
        this.size = size;
        this.quarter = quarter;
        this.year = year;
    }

    // bullshit getter boilerplate
    public int getId() {
        return this.id;
    }

    public Size getSize() {
        return this.size;
    }

    public Department getDepartment() {
        return this.department;
    }

    public int getNumber() {
        return this.number;
    }

    public Quarter getQuarter() {
        return this.quarter;
    }

    public int getYear() {
        return this.year;
    }

//    public int getQuartersAgo() {
//        return this.quartersAgo;
//    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != Course.class)
            return false;

        // compare IDs if they exist, otherwise compare department, name, and time
        Course c = (Course) o;
        if ((c.id != -1) && (this.id != -1)) {
            return (c.id == this.id);
        }
        else {
            return (c.department == this.department) && (c.number == this.number) &&
                    (c.year == this.year) && (c.quarter == this.quarter);
        }
    }

    @Override
    public int hashCode() {
        return department.ordinal() * number * quarter.ordinal() * year * id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.department.toString() + ' ' + this.number;
    }

    public static int quartersAgo(Quarter quarter, int year) {
        // TODO quarters between
        return 0;
    }

    protected Course(Parcel in) {
        this.id = in.readInt();
        this.department = Department.valueOf(in.readString());
        this.number = in.readInt();
        this.size = Size.valueOf(in.readString());
        this.quarter = Quarter.valueOf(in.readString());
        this.year = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // this and the Parcelable constructor must be in exactly the same order :D
        parcel.writeInt(id);
        parcel.writeString(department.name());
        parcel.writeInt(number);
        parcel.writeString(size.name());
        parcel.writeString(quarter.name());
        parcel.writeInt(year);
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
