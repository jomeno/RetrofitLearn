package com.example.android.retrofitlearn.api;

import java.util.ArrayList;

import data.Operation;
import data.PackModel;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Jomeno on 9/14/2015.
 */
public interface MedaceService {
    @GET("/api/packx?ispublished=true")
    Call<Operation<ArrayList<PackModel>>> getPacks(@Query("ispublished") String ispublished);
}
