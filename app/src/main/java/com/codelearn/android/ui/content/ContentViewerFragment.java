package com.codelearn.android.ui.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codelearn.android.databinding.FragmentContentViewerBinding;
import com.codelearn.android.model.Course;
import com.codelearn.android.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Fragment for displaying offline learning content
 * Shows HTML, CSS, and JavaScript lessons from assets
 */
public class ContentViewerFragment extends Fragment {

    private FragmentContentViewerBinding binding;
    private Course course;
    private String currentLesson;

    public ContentViewerFragment() {
        // Required empty constructor
    }

    public static ContentViewerFragment newInstance(Course course) {
        ContentViewerFragment fragment = new ContentViewerFragment();
        Bundle args = new Bundle();
        args.putSerializable("course", course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            course = (Course) getArguments().getSerializable("course");
            currentLesson = getArguments().getString("contentPath", course != null ? course.getFirstLessonAsset() : "01_pengenalan.html");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContentViewerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWebView();
        loadContent();
        setupNavigation();
    }

    private void setupWebView() {
        // Configure WebView settings
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setAllowFileAccess(true);
        binding.webView.getSettings().setAllowContentAccess(true);

        // Enable zoom controls
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setDisplayZoomControls(false);

        // Set WebViewClient to handle page loading
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                binding.progressBar.setVisibility(View.GONE);
                showError("Gagal memuat konten: " + description);
            }
        });
    }

    private void loadContent() {
        if (course == null || !course.hasOfflineContent()) {
            showError("Konten offline tidak tersedia untuk kursus ini");
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        try {
            // Load HTML content from assets
            String assetPath = course.getOfflineAssetPath(currentLesson);
            if (assetPath != null) {
                String htmlContent = loadHtmlFromAssets(assetPath);
                if (htmlContent != null) {
                    // Load HTML with base URL for relative paths
                    binding.webView.loadDataWithBaseURL("file:///android_asset/", htmlContent, "text/html", "UTF-8", null);
                } else {
                    showError("File konten tidak ditemukan: " + assetPath);
                }
            }
        } catch (Exception e) {
            showError("Error memuat konten: " + e.getMessage());
        }
    }

    private String loadHtmlFromAssets(String fileName) {
        try {
            InputStream inputStream = requireContext().getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            reader.close();
            inputStream.close();

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setupNavigation() {
        // Previous lesson button
        binding.buttonPrevious.setOnClickListener(v -> {
            navigateToLesson(-1);
        });

        // Next lesson button
        binding.buttonNext.setOnClickListener(v -> {
            navigateToLesson(1);
        });

        // Refresh button
        binding.buttonRefresh.setOnClickListener(v -> {
            loadContent();
        });

        // Update lesson navigation
        updateLessonNavigation();
    }

    private void navigateToLesson(int direction) {
        if (course == null || course.getLessonFiles() == null) {
            return;
        }

        // Simple lesson navigation based on current lesson number
        try {
            int currentLessonNumber = Integer.parseInt(currentLesson.replaceAll("[^0-9]", ""));
            int newLessonNumber = currentLessonNumber + direction;

            if (newLessonNumber >= 1 && newLessonNumber <= course.getLessonFiles().size()) {
                currentLesson = "lesson" + newLessonNumber + ".html";
                loadContent();
                updateLessonNavigation();
            }
        } catch (NumberFormatException e) {
            // Fallback - try to find next lesson
            Toast.makeText(getContext(), "Navigasi lesson tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLessonNavigation() {
        if (course == null || course.getLessonFiles() == null) {
            binding.buttonPrevious.setEnabled(false);
            binding.buttonNext.setEnabled(false);
            return;
        }

        try {
            int currentLessonNumber = Integer.parseInt(currentLesson.replaceAll("[^0-9]", ""));

            // Update previous button
            binding.buttonPrevious.setEnabled(currentLessonNumber > 1);

            // Update next button
            binding.buttonNext.setEnabled(currentLessonNumber < course.getLessonFiles().size());

        } catch (NumberFormatException e) {
            binding.buttonPrevious.setEnabled(false);
            binding.buttonNext.setEnabled(false);
        }

        // Update title
        if (course != null) {
            binding.toolbarTitle.setText(course.getTitle());
        }
    }

    private void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up WebView to prevent memory leaks
        if (binding.webView != null) {
            binding.webView.stopLoading();
            binding.webView.clearHistory();
            binding.webView.clearCache(true);
            binding.webView.loadUrl("about:blank");
            binding.webView.onPause();
            binding.webView.removeAllViews();
            binding.webView.destroyDrawingCache();
            binding.webView.destroy();
        }
        binding = null;
    }

    /**
     * Handle back press for WebView navigation
     */
    public boolean onBackPressed() {
        if (binding.webView != null && binding.webView.canGoBack()) {
            binding.webView.goBack();
            return true;
        }
        return false;
    }
}