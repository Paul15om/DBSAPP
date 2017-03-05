package pe.com.dbs.beerapp.service;

import java.util.List;

import pe.com.dbs.beerapp.models.Bar;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jalvarea on 04/03/2017.
 */

public interface BarService {

    @GET("/app/bars")
    Call<List<Bar>> findAll();

}
