package edu.upc.dsa.minimo2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIREST {

    @GET("/users/{username}")
    Call<User> getInfoUser(@Path("username") String user);

    @GET("/users/{username}/repos")
    Call<List<Repos>> getListaRepositories(@Path("username") String user);
}
