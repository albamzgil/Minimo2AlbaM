package edu.upc.dsa.minimo2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReposActivity extends AppCompatActivity {

    protected TextView numRepos;
    protected TextView numFollowing;
    protected TextView numFollowers;
    protected ImageView ImageView;
    public static final String MyPREFERENCES = "MyPrefs";
    ProgressBar bProgreso;
    RecyclerView recyclerView;
    APIREST apiRest;
    static final String BASEURL = "https://api.github.com/";
    final Logger log = Logger.getLogger(String.valueOf(ReposActivity.class));

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        recyclerView = findViewById(R.id.listFollowers);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiRest = retrofit.create(APIREST.class);

        SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPref.getString("Username","");
        //String username = getIntent().getStringExtra("username");

        bProgreso = findViewById(R.id.progressBar);
        bProgreso.setVisibility(View.VISIBLE);

        Call<User> call = apiRest.getInfoUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                final User user = response.body();
                log.info("Repos y following correctos");
                numRepos = findViewById(R.id.numRepos);
                numFollowing = findViewById(R.id.numFollowing);
                numFollowers = findViewById(R.id.numFollowers);
                ImageView = findViewById(R.id.imageView);
                numRepos.setText(String.valueOf(user.getPublic_repos()));
                numFollowing.setText(String.valueOf(user.getFollowing()));
                numFollowers.setText(String.valueOf(user.getFollowers()));
                Picasso.with(getApplicationContext()).load(user.getAvatar_url()).into(ImageView);

                Call<List<Repos>> call2 = apiRest.getListaRepositories(username);
                call2.enqueue(new Callback<List<Repos>>() {
                    @Override
                    public void onResponse(Call<List<Repos>> call2, Response<List<Repos>> response2) {
                        if(!response.isSuccessful()) {
                            log.info("Error" + response2.code());
                            return;
                        }

                        List<Repos> listaRepos = response2.body();
                        inicializarRecyclerView(listaRepos);
                        bProgreso.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Repos>> call2, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Code" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inicializarRecyclerView(List<Repos> repos){ //Inicializar RecyclerView
        Adapter adapter= new Adapter(this,repos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
