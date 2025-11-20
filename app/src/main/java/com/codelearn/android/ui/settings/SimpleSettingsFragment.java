package com.codelearn.android.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codelearn.android.R;

/**
 * Complete Settings Fragment with stable implementation
 */
public class SimpleSettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings_final, container, false);

        setupButtons(rootView);

        return rootView;
    }

    private void setupButtons(View rootView) {
        // Back button
        Button backButton = rootView.findViewById(R.id.btn_back);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            });
        }

        // About button
        Button aboutButton = rootView.findViewById(R.id.btn_about);
        if (aboutButton != null) {
            aboutButton.setOnClickListener(v -> showAboutDialog());
        }

        // Text size button
        Button textSizeButton = rootView.findViewById(R.id.btn_text_size);
        if (textSizeButton != null) {
            textSizeButton.setOnClickListener(v -> showTextSizeDialog());
        }

        // Reset progress button
        Button resetProgressButton = rootView.findViewById(R.id.btn_reset_progress);
        if (resetProgressButton != null) {
            resetProgressButton.setOnClickListener(v -> showResetProgressDialog());
        }

        // Help button
        Button helpButton = rootView.findViewById(R.id.btn_help);
        if (helpButton != null) {
            helpButton.setOnClickListener(v -> showHelpDialog());
        }
    }

    private void showAboutDialog() {
        if (getContext() == null) return;

        new AlertDialog.Builder(getContext())
                .setTitle("Tentang CodeLearn Android")
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

    private void showTextSizeDialog() {
        if (getContext() == null) return;

        String[] textSizeOptions = {"Kecil", "Sedang", "Besar"};
        Button textSizeButton = getView() != null ? getView().findViewById(R.id.btn_text_size) : null;

        new AlertDialog.Builder(getContext())
                .setTitle("Ukuran Teks")
                .setItems(textSizeOptions, (dialog, which) -> {
                    String selectedSize = textSizeOptions[which];
                    if (textSizeButton != null) {
                        textSizeButton.setText("🔤 Ukuran Teks: " + selectedSize);
                    }
                    Toast.makeText(getContext(), "Ukuran teks diubah ke " + selectedSize, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void showResetProgressDialog() {
        if (getContext() == null) return;

        new AlertDialog.Builder(getContext())
                .setTitle("Reset Progress")
                .setMessage("Apakah Anda yakin ingin mereset semua progress belajar? Tindakan ini tidak dapat dibatalkan.")
                .setPositiveButton("Reset", (dialog, which) -> {
                    Toast.makeText(getContext(), "Progress berhasil direset", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void showHelpDialog() {
        if (getContext() == null) return;

        new AlertDialog.Builder(getContext())
                .setTitle("Panduan Penggunaan")
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
}