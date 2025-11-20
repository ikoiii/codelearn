package com.codelearn.android.ui.courses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codelearn.android.model.Course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ViewModel for CourseDetailFragment
 * Manages course details, learning outcomes, requirements, and user interactions
 */
public class CourseDetailViewModel extends ViewModel {

    // Course data
    private final MutableLiveData<Course> course = new MutableLiveData<>();
    private final MutableLiveData<List<String>> learningOutcomes = new MutableLiveData<>();
    private final MutableLiveData<List<String>> requirements = new MutableLiveData<>();

    // User interaction states
    private final MutableLiveData<Boolean> isEnrolled = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isInWishlist = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Course metadata
    private int studentCount;
    private String instructorName = "John Doe";
    private String instructorTitle = "Senior Web Developer";
    private String instructorImage = "";
    private boolean isFree = true;
    private double price = 0.0;
    private double originalPrice = 29.99;

    public CourseDetailViewModel() {
        // Initialize default states
        isLoading.setValue(false);
        isEnrolled.setValue(false);
        isInWishlist.setValue(false);
    }

    // ==================== GETTERS ====================

    public LiveData<Course> getCourse() {
        return course;
    }

    public LiveData<List<String>> getLearningOutcomes() {
        return learningOutcomes;
    }

    public LiveData<List<String>> getRequirements() {
        return requirements;
    }

    public LiveData<Boolean> getIsEnrolled() {
        return isEnrolled;
    }

