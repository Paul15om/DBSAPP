package pe.com.dbs.beerapp.factories;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pe.com.dbs.beerapp.constants.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class RetrofitFactory {

    private static final String BASE_URL = "https://dbsapp.herokuapp.com/";

    private static Retrofit retrofit;
    private static Retrofit retrofitLogin;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.addInterceptor(new Interceptor() {

                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request requestOriginal = chain.request();

                    Request request = requestOriginal.newBuilder()
                            .header(Constant.AUTH_TOKEN, Constant.authToken)
                            .method(requestOriginal.method(), requestOriginal.body())
                            .build();
                    return chain.proceed(request);
                }

            });

            retrofit = getRetrofit(okHttpClientBuilder);
        }
        return retrofit;
    }

    public static Retrofit getRetrofitLogin() {
        if (retrofitLogin == null) {
            retrofitLogin = getRetrofit(new OkHttpClient().newBuilder());
        }
        return retrofitLogin;
    }

    private static Retrofit getRetrofit(OkHttpClient.Builder okHttpClientBuilder) {
        OkHttpClient okHttpClient = okHttpClientBuilder
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    private RetrofitFactory() {
    }

}
