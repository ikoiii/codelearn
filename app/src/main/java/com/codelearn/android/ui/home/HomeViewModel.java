package com.codelearn.android.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codelearn.android.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for HomeFragment
 * Manages home screen data and business logic
 */
public class HomeViewModel extends ViewModel {

    private final MutableLiveData<UserData> userData = new MutableLiveData<>();
    private final MutableLiveData<List<Course>> recentCourses = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
    private final MutableLiveData<Object> errorState = new MutableLiveData<>();

    public HomeViewModel() {
        // Initialize with loading state
        loadingState.setValue(false);
    }

    // ==================== GETTERS ====================

    public LiveData<UserData> getUserData() {
        return userData;
    }

    public LiveData<List<Course>> getRecentCourses() {
        return recentCourses;
    }

    public LiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    public LiveData<Object> getErrorState() {
        return errorState;
    }

    // ==================== DATA OPERATIONS ====================

    /**
     * Load user data
     */
    public void loadUserData() {
        setLoading(true);

        // Simulate data loading
        // In real implementation, this would call repository
        new Thread(() -> {
            try {
                Thread.sleep(800); // Simulate network delay

                // Mock data
                UserData mockData = new UserData(
                    "John Doe",                    // userName
                    3,                            // enrolledCourses
                    12,                           // completedLessons
                    5,                            // learningStreak
                    240                           // totalHours (in minutes)
                );

                // Post result on main thread
                userData.postValue(mockData);
                setLoading(false);

            } catch (InterruptedException e) {
                errorState.postValue(e);
                setLoading(false);
            }
        }).start();
    }

    /**
     * Load recent courses
     */
    public void loadRecentCourses() {
        new Thread(() -> {
            try {
                Thread.sleep(600); // Simulate network delay

                // Mock recent courses data
                List<Course> courses = createMockRecentCourses();

                // Post result on main thread
                recentCourses.postValue(courses);

            } catch (InterruptedException e) {
                errorState.postValue(e);
            }
        }).start();
    }

    /**
     * Load initial data for home screen
     */
    public void loadInitialData() {
        loadUserData();
        loadRecentCourses();
    }

    /**
     * Refresh all data
     */
    public void refreshData() {
        loadUserData();
        loadRecentCourses();
    }

    // ==================== PRIVATE HELPERS ====================

    private void setLoading(boolean isLoading) {
        // Track multiple loading operations
        if (isLoading) {
            loadingState.postValue(true);
        } else {
            // Only set loading to false when all operations are complete
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Wait for all operations
                    loadingState.postValue(false);
                } catch (InterruptedException e) {
                    errorState.postValue(e);
                }
            }).start();
        }
    }

    /**
     * Create mock recent courses data
     */
    private List<Course> createMockRecentCourses() {
        List<Course> courses = new ArrayList<>();

        Course htmlCourse = new Course(
                "HTML Fundamentals",
                "Learn the basics of HTML including tags, attributes, and semantic markup.",
                "HTML",
                1,
                300,
                "html_fundamentals"
        );
        htmlCourse.setId(1);

        Course cssCourse = new Course(
                "CSS Styling",
                "Master CSS for beautiful web design including layouts, animations, and responsive design.",
                "CSS",
                2,
                450,
                "css_styling"
        );
        cssCourse.setId(2);

        Course jsCourse = new Course(
                "JavaScript Programming",
                "Learn JavaScript from basics to advanced concepts including DOM manipulation and async programming.",
                "JavaScript",
                3,
                600,
                "javascript_programming"
        );
        jsCourse.setId(3);

        courses.add(htmlCourse);
        courses.add(cssCourse);
        courses.add(jsCourse);

        return courses;
    }

    
    // ==================== USER DATA MODEL ====================

    /**
     * Data class for user information displayed on home screen
     */
    public static class UserData {
        private final String userName;
        private final int enrolledCourses;
        private final int completedLessons;
        private final int learningStreak;
        private final long totalHours; // in minutes

        public UserData(String userName, int enrolledCourses, int completedLessons,
                        int learningStreak, long totalHours) {
            this.userName = userName;
            this.enrolledCourses = enrolledCourses;
            this.completedLessons = completedLessons;
            this.learningStreak = learningStreak;
            this.totalHours = totalHours;
        }

        public String getUserName() {
            return userName;
        }

        public int getEnrolledCourses() {
            return enrolledCourses;
        }

        public int getCompletedLessons() {
            return completedLessons;
        }

        public int getLearningStreak() {
            return learningStreak;
        }

        public long getTotalHours() {
            return totalHours;
        }
    }
}