    public LiveData<Boolean> getIsInWishlist() {
        return isInWishlist;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // ==================== DATA OPERATIONS ====================

    /**
     * Load course details including mock data
     */
    public void loadCourseDetails(int courseId) {
        setLoading(true);

        // Simulate data loading
        new Thread(() -> {
            try {
                Thread.sleep(500); // Simulate network delay

                // Mock course data
                Course mockCourse = createMockCourse(courseId);
                course.postValue(mockCourse);

                // Mock learning outcomes
                List<String> mockOutcomes = createMockLearningOutcomes(courseId);
                learningOutcomes.postValue(mockOutcomes);

                // Mock requirements
                List<String> mockRequirements = createMockRequirements(courseId);
                requirements.postValue(mockRequirements);

                // Set mock metadata
                setMockMetadata(courseId);

                // Mock enrollment status
                boolean enrolled = isCourseEnrolled(courseId);
                isEnrolled.postValue(enrolled);

                // Mock wishlist status
                boolean wishlisted = isCourseWishlisted(courseId);
                isInWishlist.postValue(wishlisted);

                setLoading(false);

            } catch (InterruptedException e) {
                errorMessage.postValue("Failed to load course details: " + e.getMessage());
                setLoading(false);
            }
        }).start();
    }

    /**
     * Toggle enrollment status
     */
    public void toggleEnrollment() {
        Boolean currentStatus = isEnrolled.getValue();
        if (currentStatus == null) {
            currentStatus = false;
        }

        final boolean finalCurrentStatus = currentStatus;

        // Simulate API call
        new Thread(() -> {
            try {
                Thread.sleep(300); // Simulate network delay

                boolean newStatus = !finalCurrentStatus;
                isEnrolled.postValue(newStatus);

                if (newStatus) {
                    // Show success message or navigate to course content
                } else {
                    // Show unenrollment confirmation
                }

            } catch (InterruptedException e) {
                errorMessage.postValue("Failed to update enrollment: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Toggle wishlist status
     */
    public void toggleWishlist() {
        Boolean currentStatus = isInWishlist.getValue();
        if (currentStatus == null) {
            currentStatus = false;
        }

        final boolean finalCurrentStatus = currentStatus;

        // Simulate API call
        new Thread(() -> {
            try {
                Thread.sleep(200); // Simulate network delay

                boolean newStatus = !finalCurrentStatus;
                isInWishlist.postValue(newStatus);

            } catch (InterruptedException e) {
                errorMessage.postValue("Failed to update wishlist: " + e.getMessage());
            }
        }).start();
    }

    // ==================== METADATA GETTERS ====================

    public String getFormattedStudentCount() {
        if (studentCount < 1000) {
            return studentCount + " students";
        } else if (studentCount < 1000000) {
            return String.format("%.1fk students", studentCount / 1000.0);
        } else {
            return String.format("%.1fM students", studentCount / 1000000.0);
        }
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorTitle() {
        return instructorTitle;
    }

    public String getInstructorImage() {
        return instructorImage;
    }

    public boolean isFreeCourse() {
        return isFree;
    }

    public String getFormattedPrice() {
        if (isFree) {
            return "Free";
        }
        return String.format("$%.2f", price);
    }

    public String getFormattedOriginalPrice() {
        return String.format("$%.2f", originalPrice);
    }

    // ==================== PRIVATE HELPERS ====================

    private void setLoading(boolean isLoading) {
        this.isLoading.postValue(isLoading);
    }

    /**
     * Create mock course data with offline content
     */
    private Course createMockCourse(int courseId) {
        Course course = new Course();

        // Set offline content
        course.setHasOfflineContent(true);
        course.setFirstLessonAsset("lesson1.html");

        // Set lesson files based on category
        List<String> lessonFiles = Arrays.asList("lesson1.html", "lesson2.html");
        course.setLessonFiles(lessonFiles);

        switch (courseId) {
            case 1:
                course.setId(1);
                course.setTitle("HTML Fundamentals");
                course.setDescription("<p>Learn the basics of HTML including tags, attributes, and semantic markup.</p>" +
                        "<p>This comprehensive course covers:</p>" +
                        "<ul>" +
                        "<li>HTML document structure and syntax</li>" +
                        "<li>Common HTML tags and their uses</li>" +
                        "<li>Semantic HTML5 elements</li>" +
                        "<li>Forms and input elements</li>" +
                        "<li>Accessibility best practices</li>" +
                        "</ul>" +
                        "<p>Perfect for beginners who want to start their web development journey!</p>");
                course.setCategory("HTML");
                course.setDifficulty(1);
                course.setEstimatedTime(300);
                course.setThumbnail("https://example.com/html-course.jpg");
                break;

            case 2:
                course.setId(2);
                course.setTitle("Advanced HTML5");
                course.setDescription("<p>Master advanced HTML5 features including canvas, web storage, and semantic elements.</p>" +
                        "<p>In this course, you'll learn:</p>" +
                        "<ul>" +
                        "<li>Canvas API for graphics and animations</li>" +
                        "<li>Web Storage (localStorage and sessionStorage)</li>" +
                        "<li>Advanced semantic elements</li>" +
                        "<li>Audio and video elements</li>" +
                        "<li>Web Components and Shadow DOM</li>" +
                        "</ul>" +
                        "<p>Take your HTML skills to the next level!</p>");
                course.setCategory("HTML");
                course.setDifficulty(3);
                course.setEstimatedTime(450);
                course.setThumbnail("https://example.com/html5-course.jpg");
                break;

            case 3:
                course.setId(3);
                course.setTitle("CSS Fundamentals");
                course.setDescription("<p>Master CSS styling including selectors, properties, and responsive design.</p>" +
                        "<p>This course covers:</p>" +
                        "<ul>" +
                        "<li>CSS syntax and selectors</li>" +
                        "<li>Box model and layout</li>" +
                        "<li>Typography and colors</li>" +
                        "<li>Responsive design with media queries</li>" +
                        "<li>CSS Grid and Flexbox basics</li>" +
                        "</ul>");
                course.setCategory("CSS");
                course.setDifficulty(2);
                course.setEstimatedTime(400);
                course.setThumbnail("https://example.com/css-course.jpg");
                break;

            default:
                course.setId(courseId);
                course.setTitle("Course " + courseId);
                course.setDescription("<p>Course description for course " + courseId + ".</p>");
                course.setCategory("HTML");
                course.setDifficulty(2);
                course.setEstimatedTime(300);
                course.setThumbnail("");
                break;
        }

        return course;
    }

    /**
     * Create mock learning outcomes
     */
    private List<String> createMockLearningOutcomes(int courseId) {
        List<String> outcomes = new ArrayList<>();

        switch (courseId) {
            case 1: // HTML Fundamentals
                outcomes.add("Understand HTML structure and create well-formed documents");
                outcomes.add("Master commonly used HTML tags and their proper usage");
                outcomes.add("Create semantic web pages using HTML5 elements");
                outcomes.add("Build accessible HTML forms with proper input types");
                outcomes.add("Apply web accessibility guidelines (WCAG)");
                outcomes.add("Validate HTML code and fix common errors");
                break;

            case 2: // Advanced HTML5
                outcomes.add("Create dynamic graphics using HTML5 Canvas");
                outcomes.add("Implement client-side data storage with Web Storage API");
                outcomes.add("Build advanced semantic web applications");
                outcomes.add("Integrate multimedia content with HTML5 audio/video");
                outcomes.add("Develop reusable web components");
                outcomes.add("Implement progressive web app features");
                break;

            case 3: // CSS Fundamentals
                outcomes.add("Style web pages with CSS selectors and properties");
                outcomes.add("Master the CSS box model for layout control");
                outcomes.add("Create responsive designs with Flexbox and Grid");
                outcomes.add("Implement modern CSS features and best practices");
                outcomes.add("Optimize CSS for performance and maintainability");
                outcomes.add("Debug CSS issues effectively");
                break;

            default:
                outcomes.add("Learn core concepts and fundamentals");
                outcomes.add("Apply knowledge through practical exercises");
                outcomes.add("Build real-world projects");
                outcomes.add("Master advanced techniques");
                outcomes.add("Understand best practices and industry standards");
                break;
        }

        return outcomes;
    }

    /**
     * Create mock requirements
     */
    private List<String> createMockRequirements(int courseId) {
        List<String> requirements = new ArrayList<>();

        switch (courseId) {
            case 1: // HTML Fundamentals
                requirements.add("Basic computer skills and internet access");
                requirements.add("Text editor (Visual Studio Code recommended)");
                requirements.add("Modern web browser (Chrome, Firefox, or Safari)");
                requirements.add("No prior programming experience needed");
                break;

            case 2: // Advanced HTML5
                requirements.add("Strong understanding of HTML fundamentals");
                requirements.add("Basic CSS knowledge");
                requirements.add("JavaScript basics recommended");
                requirements.add("Experience with web development tools");
                break;

            case 3: // CSS Fundamentals
                requirements.add("Basic HTML knowledge");
                requirements.add("Text editor and web browser");
                requirements.add("Understanding of web page structure");
                requirements.add("Basic computer skills");
                break;

            default:
                requirements.add("Basic computer skills");
                requirements.add("Internet access");
                requirements.add("Text editor");
                requirements.add("Web browser");
                break;
        }

        return requirements;
    }

    /**
     * Set mock course metadata
     */
    private void setMockMetadata(int courseId) {
        switch (courseId) {
            case 1: // HTML Fundamentals
                studentCount = 15420;
                instructorName = "Sarah Johnson";
                instructorTitle = "Frontend Developer";
                instructorImage = "https://example.com/instructors/sarah.jpg";
                isFree = true;
                price = 0.0;
                originalPrice = 0.0;
                break;

            case 2: // Advanced HTML5
                studentCount = 8930;
                instructorName = "Mike Chen";
                instructorTitle = "Senior Web Developer";
                instructorImage = "https://example.com/instructors/mike.jpg";
                isFree = false;
                price = 19.99;
                originalPrice = 49.99;
                break;

            case 3: // CSS Fundamentals
                studentCount = 12150;
                instructorName = "Emily Rodriguez";
                instructorTitle = "UI/UX Designer";
                instructorImage = "https://example.com/instructors/emily.jpg";
                isFree = true;
                price = 0.0;
                originalPrice = 0.0;
                break;

            default:
                studentCount = 5000;
                instructorName = "John Doe";
                instructorTitle = "Web Developer";
                instructorImage = "";
                isFree = true;
                price = 0.0;
                originalPrice = 29.99;
                break;
        }
    }

    /**
     * Mock enrollment status check
     */
    private boolean isCourseEnrolled(int courseId) {
        // Mock enrolled courses
        return courseId == 1 || courseId == 3;
    }

    /**
     * Mock wishlist status check
     */
    private boolean isCourseWishlisted(int courseId) {
        // Mock wishlisted courses
        return courseId == 2;
    }
}