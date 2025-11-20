package com.codelearn.android.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codelearn.android.R;
import com.codelearn.android.databinding.FragmentSettingsBinding;

/**
 * Settings Fragment for app configuration and user preferences
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews();
        setupClickListeners();
        loadSettings();
    }

    private void initializeViews() {
        // Setup toolbar
        if (getActivity() != null) {
            getActivity().setTitle("Pengaturan");
        }

        // Setup settings sections
        setupAboutSection();
        setupAppSection();
        setupLearningSection();
    }

    private void setupClickListeners() {
        try {
            // Back button
            if (binding.buttonBack != null) {
                binding.buttonBack.setOnClickListener(v -> {
                    try {
                        NavController navController = Navigation.findNavController(requireView());
                        navController.navigateUp();
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (getActivity() != null) {
                            getActivity().onBackPressed();
                        }
                    }
                });
            }

            // Settings options with null checks
            if (binding.textSize != null) {
                binding.textSize.setOnClickListener(v -> showTextSizeDialog());
            }

            if (binding.resetProgress != null) {
                binding.resetProgress.setOnClickListener(v -> showResetProgressDialog());
            }

            if (binding.about != null) {
                binding.about.setOnClickListener(v -> showAboutDialog());
            }

            if (binding.help != null) {
                binding.help.setOnClickListener(v -> showHelpDialog());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupAboutSection() {
        try {
            // App info
            if (binding.appVersion != null) {
                binding.appVersion.setText("Versi 1.1.0");
            }
            if (binding.appBuild != null) {
                binding.appBuild.setText("Build: 2025.11.20");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupAppSection() {
        try {
            // App preferences will be loaded here
            updateTextSizeDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupLearningSection() {
        try {
            // Learning preferences will be loaded here
            updateProgressDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        // Load settings from SharedPreferences
        // This will be implemented with actual preferences later
    }

    private void updateTextSizeDisplay() {
        try {
            // Show current text size setting
            // For now, show default
            if (binding.textSizeValue != null) {
                binding.textSizeValue.setText("Sedang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProgressDisplay() {
        try {
            // Show progress statistics
            if (binding.progressValue != null) {
                binding.progressValue.setText("2 dari 4 kursus dimulai");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTextSizeDialog() {
        try {
            // Show text size selection dialog
            // Options: Kecil, Sedang, Besar
            String[] textSizeOptions = {"Kecil", "Sedang", "Besar"};

            if (getContext() != null) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                builder.setTitle("Ukuran Teks")
                       .setItems(textSizeOptions, (dialog, which) -> {
                           String selectedSize = textSizeOptions[which];
                           if (binding.textSizeValue != null) {
                               binding.textSizeValue.setText(selectedSize);
                           }
                           if (getContext() != null) {
                               Toast.makeText(getContext(), "Ukuran teks diubah ke " + selectedSize, Toast.LENGTH_SHORT).show();
                           }
                           // TODO: Save preference and apply to UI
                       })
                       .setNegativeButton("Batal", null)
                       .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResetProgressDialog() {
        try {
            if (getContext() != null) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                builder.setTitle("Reset Progress")
                       .setMessage("Apakah Anda yakin ingin mereset semua progress belajar? Tindakan ini tidak dapat dibatalkan.")
                       .setPositiveButton("Reset", (dialog, which) -> {
                           // TODO: Reset all progress
                           updateProgressDisplay();
                           if (getContext() != null) {
                               Toast.makeText(getContext(), "Progress berhasil direset", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .setNegativeButton("Batal", null)
                       .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAboutDialog() {
        try {
            if (getContext() != null) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                builder.setTitle("Tentang CodeLearn Android")
                       .setMessage("CodeLearn Android adalah aplikasi pembelajaran coding offline yang membantu Anda belajar HTML, CSS, dan JavaScript dengan cara yang mudah dan menyenangkan.\n\n" +
                                   "Fitur:\n" +
                                   "• 4 kursus esensial (HTML, CSS, JavaScript)\n" +
                                   "• 100% offline - tidak perlu internet\n" +
                                   "• Progress tracking visual\n" +
                                   "• Responsive design untuk semua perangkat\n" +
                                   "• Bahasa Indonesia\n\n" +
                                   "Versi: 1.1.0\n" +
                                   "Dibuat dengan ❤️ untuk pembelajaran programming di Indonesia")
                       .setPositiveButton("OK", null)
                       .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showHelpDialog() {
        try {
            if (getContext() != null) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                builder.setTitle("Bantuan")
                       .setMessage("Cara menggunakan CodeLearn Android:\n\n" +
                                   "1. **Pilih Kursus**: Tap pada kartu kursus untuk mulai belajar\n" +
                                   "2. **Pelajari Materi**: Baca konten pembelajaran yang tersedia\n" +
                                   "3. **Track Progress**: Lihat progress Anda di setiap kursus\n" +
                                   "4. **Cari Kursus**: Gunakan fitur search untuk menemukan materi\n" +
                                   "5. **Filter Kategori**: Filter kursus berdasarkan HTML, CSS, atau JavaScript\n\n" +
                                   "Tips:\n" +
                                   "• Pelajari secara berurutan untuk hasil terbaik\n" +
                                   "• Aplikasi ini 100% offline, tidak perlu internet\n" +
                                   "• Progress Anda otomatis tersimpan\n\n" +
                                   "Butuh bantuan lebih lanjut? Hubungi developer.")
                       .setPositiveButton("Mengerti", null)
                       .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}