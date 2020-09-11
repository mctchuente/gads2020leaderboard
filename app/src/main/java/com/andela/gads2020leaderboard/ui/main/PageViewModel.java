package com.andela.gads2020leaderboard.ui.main;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.andela.gads2020leaderboard.model.LearnerHour;
import com.andela.gads2020leaderboard.model.LearnerSkillIQ;
import com.andela.gads2020leaderboard.service.AppExecutors;
import com.andela.gads2020leaderboard.utils.JSONParser;
import com.andela.gads2020leaderboard.utils.Utility;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageViewModel extends ViewModel {
    private static final String LOG_TAG = PageViewModel.class.getSimpleName();
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            if (input == 2) {
                return "SKILLIQLEADERS";
            }
            return "LEARNINGLEADERS";
        }
    });
    private MutableLiveData<List<LearnerHour>> mLearnersHours;
    private MutableLiveData<List<LearnerSkillIQ>> mLearnersSkilliqs;

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<LearnerHour>> getmLearnersHours() {
        if (mLearnersHours == null) {
            mLearnersHours = new MutableLiveData<List<LearnerHour>>();
        }
        return mLearnersHours;
    }

    public void setmLearnersHours() {
        if (mLearnersHours == null) {
            mLearnersHours = new MutableLiveData<List<LearnerHour>>();
        }
        loadLearnersHours();
    }

    public LiveData<List<LearnerSkillIQ>> getmLearnersSkilliqs() {
        if (mLearnersSkilliqs == null) {
            mLearnersSkilliqs = new MutableLiveData<List<LearnerSkillIQ>>();
        }
        return mLearnersSkilliqs;
    }

    public void setmLearnersSkilliqs() {
        if (mLearnersSkilliqs == null) {
            mLearnersSkilliqs = new MutableLiveData<List<LearnerSkillIQ>>();
        }
        loadLearnersSkilliqs();
    }

    private void loadLearnersHours() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Call<ResponseBody> callapi = Utility.getApiInterface().getTopLearnerHours();
                callapi.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody resultBody = response.body();
                        if (response.isSuccessful()) {
                            JSONArray retArray = JSONParser.convertResponseBodyToJsonArray(resultBody);
                            if (retArray != null) {
                                Type type = new TypeToken<List<LearnerHour>>() {
                                }.getType();
                                List<LearnerHour> learnerHours = Utility.getGson().fromJson(retArray.toString(), type);
                                if (learnerHours != null) {
                                    mLearnersHours.setValue(learnerHours);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        call.cancel();
                        Log.e(LOG_TAG, t.getLocalizedMessage(), t);
                    }
                });
            }
        });
    }

    private void loadLearnersSkilliqs() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Call<ResponseBody> callapi = Utility.getApiInterface().getSkillIQ();
                callapi.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody resultBody = response.body();
                        if (response.isSuccessful()) {
                            JSONArray retArray = JSONParser.convertResponseBodyToJsonArray(resultBody);
                            if (retArray != null) {
                                Type type = new TypeToken<List<LearnerSkillIQ>>() {
                                }.getType();
                                List<LearnerSkillIQ> learnerSkillIQS = Utility.getGson().fromJson(retArray.toString(), type);
                                if (learnerSkillIQS != null) {
                                    mLearnersSkilliqs.setValue(learnerSkillIQS);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        call.cancel();
                        Log.e(LOG_TAG, t.getLocalizedMessage(), t);
                    }
                });
            }
        });
    }
}