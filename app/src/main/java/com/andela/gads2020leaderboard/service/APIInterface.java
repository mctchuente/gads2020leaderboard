package com.andela.gads2020leaderboard.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * APIInterface
 *
 * @author Bertrand TCHUENTE
 */
public interface APIInterface {
    @GET("/api/hours")
    Call<ResponseBody> getTopLearnerHours();
    @GET("/api/skilliq")
    Call<ResponseBody> getSkillIQ();
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    Call<Void> submitProject(@Field("entry.1824927963") String emailAddress, @Field("entry.1877115667") String firstName, @Field("entry.2006916086") String lastName, @Field("entry.284483984") String projectGithub);
}
