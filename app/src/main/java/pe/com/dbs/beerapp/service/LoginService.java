package pe.com.dbs.beerapp.service;

import pe.com.dbs.beerapp.models.Customer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jalvarea on 03/03/2017.
 */

public interface LoginService {

    @POST("/app/security/api/login")
    Call<Void> login(@Body Customer customer);

}
