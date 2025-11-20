package com.codelearn.android;

import android.app.Application;

/**
 * Application class for CodeLearn Android
 * Provides application-level context and initialization
 */
public class CodeLearnApplication extends Application {

    private static CodeLearnApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Initialize application components here
        initializeComponents();
    }

    /**
     * Get singleton instance of the application
     * @return CodeLearnApplication instance
     */
    public static CodeLearnApplication getInstance() {
        return instance;
    }

    /**
     * Initialize application components
     */
    private void initializeComponents() {
        // Initialize database
        // Initialize analytics
        // Initialize crash reporting
        // Other application-wide components
    }
}