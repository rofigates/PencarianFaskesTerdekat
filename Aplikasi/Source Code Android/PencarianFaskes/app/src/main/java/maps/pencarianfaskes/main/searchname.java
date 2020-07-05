package maps.pencarianfaskes.main;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maps.pencarianfaskes.R;
import maps.pencarianfaskes.requirement.RecyclerFaskesAdapter;
import maps.pencarianfaskes.requirement.RetrofitAPI;
import maps.pencarianfaskes.requirement.AppConfig;
import maps.pencarianfaskes.requirement.AllFaskes;
import maps.pencarianfaskes.requirement.Values;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class searchname extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private List<AllFaskes> allfaskess = new ArrayList<>();
    private RecyclerFaskesAdapter viewAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_faskes);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Faskes List");

        viewAdapter = new RecyclerFaskesAdapter(this, allfaskess);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        getDataAllfaskes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataAllfaskes();
    }

    private void getDataAllfaskes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.URL_MAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI api = retrofit.create(RetrofitAPI.class);
        Call<Values> call = api.viewAllLaundry();
        call.enqueue(new Callback<Values>() {
            @Override
            public void onResponse(Call<Values> call, Response<Values> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                if (value.equals("1")) {
                    allfaskess = response.body().getAllLaundries();
                    viewAdapter = new RecyclerFaskesAdapter(searchname.this, allfaskess);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Values> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Search by name...");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.URL_MAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI api = retrofit.create(RetrofitAPI.class);
        Call<Values> call = api.searchfaskes(newText);
        call.enqueue(new Callback<Values>() {
            @Override
            public void onResponse(Call<Values> call, Response<Values> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (value.equals("1")) {
                    allfaskess = response.body().getAllfaskess();
                    viewAdapter = new RecyclerFaskesAdapter(searchname.this, allfaskess);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Values> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return true;
    }
}
