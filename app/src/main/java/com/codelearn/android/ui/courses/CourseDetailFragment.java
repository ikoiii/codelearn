package com.codelearn.android.ui.courses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.codelearn.android.R;
import com.codelearn.android.databinding.FragmentCourseDetailBinding;
import com.codelearn.android.model.Course;
import com.codelearn.android.ui.content.ContentViewerFragment;
import com.codelearn.android.utils.Constants;

/**
 * CourseDetailFragment displays detailed information about a specific course
 * Features:
 * - Collapsing toolbar with hero image
 * - Tab layout for Theory/Practice/Quiz sections
 * - HTML rendering for course descriptions
 * - Enroll button with state management
 * - Share functionality
 */
public class CourseDetailFragment extends Fragment {

    private FragmentCourseDetailBinding binding;
    private CourseDetailViewModel viewModel;
    private int courseId;
    private LearningOutcomesAdapter learningOutcomesAdapter;
    private RequirementsAdapter requirementsAdapter;

    // Required empty constructor
    public CourseDetailFragment() {}

    public static CourseDetailFragment newInstance(int courseId) {
        CourseDetailFragment fragment = new CourseDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_COURSE_ID, courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Get course ID from arguments
        if (getArguments() != null) {
            courseId = getArguments().getInt(Constants.ARG_COURSE_ID, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCourseDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
        initializeViewModel();
        observeViewModel();
    }

    private void initializeViews() {
        setupToolbar();
        setupRecyclerViews();
        setupClickListeners();
        setupTabLayout();
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);
        viewModel.loadCourseDetails(courseId);
    }

    private void observeViewModel() {
        // Observe course details
        viewModel.getCourse().observe(getViewLifecycleOwner(), course -> {
            if (course != null) {
                populateCourseDetails(course);
            }
        });

        // Observe learning outcomes
        viewModel.getLearningOutcomes().observe(getViewLifecycleOwner(), outcomes -> {
            if (outcomes != null) {
                learningOutcomesAdapter.submitList(outcomes);
            }
        });

        // Observe requirements
        viewModel.getRequirements().observe(getViewLifecycleOwner(), requirements -> {
            if (requirements != null) {
                requirementsAdapter.submitList(requirements);
            }
        });

        // Observe enrollment status
        viewModel.getIsEnrolled().observe(getViewLifecycleOwner(), isEnrolled -> {
            updateEnrollButtonState(isEnrolled);
        });

        // Observe wishlist status
        viewModel.getIsInWishlist().observe(getViewLifecycleOwner(), isInWishlist -> {
            updateWishlistButtonState(isInWishlist);
        });

        // Observe loading state
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        // Observe errors
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                showError(errorMessage);
            }
        });
    }

    private void setupToolbar() {
        if (getActivity() != null) {
            // Enable back navigation
            binding.toolbar.setNavigationOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            });
        }
    }

    private void setupRecyclerViews() {
        // Learning outcomes RecyclerView
        learningOutcomesAdapter = new LearningOutcomesAdapter();
        binding.recyclerViewLearningOutcomes.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewLearningOutcomes.setAdapter(learningOutcomesAdapter);

        // Requirements RecyclerView
        requirementsAdapter = new RequirementsAdapter();
        binding.recyclerViewRequirements.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewRequirements.setAdapter(requirementsAdapter);
    }

    private void setupClickListeners() {
        // Enroll button
        binding.buttonEnroll.setOnClickListener(v -> {
            viewModel.toggleEnrollment();
        });

        // Wishlist button
        binding.buttonWishlist.setOnClickListener(v -> {
            viewModel.toggleWishlist();
        });
    }

    private void setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
                handleTabSelection(tab.getPosition());
            }

            @Override
            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {
                // Handle tab unselection if needed
            }

            @Override
            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {
                handleTabSelection(tab.getPosition());
            }
        });
    }

    private void handleTabSelection(int position) {
        switch (position) {
            case 0: // Theory
                showTheoryContent();
                break;
            case 1: // Practice
                showPracticeContent();
                break;
            case 2: // Quiz
                showQuizContent();
                break;
        }
    }

    private void showTheoryContent() {
        // Show/hide relevant cards for theory content
        binding.cardDescription.setVisibility(View.VISIBLE);
        binding.cardLearningOutcomes.setVisibility(View.VISIBLE);
        binding.cardRequirements.setVisibility(View.VISIBLE);
        binding.cardInstructor.setVisibility(View.VISIBLE);
    }

    private void showPracticeContent() {
        // Show practice exercises
        binding.cardDescription.setVisibility(View.GONE);
        binding.cardLearningOutcomes.setVisibility(View.GONE);
        binding.cardRequirements.setVisibility(View.GONE);
        binding.cardInstructor.setVisibility(View.GONE);

        // Navigate to content viewer
        startLearning();
    }

    private void showQuizContent() {
        // Show quiz questions
        binding.cardDescription.setVisibility(View.GONE);
        binding.cardLearningOutcomes.setVisibility(View.GONE);
        binding.cardRequirements.setVisibility(View.GONE);
        binding.cardInstructor.setVisibility(View.GONE);

        // You could replace content with quiz questions
        Toast.makeText(getContext(), "Quiz coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void populateCourseDetails(@NonNull com.codelearn.android.model.Course course) {
        // Set toolbar title
        binding.collapsingToolbar.setTitle(course.getTitle());

        // Load hero image
        if (course.getThumbnail() != null && !course.getThumbnail().isEmpty()) {
            Glide.with(this)
                    .load(course.getThumbnail())
                    .placeholder(R.drawable.ic_courses)
                    .error(R.drawable.ic_courses)
                    .into(binding.imageHero);
        } else {
            binding.imageHero.setImageResource(getCategoryPlaceholder(course.getCategory()));
        }

        // Set category and difficulty
        binding.chipCategory.setText(course.getCategory());
        binding.chipDifficulty.setText(course.getDifficultyLevel());

        // Set colors for chips
        int categoryColor = getCategoryColor(course.getCategory());
        binding.chipCategory.setTextColor(categoryColor);

        int difficultyColor = getDifficultyColor(course.getDifficulty());
        binding.chipDifficulty.setChipBackgroundColorResource(difficultyColor);
        binding.chipDifficulty.setTextColor(getColorForDifficulty(course.getDifficulty()));

        // Set estimated time and student count
        binding.textEstimatedTime.setText(course.getFormattedTime());
        binding.textStudentCount.setText(viewModel.getFormattedStudentCount());

        // Set course description with HTML rendering
        String htmlDescription = course.getDescription();
        Spanned formattedDescription = Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_COMPACT);
        binding.textCourseDescription.setText(formattedDescription);
        binding.textCourseDescription.setMovementMethod(LinkMovementMethod.getInstance());

        // Set instructor info
        binding.textInstructorName.setText(viewModel.getInstructorName());
        binding.textInstructorTitle.setText(viewModel.getInstructorTitle());

        // Load instructor image
        Glide.with(this)
                .load(viewModel.getInstructorImage())
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(binding.imageInstructor);

        // Set price
        if (viewModel.isFreeCourse()) {
            binding.textPrice.setText("Free");
            binding.textOriginalPrice.setVisibility(View.GONE);
        } else {
            binding.textPrice.setText(viewModel.getFormattedPrice());
            binding.textOriginalPrice.setText(viewModel.getFormattedOriginalPrice());
            binding.textOriginalPrice.setVisibility(View.VISIBLE);
        }
    }

    private void updateEnrollButtonState(boolean isEnrolled) {
        if (isEnrolled) {
            binding.buttonEnroll.setText("Continue Learning");
            binding.buttonEnroll.setBackgroundColor(requireContext().getColor(R.color.md_theme_light_secondary));
            binding.buttonEnroll.setTextColor(requireContext().getColor(R.color.md_theme_light_onSecondary));
            // Add click listener to start learning
            binding.buttonEnroll.setOnClickListener(v -> startLearning());
        } else {
            binding.buttonEnroll.setText("Start Learning");
            binding.buttonEnroll.setBackgroundColor(requireContext().getColor(R.color.md_theme_light_primary));
            binding.buttonEnroll.setTextColor(requireContext().getColor(R.color.md_theme_light_onPrimary));
            // Add click listener to start learning (since it's offline, we can start immediately)
            binding.buttonEnroll.setOnClickListener(v -> startLearning());
        }
    }

    private void updateWishlistButtonState(boolean isInWishlist) {
        if (isInWishlist) {
            binding.buttonWishlist.setIconResource(R.drawable.ic_favorite);
        } else {
            binding.buttonWishlist.setIconResource(R.drawable.ic_favorite_border);
        }
    }

    private int getCategoryColor(String category) {
        switch (category) {
            case "HTML":
                return R.color.html_color;
            case "CSS":
                return R.color.css_color;
            case "JavaScript":
                return R.color.javascript_color;
            default:
                return R.color.md_theme_light_primary;
        }
    }

    private int getDifficultyColor(int difficulty) {
        switch (difficulty) {
            case 1:
            case 2:
                return R.color.difficulty_beginner;
            case 3:
                return R.color.difficulty_intermediate;
            case 4:
            case 5:
                return R.color.difficulty_advanced;
            default:
                return R.color.md_theme_light_surfaceVariant;
        }
    }

    private int getColorForDifficulty(int difficulty) {
        switch (difficulty) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return R.color.md_theme_light_onSurface;
            default:
                return R.color.md_theme_light_onSurfaceVariant;
        }
    }

    private int getCategoryPlaceholder(String category) {
        switch (category) {
            case "HTML":
            case "CSS":
            case "JavaScript":
                return R.drawable.ic_courses;
            default:
                return R.drawable.ic_courses;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_course_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            shareCourse();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startLearning() {
        com.codelearn.android.model.Course currentCourse = viewModel.getCourse().getValue();
        if (currentCourse != null && getActivity() != null) {
            // Create content viewer fragment
            ContentViewerFragment contentFragment = ContentViewerFragment.newInstance(currentCourse);

            // Navigate to content viewer
            getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, contentFragment)
                .addToBackStack("content_viewer")
                .commit();
        }
    }

    private void shareCourse() {
        com.codelearn.android.model.Course course = viewModel.getCourse().getValue();
        if (course != null && getContext() != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this course: " + course.getTitle());
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "I'm learning " + course.getTitle() + " on CodeLearn!\n\n" +
                    course.getDescription() + "\n\n" +
                    "Download CodeLearn and start coding today!");

            Intent chooser = Intent.createChooser(shareIntent, "Share Course");
            startActivity(chooser);
        }
    }

    private void showLoading() {
        // Show loading state - using toast for now since progressBar doesn't exist in layout
        if (getContext() != null) {
            Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideLoading() {
        // Hide loading state - no-op for now since progressBar doesn't exist in layout
    }

    private void showError(String errorMessage) {
        // Show error message
        if (getContext() != null) {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}