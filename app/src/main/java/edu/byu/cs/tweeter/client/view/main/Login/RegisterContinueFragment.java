package edu.byu.cs.tweeter.client.view.main.Login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;

public class RegisterContinueFragment extends Fragment implements RegisterPresenter.View {

    private RegisterPresenter presenter;

    public static final int PICK_IMAGE = 1;
    String imageUrl = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    ImageView photo;

    RegisterContinueFragment(RegisterPresenter presenter){
        this.presenter = presenter;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_info, container, false);
        final TextView changePhoto = view.findViewById(R.id.changePhoto);
        final EditText firstName = view.findViewById(R.id.firstNameRegister);
        final EditText lastName = view.findViewById(R.id.lastNameRegister);
        final EditText handle = view.findViewById(R.id.handlRegister);
        final Button signUpButton = view.findViewById(R.id.signUpButton);
        photo = view.findViewById(R.id.registerImage);



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)photo.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                byte[] byteArray = stream.toByteArray();
                String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                imageString = imageString.replace("\n","");
                RegisterTask registerTask = new RegisterTask(presenter, getActivity(), imageString);
                RegisterRequest request = new RegisterRequest(presenter.email, presenter.password,firstName.getText().toString(),lastName.getText().toString(),handle.getText().toString(), imageUrl);
                registerTask.execute(request);
//                Fragment fragment = getFragmentManager().findFragmentById(R.id.view_pager);
//                MainLoggedInActivity loggedIn = new MainLoggedInActivity();
//                RegisterContinueFragment f = (RegisterContinueFragment) getFragmentManager().findFragmentByTag("registerContinueFrag");
//                getFragmentManager().beginTransaction().remove(f).commit();
//                Intent intent = new Intent(getActivity(), MainLoggedInActivity.class);
//                getActivity().startActivity(intent);
            }
        });

        signUpButton.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkName(firstName) && checkName(lastName) && checkHandle(handle)){
                    signUpButton.setEnabled(true);
                } else {
                    signUpButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        handle.addTextChangedListener(textWatcher);

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//
//                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                pickIntent.setType("image/*");
//
//                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
//
//                startActivityForResult(chooserIntent, PICK_IMAGE);
//
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

//                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, PICK_IMAGE);

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , PICK_IMAGE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            imageUrl = data.getData().toString();
            photo.setImageURI(data.getData());
            //TODO: action
        }
    }

    public boolean checkHandle(EditText handle){
        System.out.println(handle.getText().length());
        if(handle.getText().length() > 0 && handle.getText().charAt(0) == '@'){
            return true;
        } else {
            return false;
        }
    }
    public boolean checkName(EditText name){
        System.out.println(name.getText().length());
        if(name.getText().length() > 0){
            return true;
        } else {
            return false;
        }
    }
}
