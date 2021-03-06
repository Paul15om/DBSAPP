package pe.com.dbs.beerapp.activities;

import android.support.v7.app.AppCompatActivity;

import pe.com.dbs.beerapp.factories.RetrofitFactory;
import retrofit2.Retrofit;


public abstract class AbstractActivity extends AppCompatActivity {

    private static Retrofit retrofit;

    public AbstractActivity() {
        setRetrofit(RetrofitFactory.getRetrofit());
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        AbstractActivity.retrofit = retrofit;
    }
}
