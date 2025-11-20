package com.codelearn.android.ui.courses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.codelearn.android.databinding.ItemLearningOutcomeBinding;

/**
 * RecyclerView adapter for learning outcomes
 */
public class LearningOutcomesAdapter extends ListAdapter<String, LearningOutcomesAdapter.LearningOutcomeViewHolder> {

    public LearningOutcomesAdapter() {
        super(new DiffUtilCallback());
    }

    @NonNull
    @Override
    public LearningOutcomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLearningOutcomeBinding binding = ItemLearningOutcomeBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new LearningOutcomeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningOutcomeViewHolder holder, int position) {
        String outcome = getItem(position);
        holder.bind(outcome);
    }

    static class LearningOutcomeViewHolder extends RecyclerView.ViewHolder {
        private final ItemLearningOutcomeBinding binding;

        public LearningOutcomeViewHolder(@NonNull ItemLearningOutcomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String outcome) {
            binding.textLearningOutcome.setText(outcome);
        }
    }

    private static class DiffUtilCallback extends DiffUtil.ItemCallback<String> {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    }
}