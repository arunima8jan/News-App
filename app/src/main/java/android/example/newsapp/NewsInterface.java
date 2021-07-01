package android.example.newsapp;

import android.example.newsapp.ModelClasses.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {
    @GET("/v2/top-headlines?country=in&apiKey=9efab09775e34a038f57233cc829ffb4")
    Call<MainResponse> getNewsData(@Query("category") String category);
}
