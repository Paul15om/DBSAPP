package pe.com.dbs.beerapp.activities;

import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

import pe.com.dbs.beerapp.factories.RetrofitFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public abstract class AbstractActivity extends AppCompatActivity {

    public static Retrofit retrofit;

    public AbstractActivity() {
        retrofit = RetrofitFactory.getRetrofit();
    }

}
