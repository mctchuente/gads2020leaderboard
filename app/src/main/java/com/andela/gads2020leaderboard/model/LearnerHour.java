package com.andela.gads2020leaderboard.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * LearnerHour
 *
 * @author Bertrand TCHUENTE
 */
public class LearnerHour implements Serializable {
    @SerializedName("hours")
    @Expose
    private int learnerHours;

    @SerializedName("name")
    @Expose
    private String learnerName;

    @SerializedName("badgeUrl")
    @Expose
    private String learnerBadgeUrl;

    @SerializedName("country")
    @Expose
    private String learnerCountry;

    public int getLearnerHours() {
        return learnerHours;
    }

    public void setLearnerHours(int learnerHours) {
        this.learnerHours = learnerHours;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
    }

    public String getLearnerBadgeUrl() {
        return learnerBadgeUrl;
    }

    public void setLearnerBadgeUrl(String learnerBadgeUrl) {
        this.learnerBadgeUrl = learnerBadgeUrl;
    }

    public String getLearnerCountry() {
        return learnerCountry;
    }

    public void setLearnerCountry(String learnerCountry) {
        this.learnerCountry = learnerCountry;
    }

    public String toJSON() {
        return new GsonBuilder().create().toJson(this, this.getClass());
    }

    @NonNull
    @Override
    public String toString() {
        return learnerName;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        //return super.equals(obj);
        boolean result = false;
        if (obj != null && obj.getClass() == getClass()) {
            LearnerHour learnerHour = (LearnerHour) obj;
            if (this.learnerHours == learnerHour.getLearnerHours() && this.learnerName.equals(learnerHour.getLearnerName()) && this.learnerCountry.equals(learnerHour.getLearnerCountry())) {
                result = true;
            }
        }
        return result;
    }
}
