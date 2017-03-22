package pe.com.dbs.beerapp.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

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

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialog();
    }

    private AlertDialog createLoginDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_sign_up, null);
        builder.setView(v);
        Button signUp = (Button) v.findViewById(R.id.signUpButton);
        final EditText emailSingUp = (EditText) v.findViewById(R.id.emailSingUp);
        final EditText passwordSingUp = (EditText) v.findViewById(R.id.passwordSingUp);
        final EditText dateBornSingUp = (EditText) v.findViewById(R.id.dateBornSingUp);
        dateBornSingUp.setShowSoftInputOnFocus(false);
        dateBornSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int YEAR_18 = c.get(Calendar.YEAR) - 19;
                DatePickerDialog dpd = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateBornSingUp.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, YEAR_18, c.get(Calendar.MONTH), c.get(Calendar.DATE));
                dpd.show();
            }
        });
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
                                // if (response.code() == 200) {
                                dismiss();
                                //Toast.makeText(v.getContext(), "Se registro correctamente el usuario", Toast.LENGTH_LONG).show();
                                //}
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(v.getContext(), "entre3", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
        return builder.create();
    }
}

