package com.codelearn.android.ui.courses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codelearn.android.model.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ViewModel for CourseListFragment
 * Manages course list data, filtering, sorting, and search functionality
 */
public class CourseListViewModel extends ViewModel {

    private final MutableLiveData<List<Course>> courses = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
    private final MutableLiveData<Object> errorState = new MutableLiveData<>();
    private final MutableLiveData<FilterType> currentFilter = new MutableLiveData<>();
    private final MutableLiveData<SortType> currentSort = new MutableLiveData<>();

    private List<Course> allCourses = new ArrayList<>();
    private List<Course> filteredCourses = new ArrayList<>();

    public CourseListViewModel() {
        // Initialize with default states
        loadingState.setValue(false);
        currentFilter.setValue(FilterType.ALL);
        currentSort.setValue(SortType.TITLE);
    }

    // ==================== GETTERS ====================

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public LiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    public LiveData<Object> getErrorState() {
        return errorState;
    }

    public LiveData<FilterType> getCurrentFilter() {
        return currentFilter;
    }

    public LiveData<SortType> getCurrentSort() {
        return currentSort;
    }

    // ==================== ENUMS ====================

    public enum FilterType {
        ALL, HTML, CSS, JAVASCRIPT
    }

    public enum SortType {
        TITLE, DIFFICULTY, POPULAR
    }

    // ==================== DATA OPERATIONS ====================

