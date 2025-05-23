package com.usc.osservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CleanerAdapter extends RecyclerView.Adapter<CleanerAdapter.CleanerViewHolder> {

    private List<Cleaner> cleaners;
    private List<Cleaner> cleanersFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onFavoriteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CleanerAdapter(List<Cleaner> cleaners) {
        this.cleaners = cleaners;
        this.cleanersFull = new ArrayList<>(cleaners);
    }

    @NonNull
    @Override
    public CleanerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cleaner, parent, false);
        return new CleanerViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CleanerViewHolder holder, int position) {
        Cleaner cleaner = cleaners.get(position);
        holder.cleanerImage.setImageResource(cleaner.getImageResource());
        holder.cleanerName.setText(cleaner.getName());
        holder.ratingBar.setRating(cleaner.getRating());
        holder.ratingText.setText(String.valueOf(cleaner.getRating()));
        holder.cleanerAge.setText("Age: " + cleaner.getAge());
        holder.cleanerSchedule.setText("Available: " + cleaner.getSchedule());
        
        // Set services if they exist and the TextView is available
        if (holder.cleanerServices != null && cleaner.getServices() != null && !cleaner.getServices().isEmpty()) {
            String servicesText = "Services: " + String.join(", ", cleaner.getServices());
            holder.cleanerServices.setText(servicesText);
            holder.cleanerServices.setVisibility(View.VISIBLE);
        } else if (holder.cleanerServices != null) {
            holder.cleanerServices.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cleaners.size();
    }
    
    public void filter(String text) {
        cleaners.clear();
        if (text.isEmpty()) {
            cleaners.addAll(cleanersFull);
        } else {
            final String searchText = text.toLowerCase(); // Make it final
            for (Cleaner cleaner : cleanersFull) {
                if (cleaner.getName().toLowerCase().contains(searchText) ||
                    cleaner.getSchedule().toLowerCase().contains(searchText) ||
                    (cleaner.getServices() != null && 
                     cleaner.getServices().stream().anyMatch(service -> 
                        service.toLowerCase().contains(searchText)))) {
                    cleaners.add(cleaner);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class CleanerViewHolder extends RecyclerView.ViewHolder {
        ImageView cleanerImage;
        TextView cleanerName, ratingText, cleanerAge, cleanerSchedule, cleanerServices;
        RatingBar ratingBar;
        ImageButton favoriteButton;

        public CleanerViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            cleanerImage = itemView.findViewById(R.id.cleanerImage);
            cleanerName = itemView.findViewById(R.id.cleanerName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ratingText = itemView.findViewById(R.id.ratingText);
            cleanerAge = itemView.findViewById(R.id.cleanerAge);
            cleanerSchedule = itemView.findViewById(R.id.cleanerSchedule);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
            
            // Try to find services TextView (might not be in all layouts)
            cleanerServices = itemView.findViewById(R.id.cleanerServices);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

            favoriteButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onFavoriteClick(position);
                    }
                }
            });
        }
    }
}