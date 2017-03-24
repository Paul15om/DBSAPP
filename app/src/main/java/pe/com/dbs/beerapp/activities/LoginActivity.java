package pe.com.dbs.beerapp.activities;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.constants.Constant;
import pe.com.dbs.beerapp.dialogs.SignUp;
import pe.com.dbs.beerapp.factories.RetrofitFactory;
import pe.com.dbs.beerapp.models.Customer;
import pe.com.dbs.beerapp.services.LoginService;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView emailLogin;
    private EditText passwordLogin;
    private CallbackManager callbackManager;
    private Boolean status = false;
    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginService = RetrofitFactory.getRetrofitLogin().create(LoginService.class);
        emailLogin = (AutoCompleteTextView) findViewById(R.id.emailLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);
        LoginButton loginButton = (LoginButton) findViewById(R.id.loginButtonFacebook);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                try {
                                    emailLogin.setText(object.getString("email"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.com_facebook_loginview_cancel_action, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.com_facebook_internet_permission_error_message, Toast.LENGTH_SHORT).show();
            }
        });
        Button signInButton = (Button) findViewById(R.id.loginButton);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptLogin();
            }
        });

        TextView signUp = (TextView) findViewById(R.id.linkToLogin);
        signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                new SignUp().show(fragmentManager, "Sign Up");
            }
        });
        emailLogin.setText("george@dbs.com");
        passwordLogin.setText("1234");
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void showLoginError(String error) {
        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    private void attemptLogin() {

        emailLogin.setError(null);
        passwordLogin.setError(null);

        final String email = emailLogin.getText().toString();
        final String password = passwordLogin.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordLogin.setError(getString(R.string.error_invalid_password));
            focusView = passwordLogin;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            emailLogin.setError(getString(R.string.error_field_required));
            focusView = emailLogin;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailLogin.setError(getString(R.string.error_invalid_email));
            focusView = emailLogin;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {

            Call<Void> call = loginService.login(new Customer(email, password));

            BackgroundTask task = new BackgroundTask(LoginActivity.this);
            task.execute(call);
        }
    }

    private void showAppointmentsScreen() {
        startActivity(new Intent(LoginActivity.this, BarActivity.class));
        finish();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    private class BackgroundTask extends AsyncTask<Call<Void>, Void, Void> {

        private ProgressDialog dialog;

        BackgroundTask(LoginActivity activity) {
            this.dialog = new ProgressDialog(activity);
        }

        @Override
        protected Void doInBackground(Call<Void>... params) {
            try {
                Response<Void> response = params[0].execute();
                String authToken = response.headers().get(Constant.AUTH_TOKEN);
                if (authToken != null) {
                    Constant.authToken = authToken;
                }
                status = authToken != null;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
            dialog.setContentView(R.layout.custom_progressdialog);
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                if (status) {
                    showAppointmentsScreen();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.LoginNo), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