    /**
     * Load courses from repository
     */
    public void loadCourses() {
        setLoading(true);

        // Simulate data loading
        // In real implementation, this would call repository
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate network delay

                // Mock courses data
                List<Course> mockCourses = createMockCourses();
                allCourses.clear();
                allCourses.addAll(mockCourses);

                // Apply current filter and sort
                applyFilterAndSort();

                setLoading(false);

            } catch (InterruptedException e) {
                errorState.postValue(e);
                setLoading(false);
            }
        }).start();
    }

    /**
     * Refresh courses
     */
    public void refreshCourses() {
        loadCourses();
    }

    /**
     * Set filter type
     */
    public void setFilter(FilterType filterType) {
        currentFilter.setValue(filterType);
        applyFilterAndSort();
    }

    /**
     * Set sort type
     */
    public void setSort(SortType sortType) {
        currentSort.setValue(sortType);
        applyFilterAndSort();
    }

    /**
     * Search courses by query
     */
    public void searchCourses(String query) {
        setLoading(true);

        new Thread(() -> {
            try {
                Thread.sleep(500); // Simulate search delay

                List<Course> searchResults = new ArrayList<>();

                if (query == null || query.trim().isEmpty()) {
                    searchResults.addAll(filteredCourses);
                } else {
                    String searchQuery = query.toLowerCase().trim();
                    for (Course course : filteredCourses) {
                        if (course.getTitle().toLowerCase().contains(searchQuery) ||
                                course.getDescription().toLowerCase().contains(searchQuery)) {
                            searchResults.add(course);
                        }
                    }
                }

                courses.postValue(searchResults);
                setLoading(false);

            } catch (InterruptedException e) {
                errorState.postValue(e);
                setLoading(false);
            }
        }).start();
    }

    /**
     * Clear search
     */
    public void clearSearch() {
        courses.postValue(new ArrayList<>(filteredCourses));
    }

    // ==================== PRIVATE HELPERS ====================

    private void setLoading(boolean isLoading) {
        loadingState.postValue(isLoading);
    }

    /**
     * Apply current filter and sort to courses
     */
    private void applyFilterAndSort() {
        filteredCourses.clear();

        // Apply filter
        FilterType filter = currentFilter.getValue();
        for (Course course : allCourses) {
            if (filter == FilterType.ALL ||
                (filter == FilterType.HTML && "HTML".equals(course.getCategory())) ||
                (filter == FilterType.CSS && "CSS".equals(course.getCategory())) ||
                (filter == FilterType.JAVASCRIPT && "JavaScript".equals(course.getCategory()))) {
                filteredCourses.add(course);
            }
        }

        // Apply sort
        SortType sort = currentSort.getValue();
        sortCourses(sort);

        // Update LiveData
        courses.postValue(new ArrayList<>(filteredCourses));
    }

    /**
     * Sort courses based on current sort type
     */
    private void sortCourses(SortType sortType) {
        Comparator<Course> comparator;

        switch (sortType) {
            case TITLE:
                comparator = Comparator.comparing(Course::getTitle);
                break;
            case DIFFICULTY:
                comparator = Comparator.comparingInt(Course::getDifficulty);
                break;
            case POPULAR:
                // Mock popularity - in real implementation this would come from user data
                comparator = Comparator.comparingInt(Course::getId); // Reverse ID for mock popularity
                break;
            default:
                comparator = Comparator.comparing(Course::getTitle);
                break;
        }

        Collections.sort(filteredCourses, comparator);
    }

    /**
     * Create mock courses data
     */
    private List<Course> createMockCourses() {
        List<Course> courses = new ArrayList<>();

        // HTML Courses
        Course htmlBasics = new Course(
                "Pengenalan HTML",
                "Pelajari dasar-dasar HTML mulai dari tag, atribut, hingga struktur dokumen HTML untuk pemula.",
                "HTML",
                1,
                180,
                "html_basics"
        );
        htmlBasics.setId(1);

        // Set offline content untuk HTML Pengenalan
        List<String> htmlBasicsFiles = new ArrayList<>();
        htmlBasicsFiles.add("01_pengenalan.html");
        htmlBasics.setLessonFiles(htmlBasicsFiles);
        htmlBasics.setHasOfflineContent(true);
        htmlBasics.setFirstLessonAsset("01_pengenalan.html");

        Course htmlForms = new Course(
                "Form dan Input HTML",
                "Kuasai pembuatan form dan berbagai jenis input untuk interaksi pengguna di website.",
                "HTML",
                2,
                240,
                "html_forms"
        );
        htmlForms.setId(2);

        // Set offline content untuk HTML Forms
        List<String> htmlFormsFiles = new ArrayList<>();
        htmlFormsFiles.add("02_form_dan_input.html");
        htmlForms.setLessonFiles(htmlFormsFiles);
        htmlForms.setHasOfflineContent(true);
        htmlForms.setFirstLessonAsset("02_form_dan_input.html");

        // CSS Courses
        Course cssBasics = new Course(
                "Pengenalan CSS",
                "Pelajari dasar-dasar CSS untuk styling website, termasuk selectors, properties, dan layout.",
                "CSS",
                2,
                300,
                "css_fundamentals"
        );
        cssBasics.setId(3);

        // Set offline content untuk CSS Pengenalan
        List<String> cssBasicsFiles = new ArrayList<>();
        cssBasicsFiles.add("01_pengenalan_css.html");
        cssBasics.setLessonFiles(cssBasicsFiles);
        cssBasics.setHasOfflineContent(true);
        cssBasics.setFirstLessonAsset("01_pengenalan_css.html");

        // JavaScript Courses
        Course jsBasics = new Course(
                "Pengenalan JavaScript",
                "Pelajari fundamental JavaScript untuk membuat website interaktif dan dinamis.",
                "JavaScript",
                3,
                420,
                "javascript_fundamentals"
        );
        jsBasics.setId(4);

        // Set offline content untuk JavaScript Pengenalan
        List<String> jsBasicsFiles = new ArrayList<>();
        jsBasicsFiles.add("01_pengenalan_javascript.html");
        jsBasics.setLessonFiles(jsBasicsFiles);
        jsBasics.setHasOfflineContent(true);
        jsBasics.setFirstLessonAsset("01_pengenalan_javascript.html");

        courses.add(htmlBasics);
        courses.add(htmlForms);
        courses.add(cssBasics);
        courses.add(jsBasics);

        return courses;
    }
}