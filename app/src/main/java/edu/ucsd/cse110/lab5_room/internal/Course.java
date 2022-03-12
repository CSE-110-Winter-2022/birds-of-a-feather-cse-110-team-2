package edu.ucsd.cse110.lab5_room.internal;

import androidx.annotation.NonNull;

public class Course {
    private Department department;
    private int number;
    private Size size;
    int quartersAgo;

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

        abstract double weight();
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

    public Course(Department department, int number, Size size, int quartersAgo) {
        this.size = size;
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

    public int getQuartersAgo() {
        return this.quartersAgo;
    }

    // TODO add comparison
}
