package pe.com.dbs.beerapp.services;

import java.util.List;

import pe.com.dbs.beerapp.models.Catalog;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CatalogService {

    @GET("/app/catalogs/bar/{barId}")
    Call<List<Catalog>> findByBar(@Path("barId") Integer barId);

}
