package edu.byu.cs.tweeter.client.view.main.Login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.RegisterPresenter;

public class RegisterFragment extends Fragment implements RegisterPresenter.View {

    private RegisterPresenter presenter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_tab, container, false);
        presenter = new RegisterPresenter(this);

        final EditText email = view.findViewById(R.id.registerEmail);
        final EditText password = view.findViewById(R.id.registerPassword);
        Switch showPasswordSwitch = view.findViewById(R.id.registerShowPassword);
        final Button registerButton = view.findViewById(R.id.registerButtonContinue);

        final RegisterContinueFragment regContinue = new RegisterContinueFragment(presenter);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.email = email.getText().toString();
                presenter.password = password.getText().toString();
                getFragmentManager().beginTransaction().add(R.id.frameLayoutLogin, regContinue, "registerContinueFrag").commit();
            }
        });
        registerButton.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkEmail(email) && checkPassword(password)){
                    registerButton.setEnabled(true);
                } else {
                    registerButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        return view;
    }
    public boolean checkEmail(EditText email){
        if(email.getText().length() > 0 && email.getText().toString().contains("@")){
            return true;
        } else {
            return false;
        }
    }
    public boolean checkPassword(EditText password){
        if(password.getText().length() > 5){
            return true;
        } else {
            return false;
        }
    }

}
