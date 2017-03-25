package pe.com.dbs.beerapp.services;

import pe.com.dbs.beerapp.models.Customer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomerService {

    @POST("/app/customers")
    Call<Void> save(@Body Customer customer);

}
