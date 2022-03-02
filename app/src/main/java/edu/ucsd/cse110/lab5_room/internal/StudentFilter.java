package edu.ucsd.cse110.lab5_room.internal;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.lab5_room.model.Student;

public class StudentFilter {
    public enum FilterType {
        FAVORITES,
        CLASS_SIZE,
        CLASS_RECENT
    }

    //@require valid f (as above), valid params has format of "FILTERMODE?DETAIL"
    //@ensure filter correct mode
    public static List<Student> filterStudents(Student[] students, FilterType f, String params) {
        List<Student> filteredList = new ArrayList<Student>();
        switch (f) {
            case FAVORITES: {
                //keep all students that are favourited
                for(int i = 0; i < students.length; i++) {
                    Student currStudent = students[i];
                    if(currStudent.isFavorite()) {
                        filteredList.add(currStudent);
                    }
                }
                break;
            }

            case CLASS_SIZE: {
                //params = "CLASS_SIZE?SMALL"
                //check class size
                //keep all the students that has the common class with user
                //that has certain class size

                String classSize = params.split("\\?")[1];
                for(int i = 0; i < students.length; i++) {
                    Student currStudent = students[i];
                    String[] currStudentClasses = currStudent.getClasses();
                    for(int j = 0; i < currStudentClasses.length; j++) {
                        if(currStudentClasses[i].contains(classSize)) {
                            filteredList.add(currStudent);
                            break;
                        }
                    }
                }
                break;
            }

            case CLASS_RECENT: {
                //params = "CLASS_RECENT?SP2022"
                //check when the class was taken.
                //only show entries which are from the quarter the user inputs
                String quarter = params.split("\\?")[1];
                for(int i = 0; i < students.length; i++) {
                    Student currStudent = students[i];
                    String[] currStudentClasses = currStudent.getClasses();
                    for(int j = 0; i < currStudentClasses.length; j++) {
                        if(currStudentClasses[i].contains(quarter)) {
                            filteredList.add(currStudent);
                            break;
                        }
                    }
                }
                break;
            }

            default:
                break;
        }
        
        return filteredList;
    }

}
