package pe.com.dbs.beerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Random;

import pe.com.dbs.beerapp.R;


public class SplashActivity extends AbstractActivity {
    private ProgressBar progressBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        new Thread(new Runnable() {
            public void run() {
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


}
