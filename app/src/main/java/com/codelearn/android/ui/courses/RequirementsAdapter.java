package com.codelearn.android.ui.courses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.codelearn.android.databinding.ItemRequirementBinding;

/**
 * RecyclerView adapter for course requirements
 */
public class RequirementsAdapter extends ListAdapter<String, RequirementsAdapter.RequirementViewHolder> {

    public RequirementsAdapter() {
        super(new DiffUtilCallback());
    }

    @NonNull
    @Override
    public RequirementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRequirementBinding binding = ItemRequirementBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RequirementViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequirementViewHolder holder, int position) {
        String requirement = getItem(position);
        holder.bind(requirement);
    }

    static class RequirementViewHolder extends RecyclerView.ViewHolder {
        private final ItemRequirementBinding binding;

        public RequirementViewHolder(@NonNull ItemRequirementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String requirement) {
            binding.textRequirement.setText(requirement);
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