package com.example.blackboard.ui.UpdateProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blackboard.LoginManager;
import com.example.blackboard.R;

public class UpdateProfile extends Fragment {
    EditText name,email,contact;
    String e,sessionemail,sessionPassword;
    LoginManager sessionManager,sessionManager1;
    Button btsave;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_profile, container, false);
        name = (EditText) root.findViewById(R.id.profile_name);
        email = (EditText) root.findViewById(R.id.profile_email);
        contact = (EditText) root.findViewById(R.id.profile_contact);
        sessionManager = new LoginManager(getContext());
        sessionManager1 = new LoginManager(getContext());
        sessionemail = sessionManager.getUserDetails().get("email");
        Toast.makeText(getContext(), sessionemail, Toast.LENGTH_LONG).show();
        btsave = (Button) root.findViewById(R.id.btsave);
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;

    }

}