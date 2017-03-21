package pe.com.dbs.beerapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.factories.RetrofitFactory;
import pe.com.dbs.beerapp.models.Customer;
import pe.com.dbs.beerapp.services.CustomerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends DialogFragment {

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
                    public void onClick(final View v) {
                        Customer customer = new Customer();
                        customer.setEmail(emailSingUp.getText().toString());
                        customer.setPass(passwordSingUp.getText().toString());
                        customer.setBirthDate(dateBornSingUp.getText().toString());

                        CustomerService customerService = RetrofitFactory.getRetrofitLogin().create(CustomerService.class);
                        Call<Void> call = customerService.save(customer);

                        call.enqueue(new Callback<Void>() {

                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 200) {
                                    Toast.makeText(v.getContext(), "Se registro correctamente el usuario", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }

                        });
                    }
                }
        );
        return builder.create();
    }

}
