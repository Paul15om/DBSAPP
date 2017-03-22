package pe.com.dbs.beerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orm.SugarRecord;

import java.util.List;
import java.util.Random;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.models.Bar;
import pe.com.dbs.beerapp.services.BarService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView textAlert;
    private Retrofit mRestAdapter;
    BarService barService;
    String BASE_URL = "https://dbsapp.herokuapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textAlert = (TextView) findViewById(R.id.LoadingTextView);
        progressBar = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        barService = mRestAdapter.create(BarService.class);

        new Thread(new Runnable() {
            public void run() {
                loadBars();
                doWork();
                finish();
                startApp();
            }
        }).start();
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress += 30) {
            try {
                Random random = new Random();
                int num = (int) (random.nextDouble() * 600 + 100);
                Thread.sleep(num);
                progressBar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }

    private void loadBars() {
        Call<List<Bar>> call = barService.findAll();
        call.enqueue(new Callback<List<Bar>>() {
            @Override
            public void onResponse(Call<List<Bar>> call, Response<List<Bar>> response) {

                if (response.body() == null) {
                    showMessage("BODY NULL");
                    return;
                }

                if (response.body().size() == 0) {
                    showMessage("VACIO");
                    return;
                }

                SugarRecord.deleteAll(BarService.class);
                SugarRecord.saveInTx(response.body());
                showMessage("Descargando Información..");
            }

            @Override
            public void onFailure(Call<List<Bar>> call, Throwable t) {
                showMessage(t.getMessage());
            }
        });
    }

    public void showMessage(String alert) {
        textAlert.setText(alert);
    }
}
