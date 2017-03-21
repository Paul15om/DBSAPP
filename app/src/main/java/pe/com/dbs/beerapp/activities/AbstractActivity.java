package pe.com.dbs.beerapp.activities;

import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

import pe.com.dbs.beerapp.factories.RetrofitFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public abstract class AbstractActivity extends AppCompatActivity {

    protected final Retrofit retrofit;

    public AbstractActivity() {
        retrofit = RetrofitFactory.getRetrofit();
    }

    public <T> void execute(Call<T> call) {

        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                System.err.println(Arrays.toString(t.getStackTrace()));

            }

        });
    }

}
