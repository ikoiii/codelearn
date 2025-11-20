package com.codelearn.android.utils;

/**
 * Application constants
 */
public class Constants {

    // Bundle arguments
    public static final String ARG_COURSE_ID = "course_id";
    public static final String ARG_LESSON_ID = "lesson_id";
    public static final String ARG_USER_ID = "user_id";

    // Preferences
    public static final String PREF_NAME = "codelearn_prefs";
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_USER_NAME = "user_name";
    public static final String PREF_USER_EMAIL = "user_email";
    public static final String PREF_IS_LOGGED_IN = "is_logged_in";
    public static final String PREF_DARK_MODE = "dark_mode";

    // Database
    public static final String DATABASE_NAME = "codelearn_database";
    public static final int DATABASE_VERSION = 1;

    // API endpoints
    public static final String BASE_URL = "https://api.codelearn.com/";
    public static final String ENDPOINT_COURSES = "courses";
    public static final String ENDPOINT_LESSONS = "lessons";
    public static final String ENDPOINT_USERS = "users";
    public static final String ENDPOINT_PROGRESS = "progress";

    // Time constants
    public static final long ANIMATION_DURATION_SHORT = 200L;
    public static final long ANIMATION_DURATION_MEDIUM = 300L;
    public static final long ANIMATION_DURATION_LONG = 500L;

    // Pagination
    public static final int PAGE_SIZE = 20;
    public static final int FIRST_PAGE = 1;

    // Delay constants
    public static final int SPLASH_DELAY = 2000;
    public static final int SEARCH_DELAY = 300;
    public static final int NETWORK_TIMEOUT = 30000;

    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Constants class should not be instantiated");
    }
}