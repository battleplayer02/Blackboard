package com.example.blackboard.InsideClass.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blackboard.AccountDetails;
import com.example.blackboard.AfterLogin;
import com.example.blackboard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {
    TextView nameofblackboard,subject,dateandtime,nameodcreator,email,contactnumber,code,sec;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        nameofblackboard = root.findViewById(R.id.nameofblackboard);
        subject = root.findViewById(R.id.subject);
        dateandtime = root.findViewById(R.id.date_and_time);
        nameodcreator = root.findViewById(R.id.nameofcreator);
        email = root.findViewById(R.id.email);
        sec = root.findViewById(R.id.section);
        contactnumber = root.findViewById(R.id.contact);
        code = root.findViewById(R.id.code);
        setData();
        return root;
    }


    public void setData()
    {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://blackboard.himanshushekhar.ml/sendData.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        JSONObject jsonObject;
                        try {
//                            jsonObject = new JSONObject(response);
//                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            JSONArray object = new JSONArray(response);
                            JSONObject jsonObject = (JSONObject) object.getJSONObject(0);
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            sec.setText(sec.getText()+jsonObject.getString("section"));
                            nameodcreator.setText(nameodcreator.getText()+jsonObject.getString("creator"));
                            subject.setText(subject.getText()+jsonObject.getString("subject"));
                            nameofblackboard.setText(nameofblackboard.getText()+jsonObject.getString("name"));
                            email.setText(email.getText()+jsonObject.getString("email"));
                            new Intent().putExtra("tmail",jsonObject.getString("code"));
                            contactnumber.setText(contactnumber.getText()+jsonObject.getString("contact_number"));
                            dateandtime.setText(dateandtime.getText()+"   :" + jsonObject.getString("creationtime"));
                            code.setText(jsonObject.getString("code"));
                            new Intent().putExtra("id",jsonObject.getString("code"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
        @Override
        protected Map<String,String> getParams() throws AuthFailureError
        {
            Map<String,String> params=new HashMap<>();
            params.put("id",getActivity().getIntent().getStringExtra("id"));
            return params;
        }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}