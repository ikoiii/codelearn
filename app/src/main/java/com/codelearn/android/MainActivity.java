package com.codelearn.android;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * Main Activity untuk aplikasi pembelajaran coding offline
 * Tampilan langsung ke materi pembelajaran dengan navigation yang sederhana
 */
public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupNavigation();
    }

    /**
     * Initialize views
     */
    private void initializeViews() {
        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Materi Pembelajaran");
        }

        // Setup Navigation Controller
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    /**
     * Setup navigation - simplified untuk direct course access
     */
    private void setupNavigation() {
        if (navController == null) return;

        // Define top-level destinations - hanya course list
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.courseListFragment
        ).build();

        // Setup action bar with back button support
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Listen for navigation changes to update title
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            updateToolbarTitle(destination.getId());
        });
    }

    /**
     * Update toolbar title based on destination
     */
    private void updateToolbarTitle(int destinationId) {
        if (getSupportActionBar() == null) return;

        String title = "Materi Pembelajaran";

        switch (destinationId) {
            case R.id.courseListFragment:
                title = "Materi Pembelajaran";
                break;
            case R.id.contentViewerFragment:
                title = "Belajar";
                break;
        }

        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle back navigation properly
        if (navController != null) {
            boolean handled = NavigationUI.navigateUp(navController, appBarConfiguration);

            // Update title when navigating back
            if (handled && navController.getCurrentDestination() != null) {
                updateToolbarTitle(navController.getCurrentDestination().getId());
            }

            return handled;
        }
        return super.onSupportNavigateUp();
    }

    /**
     * Handle back press - ensure proper navigation
     */
    @Override
    public void onBackPressed() {
        if (navController != null && navController.getCurrentDestination() != null) {
            int currentDestination = navController.getCurrentDestination().getId();

            // If we're in content viewer, navigate back to course list
            if (currentDestination == R.id.contentViewerFragment) {
                navController.navigateUp();
                return;
            }
        }

        // Exit app
        super.onBackPressed();
    }
}