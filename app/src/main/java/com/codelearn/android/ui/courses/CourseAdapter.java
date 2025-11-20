package com.codelearn.android.ui.courses;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.codelearn.android.R;
import com.codelearn.android.databinding.ItemCourseBinding;
import com.codelearn.android.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for course list
 */
public class CourseAdapter extends ListAdapter<Course, CourseAdapter.CourseViewHolder> {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private List<Course> courses = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(Course course, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Course course, int position);
    }

    public CourseAdapter() {
        super(new DiffUtilCallback());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseBinding binding = ItemCourseBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = getItem(position);
        holder.bind(course);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Course> list) {
        this.courses = list != null ? list : new ArrayList<>();
        super.submitList(this.courses);
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final ItemCourseBinding binding;

        public CourseViewHolder(@NonNull ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Course course) {
            // Set course title and description
            binding.textCourseTitle.setText(course.getTitle());
            binding.textCourseDescription.setText(course.getDescription());

            // Set category with chip
            binding.textCategory.setText(course.getCategory());

            // Set estimated time
            binding.textEstimatedTime.setText(course.getFormattedTime());

            // Set category icon
            binding.imageCourseThumbnail.setImageResource(getCategoryIcon(course.getCategory()));

            // Set enroll button text
            binding.buttonEnroll.setText("Mulai Belajar");

            // Set click listeners
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(course, getAdapterPosition());
                }
            });

            // Enroll button click
            binding.buttonEnroll.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(course, getAdapterPosition());
                }
            });
        }

        private int getCategoryIcon(String category) {
            // Use same icon for all categories for now
            // You can create specific icons for each category later
            return R.drawable.ic_courses;
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
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getCategory().equals(newItem.getCategory()) &&
                    oldItem.getDifficulty() == newItem.getDifficulty() &&
                    oldItem.getEstimatedTime() == newItem.getEstimatedTime();
        }
    }
}