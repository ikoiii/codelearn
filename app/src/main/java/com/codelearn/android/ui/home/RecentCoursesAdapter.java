package com.codelearn.android.ui.home;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.codelearn.android.R;
import com.codelearn.android.databinding.ItemRecentCourseBinding;
import com.codelearn.android.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for recent courses on home screen
 */
public class RecentCoursesAdapter extends ListAdapter<Course, RecentCoursesAdapter.RecentCourseViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<Course> courses = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(Course course, int position);
    }

    public RecentCoursesAdapter() {
        super(new DiffUtilCallback());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecentCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecentCourseBinding binding = ItemRecentCourseBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RecentCourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentCourseViewHolder holder, int position) {
        Course course = getItem(position);
        holder.bind(course);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Course> list) {
        this.courses = list != null ? list : new ArrayList<>();
        super.submitList(this.courses);
    }

    class RecentCourseViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecentCourseBinding binding;

        public RecentCourseViewHolder(@NonNull ItemRecentCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Course course) {
            binding.textCourseTitle.setText(course.getTitle());
            binding.chipCategory.setText(course.getCategory());

            // Set category color
            int categoryColor = getCategoryColor(course.getCategory());
            binding.chipCategory.setChipBackgroundColorResource(categoryColor);

            // Set difficulty badge
            String difficultyLevel = course.getDifficultyLevel();
            binding.textDifficulty.setText(difficultyLevel);
            int difficultyColor = getDifficultyColor(course.getDifficulty());
            binding.textDifficulty.setTextColor(difficultyColor);

            // Set estimated time
            binding.textEstimatedTime.setText(course.getFormattedTime());

            // Set click listener
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(course, getAdapterPosition());
                }
            });
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
                    return R.color.difficulty_beginner;
                case 2:
                    return R.color.difficulty_beginner;
                case 3:
                    return R.color.difficulty_intermediate;
                case 4:
                    return R.color.difficulty_advanced;
                case 5:
                    return R.color.difficulty_advanced;
                default:
                    return R.color.md_theme_light_onSurfaceVariant;
            }
        }
    }

    private static class DiffUtilCallback extends DiffUtil.ItemCallback<Course> {
        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getCategory().equals(newItem.getCategory()) &&
                    oldItem.getDifficulty() == newItem.getDifficulty() &&
                    oldItem.getEstimatedTime() == newItem.getEstimatedTime();
        }
    }
}