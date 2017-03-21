package pe.com.dbs.beerapp.services;

import pe.com.dbs.beerapp.models.Customer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LoginService {

    @POST("/app/security/api/login")
    Call<Void> login(@Body Customer customer);

}
