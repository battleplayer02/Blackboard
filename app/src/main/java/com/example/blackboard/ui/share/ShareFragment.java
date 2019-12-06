package com.example.blackboard.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;


import com.example.blackboard.LoginActivity;
import com.example.blackboard.LoginManager;
import com.example.blackboard.R;

public class ShareFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        new LoginManager(getContext()).logoutUser();
        getActivity().finish();
        root.getContext().startActivity(new Intent(getContext(), LoginActivity.class));
        return root;
    }
}