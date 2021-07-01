package android.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.example.newsapp.ModelClasses.ArticlesItem;
import android.example.newsapp.ModelClasses.MainResponse;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
 private ProgressBar progressBar;
 private RecyclerView newsRecycler;
 private Retrofit retrofit;
 private NewsInterface newsInterface;
 private ActionBar actionBar;
    String generalCategory="General",healthCategory="Health",sportsCategory="sports",technologyCategory="Technology",businessCategory="Business";
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRecycler=findViewById(R.id.newsRecyclerView);
        bottomNavigationView=findViewById(R.id.bottomNavigation);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(getResources().getColor(R.color.action_bar)));
        actionBar=getSupportActionBar();
        actionBar.setTitle("General");
        setNewsRetrofit("general");
        setNavigationListener();




    }

    private void setNavigationListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.general:
                        actionBar.setTitle("General");
                    setNewsRetrofit(generalCategory);
                        return true;
                    case R.id.technology:

                        actionBar.setTitle("Technology");
                        setNewsRetrofit("technology");
                        return true;
                    case R.id.health:

                        actionBar.setTitle("Health");
                        setNewsRetrofit("health");
                        return true;
                    case R.id.business:

                        actionBar.setTitle("Business");
                        setNewsRetrofit("business");
                        return true;
                    case R.id.sports:

                        actionBar.setTitle("Sports");
                        setNewsRetrofit("sports");
                        return true;

                    default: return false;

                }
            }
        });
    }

    private void setNewsRetrofit(String category) {
        progressBar.setVisibility(View.VISIBLE);
        newsRecycler.setVisibility(View.INVISIBLE);

        retrofit=new Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).build();
        newsInterface=retrofit.create(NewsInterface.class);
        Call<MainResponse> responseCall=newsInterface.getNewsData(category);
        responseCall.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if ((response.isSuccessful())){
                    MainResponse mainResponse=response.body();
                    List<ArticlesItem> news=mainResponse.getArticles();
                    newsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    NewsAdapter adapter=new NewsAdapter(MainActivity.this,news);
                    newsRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    newsRecycler.setVisibility(View.VISIBLE);
                }
                else{}
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });
    }
}