package pe.com.dbs.beerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import java.util.Random;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.activities.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);

        new Thread(new Runnable() {
            public void run() {
                doWork();
                finish();
                startApp();

            }
        }).start();

    }

    private void doWork() {
        for (int progress=0; progress<100; progress+=30) {
            try {
                Random rnd = new Random();
                int num =(int)(rnd.nextDouble() * 600 + 100);
                Thread.sleep(num);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }
}