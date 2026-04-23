package com.example.preprocessor;

import okhttp3.ResponseBody;
import retrofit2.Call; // 이 부분이 반드시 retrofit2 여야 합니다.
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("save")
    Call<ResponseBody> saveData(@Body LearningRequest data);
}