package edu.ucsd.cse110.lab5_room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;

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
            public String toString() { return "Tiny"; }
        },
        SMALL {
            public double weight() { return 0.33; }

            @NonNull
            public String toString() { return "Small"; }
        },
        MEDIUM {
            public double weight() { return 0.18; }

            @NonNull
            public String toString() { return "Medium"; }
        },
        LARGE {
            public double weight() { return 0.10; }

            @NonNull
            public String toString() { return "Large"; }
        },
        HUGE {
            public double weight() { return 0.08; }

            @NonNull
            public String toString() { return "Huge"; }
        },
        GIGANTIC {
            public double weight() { return 0.03; }

            @NonNull
            public String toString() { return "Gigantic"; }
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
        FALL {
            @NonNull
            public String toString() { return "Fall"; }
        },
        WINTER {
            @NonNull
            public String toString() { return "Winter"; }
        },
        SPRING {
            @NonNull
            public String toString() { return "Spring"; }
        },
        SUMMER1 {
            @NonNull
            public String toString() { return "Summer Session 1"; }
        },
        SUMMER2 {
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

    // TODO add comparison
    @NonNull
    @Override
    public String toString() {
        return this.department.toString() + ' ' + this.number;
    }

    public static int quartersAgo(Quarter quarter, int year) {
        // TODO quarters between
        return 0;
    }
}
