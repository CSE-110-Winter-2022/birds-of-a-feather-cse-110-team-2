package edu.ucsd.cse110.lab5_room.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Constants {
    private Constants() {}

    public static final String INTENT_LOGGED_IN = "logged_in";
    public static final Set<Character> CHARSET_ALPHA_LATIN = new HashSet<>(Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            ' ', '-', '\''
    ));
    public static final Set<Character> CHARSET_URL = new HashSet<>(Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', '.', '-',
            '/', ':', '?', '%', '&', '[', ']', '!', '$', '\'', '(', ')', '*',
            '+', ',', ';', '='
    ));

    // remove or add users to discover list
    public static final int ACTION_ADD = 0;
    public static final int ACTION_REMOVE = 1;

    public static final String USER_NAME    = "name";
    public static final String USER_PFP     = "profileURL";
    public static final String USER_COURSES = "courses";
    public static final String USER_ID      = "studentId";
}
