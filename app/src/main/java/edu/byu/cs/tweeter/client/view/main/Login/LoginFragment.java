package edu.byu.cs.tweeter.client.view.main.Login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;

public class LoginFragment extends Fragment implements LoginPresenter.View {

    private LoginPresenter presenter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.sign_in_tab, container, false);

        presenter = new LoginPresenter(this);

        final EditText email = view.findViewById(R.id.loginEmail);
        final EditText password = view.findViewById(R.id.loginPassword);
        final Button signIn = view.findViewById(R.id.loginButton);
        TextView noAccount = view.findViewById(R.id.loginNoAccount);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginTask loginTask = new LoginTask(presenter, getActivity());
                LoginRequest request = new LoginRequest(email.getText().toString(), password.getText().toString());
                loginTask.execute(request);


                System.out.println("HELLO");
            }
        });

        signIn.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkEmail(email) && checkPassword(password)){
                    signIn.setEnabled(true);
                } else {
                    signIn.setEnabled(false);
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
        if(email.getText().length() > 0 && email.getText().charAt(0) == '@'){
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
