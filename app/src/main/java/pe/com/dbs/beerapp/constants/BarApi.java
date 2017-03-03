package pe.com.dbs.beerapp.constants;

import pe.com.dbs.beerapp.models.Customer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by JeralBenites on 26/02/2017.
 */

public interface BarApi {

    @POST("/app/security/api/login")
    Call<Void> login(@Body Customer customer);

    /*@POST("security/api/login")
    Call<List<Customer>> login(@Header("Status") String token);*/


}