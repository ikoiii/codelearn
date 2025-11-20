package com.codelearn.android.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codelearn.android.R;
import com.codelearn.android.databinding.FragmentCourseListBinding;
import com.codelearn.android.model.Course;
import com.codelearn.android.ui.content.ContentViewerFragment;

import java.util.List;

/**
 * Fragment untuk menampilkan daftar kursus offline
 * Fokus pada HTML, CSS, JavaScript
 */
public class CourseListFragment extends Fragment {

    private FragmentCourseListBinding binding;
    private CourseListViewModel viewModel;
    private CourseAdapter courseAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCourseListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
        initializeViewModel();
        observeViewModel();
        setupFilterButtons();
    }

    private void initializeViews() {
        // Setup RecyclerView
        courseAdapter = new CourseAdapter();
        binding.recyclerViewCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCourses.setAdapter(courseAdapter);

        // Setup course click listener
        courseAdapter.setOnItemClickListener((course, position) -> {
            navigateToContentViewer(course);
        });
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(CourseListViewModel.class);
        viewModel.loadCourses();
    }

    private void observeViewModel() {
        // Observe courses
        viewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
            hideLoading();
            if (courses != null && !courses.isEmpty()) {
                showCourses(courses);
            } else {
                showNoResults();
            }
        });

        // Observe loading state
        viewModel.getLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        // Observe errors
        viewModel.getErrorState().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                showError("Gagal memuat kursus. Silakan coba lagi.");
            }
        });
    }

    private void setupFilterButtons() {
        // Filter buttons
        binding.buttonFilterAll.setOnClickListener(v -> {
            viewModel.setFilter(CourseListViewModel.FilterType.ALL);
            updateFilterButtonState(CourseListViewModel.FilterType.ALL);
        });

        binding.buttonFilterHtml.setOnClickListener(v -> {
            viewModel.setFilter(CourseListViewModel.FilterType.HTML);
            updateFilterButtonState(CourseListViewModel.FilterType.HTML);
        });

        binding.buttonFilterCss.setOnClickListener(v -> {
            viewModel.setFilter(CourseListViewModel.FilterType.CSS);
            updateFilterButtonState(CourseListViewModel.FilterType.CSS);
        });

        binding.buttonFilterJavascript.setOnClickListener(v -> {
            viewModel.setFilter(CourseListViewModel.FilterType.JAVASCRIPT);
            updateFilterButtonState(CourseListViewModel.FilterType.JAVASCRIPT);
        });

        // Set initial state
        updateFilterButtonState(CourseListViewModel.FilterType.ALL);
    }

    private void updateFilterButtonState(CourseListViewModel.FilterType filterType) {
        // Reset all buttons to outlined style
        binding.buttonFilterAll.setElevation(0);
        binding.buttonFilterHtml.setElevation(0);
        binding.buttonFilterCss.setElevation(0);
        binding.buttonFilterJavascript.setElevation(0);

        // Highlight selected button
        switch (filterType) {
            case ALL:
                binding.buttonFilterAll.setElevation(4);
                break;
            case HTML:
                binding.buttonFilterHtml.setElevation(4);
                break;
            case CSS:
                binding.buttonFilterCss.setElevation(4);
                break;
            case JAVASCRIPT:
                binding.buttonFilterJavascript.setElevation(4);
                break;
        }
    }

    private void showLoading() {
        binding.layoutLoading.setVisibility(View.VISIBLE);
        binding.recyclerViewCourses.setVisibility(View.GONE);
        binding.layoutNoResults.setVisibility(View.GONE);
    }

    private void hideLoading() {
        binding.layoutLoading.setVisibility(View.GONE);
    }

    private void showCourses(List<Course> courses) {
        binding.recyclerViewCourses.setVisibility(View.VISIBLE);
        binding.layoutNoResults.setVisibility(View.GONE);
        courseAdapter.submitList(courses);
    }

    private void showNoResults() {
        binding.recyclerViewCourses.setVisibility(View.GONE);
        binding.layoutNoResults.setVisibility(View.VISIBLE);
    }

    private void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    private void navigateToContentViewer(Course course) {
        NavController navController = Navigation.findNavController(requireView());

        // Navigate ke ContentViewer langsung untuk kursus offline
        if (course.hasOfflineContent() && course.getFirstLessonAsset() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("course", course);
            bundle.putString("contentPath", course.getFirstLessonAsset());

            navController.navigate(R.id.action_courseListFragment_to_contentViewerFragment, bundle);
        } else {
            showError("Konten offline tidak tersedia untuk kursus ini");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}