package edu.upc.dsa.minimo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    APIREST apiRest;
    static final String BASEURL = "https://api.github.com/";
    final Logger log = Logger.getLogger(String.valueOf(MainActivity.class));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    //Boton para explorar followers
    public void exploreFollowers(View view) {
        Intent intent = new Intent(this, FollowerActivity.class);
        TextView user = (TextView) findViewById(R.id.username);
        String username = user.getText().toString();

        Call<User> call = apiRest.getInfoUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    log.info("Usuario introducido correctamente");
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
                else{
                    log.info("Usuario no encontrado");
                    Toast.makeText(getApplicationContext(),"Usuario no encontrado" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}