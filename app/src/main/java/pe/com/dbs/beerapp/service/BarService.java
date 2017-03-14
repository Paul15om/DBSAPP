package pe.com.dbs.beerapp.service;

import java.util.List;

import pe.com.dbs.beerapp.models.Bar;
import retrofit2.Call;
import retrofit2.http.GET;


public interface BarService {

    @GET("/app/bars")
    Call<List<Bar>> findAll();

}
