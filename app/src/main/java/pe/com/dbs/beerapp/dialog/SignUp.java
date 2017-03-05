package pe.com.dbs.beerapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import pe.com.dbs.beerapp.R;

public class SignUp extends DialogFragment {
    private static final String TAG = SignUp.class.getSimpleName();

    public SignUp() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    public AlertDialog createLoginDialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.activity_sign_up, null);

        builder.setView(v);

        Button signUp = (Button) v.findViewById(R.id.SignUpbuytton);

        signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
        return builder.create();
    }

}

