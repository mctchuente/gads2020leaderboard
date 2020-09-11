package com.andela.gads2020leaderboard.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.gads2020leaderboard.R;
import com.andela.gads2020leaderboard.model.LearnerHour;
import com.andela.gads2020leaderboard.model.LearnerSkillIQ;
import com.andela.gads2020leaderboard.ui.learning.LearningRecyclerViewAdapter;
import com.andela.gads2020leaderboard.ui.skilliq.SkilliqRecyclerViewAdapter;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private List<LearnerHour> learnerHourList;
    private List<LearnerSkillIQ> learnerSkillIQList;
    private RecyclerView recyclerView;
    private LearningRecyclerViewAdapter learningRecyclerViewAdapter;
    private SkilliqRecyclerViewAdapter skilliqRecyclerViewAdapter;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
        if (index == 2) {
            pageViewModel.setmLearnersSkilliqs();
        } else {
            pageViewModel.setmLearnersHours();
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        if (recyclerView != null) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                assert s != null;
                if(s.equals("SKILLIQLEADERS")) {
                    pageViewModel.setmLearnersSkilliqs();
                } else {
                    pageViewModel.setmLearnersHours();
                }
            }
        });
        pageViewModel.getmLearnersHours().observe(getViewLifecycleOwner(), new Observer<List<LearnerHour>>() {
            @Override
            public void onChanged(final List<LearnerHour> learnerHours) {
                if (learnerHourList == null) {
                    learnerHourList = learnerHours;
                } else {
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return learnerHourList.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return learnerHours.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            LearnerHour oldItem = learnerHourList.get(oldItemPosition);
                            LearnerHour newItem = learnerHours.get(newItemPosition);
                            return oldItem.getLearnerHours() == newItem.getLearnerHours() && oldItem.getLearnerName().equals(newItem.getLearnerName()) && oldItem.getLearnerCountry().equals(newItem.getLearnerCountry());
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            LearnerHour oldItem = learnerHourList.get(oldItemPosition);
                            LearnerHour newItem = learnerHours.get(newItemPosition);
                            return oldItem.equals(newItem);
                        }
                    });
                    result.dispatchUpdatesTo(learningRecyclerViewAdapter);
                    learnerHourList = learnerHours;
                }
                learningRecyclerViewAdapter = new LearningRecyclerViewAdapter(learnerHourList);
                recyclerView.setAdapter(learningRecyclerViewAdapter);
                learningRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        pageViewModel.getmLearnersSkilliqs().observe(getViewLifecycleOwner(), new Observer<List<LearnerSkillIQ>>() {
            @Override
            public void onChanged(final List<LearnerSkillIQ> learnerSkillIQS) {
                if (learnerSkillIQList == null) {
                    learnerSkillIQList = learnerSkillIQS;
                } else {
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return learnerSkillIQList.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return learnerSkillIQS.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            LearnerSkillIQ oldItem = learnerSkillIQList.get(oldItemPosition);
                            LearnerSkillIQ newItem = learnerSkillIQS.get(newItemPosition);
                            return oldItem.getLearnerScore() == newItem.getLearnerScore() && oldItem.getLearnerName().equals(newItem.getLearnerName()) && oldItem.getLearnerCountry().equals(newItem.getLearnerCountry());
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            LearnerSkillIQ oldItem = learnerSkillIQList.get(oldItemPosition);
                            LearnerSkillIQ newItem = learnerSkillIQS.get(newItemPosition);
                            return oldItem.equals(newItem);
                        }
                    });
                    result.dispatchUpdatesTo(skilliqRecyclerViewAdapter);
                    learnerSkillIQList = learnerSkillIQS;
                }
                skilliqRecyclerViewAdapter = new SkilliqRecyclerViewAdapter(learnerSkillIQList);
                recyclerView.setAdapter(skilliqRecyclerViewAdapter);
                skilliqRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }
}