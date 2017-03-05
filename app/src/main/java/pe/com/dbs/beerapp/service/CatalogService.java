package pe.com.dbs.beerapp.service;

import java.util.List;

import pe.com.dbs.beerapp.models.Catalog;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jalvarea on 04/03/2017.
 */

public interface CatalogService {

    @GET("/app/catalogs/bar/{barId}")
    Call<List<Catalog>> findByBar(@Path("barId") Integer barId);

}
