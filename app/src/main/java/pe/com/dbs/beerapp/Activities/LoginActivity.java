package pe.com.dbs.beerapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import pe.com.dbs.beerapp.R;

public class LoginActivity extends AppCompatActivity {
    private LoginButton _LoginButton;
    private CallbackManager _CallBackManager;
    private TextView _emailText,_passwordText;
    private Button _Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _Button = (Button) findViewById(R.id._LoginButton);
        _CallBackManager=CallbackManager.Factory.create();
        _LoginButton = (LoginButton) findViewById(R.id.loginButton);
        _emailText=(TextView)findViewById(R.id.EditTextEmail);
        _passwordText=(TextView)findViewById(R.id.EditTextPassword);
        _LoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_friends"));
        _LoginButton.registerCallback(_CallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                try {
                                    _emailText.setText(object.getString("email"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");
                request.setParameters(parameters);
                request.executeAsync();

                //goMainScreen();
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
        _Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), BarActivities.class));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _CallBackManager.onActivityResult(requestCode,resultCode,data);
    }
    protected void goMainScreen(){
        Intent intent =new Intent(this,BarActivities.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    protected boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getResources().getString(R.string.Email_error));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            _passwordText.setError(getResources().getString(R.string.Clave_error));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
    public void onBackPressed() {
    }
}
