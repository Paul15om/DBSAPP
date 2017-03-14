package pe.com.dbs.beerapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.models.Customer;

public class SignUp extends DialogFragment {

    private static final String TAG = SignUp.class.getSimpleName();

    public SignUp() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return createLoginDialog();
    }

    private AlertDialog createLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.activity_sign_up, null);

        builder.setView(v);

        Button signUp = (Button) v.findViewById(R.id.signUpButton);
        final EditText emailSingUp = (EditText) v.findViewById(R.id.emailSingUp);
        final EditText passwordSingUp = (EditText) v.findViewById(R.id.passwordSingUp);
        final EditText dateBornSingUp = (EditText) v.findViewById(R.id.dateBornSingUp);

        signUp.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Customer customer = new Customer();
                        customer.setEmail(emailSingUp.getText().toString());
                        customer.setPass(passwordSingUp.getText().toString());


                    }
                }
        );
        return builder.create();
    }


}
