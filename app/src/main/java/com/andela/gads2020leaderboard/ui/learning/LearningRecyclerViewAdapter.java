package com.andela.gads2020leaderboard.ui.learning;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.gads2020leaderboard.R;
import com.andela.gads2020leaderboard.model.LearnerHour;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * LearningRecyclerViewAdapter
 *
 * @author Bertrand TCHUENTE
 */
public class LearningRecyclerViewAdapter extends RecyclerView.Adapter<LearningRecyclerViewAdapter.ViewHolder> {
    private List<LearnerHour> mValues;
    private int countItems = 0;

    public LearningRecyclerViewAdapter(List<LearnerHour> mValues) {
        this.mValues = mValues;
        if (mValues != null) {
            this.countItems = mValues.size();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.learning_recyclerview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mValues.isEmpty()) {
            holder.mItem = mValues.get(position);
            holder.mItemPosition = position;
            holder.itemLearnerFullname.setText(holder.mItem.getLearnerName());
            holder.itemLearnerHour.setText(String.format("%d %s, %s.", holder.mItem.getLearnerHours(), holder.itemView.getContext().getString(R.string.learning_hour), holder.mItem.getLearnerCountry()));
            if (holder.mItem.getLearnerBadgeUrl() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(holder.mItem.getLearnerBadgeUrl())
                        .placeholder(R.drawable.top_learner)
                        .error(R.drawable.top_learner)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.itemLearnerBadge);
            } else {
                holder.itemLearnerBadge.setImageResource(R.drawable.top_learner);
            }
        }
    }

    @Override
    public int getItemCount() {
        return countItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View itemView;
        public ImageView itemLearnerBadge;
        public TextView itemLearnerFullname;
        public TextView itemLearnerHour;
        public LearnerHour mItem;
        public int mItemPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemLearnerBadge = (ImageView) itemView.findViewById(R.id.imageViewBadge);
            itemLearnerFullname = (TextView) itemView.findViewById(R.id.textViewFullname);
            itemLearnerHour = (TextView) itemView.findViewById(R.id.textViewHour);
        }
    }
}
