package com.andela.gads2020leaderboard.ui.skilliq;

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
import com.andela.gads2020leaderboard.model.LearnerSkillIQ;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * SkilliqRecyclerViewAdapter
 *
 * @author Bertrand TCHUENTE
 */
public class SkilliqRecyclerViewAdapter extends RecyclerView.Adapter<SkilliqRecyclerViewAdapter.ViewHolder> {
    private List<LearnerSkillIQ> mValues;
    private int countItems = 0;

    public SkilliqRecyclerViewAdapter(List<LearnerSkillIQ> mValues) {
        this.mValues = mValues;
        if (mValues != null) {
            this.countItems = mValues.size();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_iq_recyclerview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mValues.isEmpty()) {
            holder.mItem = mValues.get(position);
            holder.mItemPosition = position;
            holder.itemLearnerFullname.setText(holder.mItem.getLearnerName());
            holder.itemLearnerScore.setText(String.format("%d %s, %s", holder.mItem.getLearnerScore(), holder.itemView.getContext().getString(R.string.skill_iq_score), holder.mItem.getLearnerCountry()));
            if (holder.mItem.getLearnerBadgeUrl() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(holder.mItem.getLearnerBadgeUrl())
                        .placeholder(R.drawable.skill_iq)
                        .error(R.drawable.skill_iq)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.itemLearnerBadge);
            } else {
                holder.itemLearnerBadge.setImageResource(R.drawable.skill_iq);
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
        public TextView itemLearnerScore;
        public LearnerSkillIQ mItem;
        public int mItemPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemLearnerBadge = (ImageView) itemView.findViewById(R.id.imageViewBadge);
            itemLearnerFullname = (TextView) itemView.findViewById(R.id.textViewFullname);
            itemLearnerScore = (TextView) itemView.findViewById(R.id.textViewScore);
        }
    }
}
