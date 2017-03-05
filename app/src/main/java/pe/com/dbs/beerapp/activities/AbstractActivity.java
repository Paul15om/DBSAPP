package pe.com.dbs.beerapp.activities;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pe.com.dbs.beerapp.constants.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by jalvarea on 03/03/2017.
 */

public abstract class AbstractActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://dbsapp.herokuapp.com/";

    protected Retrofit retrofit;

    public AbstractActivity(){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request requestOriginal = chain.request();

                Request request = requestOriginal.newBuilder()
                        .header(Constant.AUTH_TOKEN, Constant.authToken)
                        .method(requestOriginal.method(),requestOriginal.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient okHttpClient = okHttpClientBuilder
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public <T> void execute(Call<T> call){

        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                System.err.println(t.getStackTrace());

            }

        });
    }

}
