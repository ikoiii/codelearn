package com.codelearn.android.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codelearn.android.R;
import com.codelearn.android.databinding.FragmentHomeBinding;
import com.codelearn.android.model.Course;

/**
 * Home fragment yang menampilkan jalur pembelajaran offline sederhana
 * Fokus pada HTML, CSS, JavaScript
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
    }

    /**
     * Initialize click listeners untuk learning path cards
     */
    private void initializeViews() {
        // HTML Card click listener
        binding.cardHtml.setOnClickListener(v -> {
            Course htmlCourse = createHtmlCourse();
            navigateToContent(htmlCourse, "01_pengenalan.html");
        });

        // CSS Card click listener
        binding.cardCss.setOnClickListener(v -> {
            Course cssCourse = createCssCourse();
            navigateToContent(cssCourse, "01_pengenalan_css.html");
        });

        // JavaScript Card click listener
        binding.cardJavaScript.setOnClickListener(v -> {
            Course jsCourse = createJavaScriptCourse();
            navigateToContent(jsCourse, "01_pengenalan_javascript.html");
        });
    }

    /**
     * Membuat course object untuk HTML
     */
    private Course createHtmlCourse() {
        Course course = new Course();
        course.setId(1);
        course.setTitle("HTML");
        course.setDescription("Pelajari dasar-dasar HTML mulai dari tag, atribut, hingga struktur dokumen HTML untuk pemula.");
        course.setCategory("HTML");
        course.setDifficulty(1);
        course.setEstimatedTime(180);
        course.setHasOfflineContent(true);
        course.setFirstLessonAsset("01_pengenalan.html");
        return course;
    }

    /**
     * Membuat course object untuk CSS
     */
    private Course createCssCourse() {
        Course course = new Course();
        course.setId(2);
        course.setTitle("CSS");
        course.setDescription("Pelajari dasar-dasar CSS untuk styling website, termasuk selectors, properties, dan layout.");
        course.setCategory("CSS");
        course.setDifficulty(2);
        course.setEstimatedTime(300);
        course.setHasOfflineContent(true);
        course.setFirstLessonAsset("01_pengenalan_css.html");
        return course;
    }

    /**
     * Membuat course object untuk JavaScript
     */
    private Course createJavaScriptCourse() {
        Course course = new Course();
        course.setId(3);
        course.setTitle("JavaScript");
        course.setDescription("Pelajari fundamental JavaScript untuk membuat website interaktif dan dinamis.");
        course.setCategory("JavaScript");
        course.setDifficulty(3);
        course.setEstimatedTime(420);
        course.setHasOfflineContent(true);
        course.setFirstLessonAsset("01_pengenalan_javascript.html");
        return course;
    }

    /**
     * Navigasi ke ContentViewer dengan course dan content path
     */
    private void navigateToContent(Course course, String contentPath) {
        NavController navController = Navigation.findNavController(requireView());

        // Navigate ke ContentViewerFragment dengan arguments
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", course);
        bundle.putString("contentPath", contentPath);

        navController.navigate(R.id.action_courseListFragment_to_contentViewerFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}