package com.example.blackboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateClass extends AppCompatActivity {
    LoginManager loginManager;

    String id;
    Button button;
    EditText subject,name,section;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);


        loginManager = new LoginManager(this);
        id = loginManager.getUserDetails().get(LoginManager.KEY_ID);
        Toast.makeText(this, loginManager.getUserDetails().get(LoginManager.KEY_ID), Toast.LENGTH_SHORT).show();
        subject = (EditText)findViewById(R.id.subject);
        name = (EditText)findViewById(R.id.name);
        name.setText(loginManager.getUserDetails().get(LoginManager.KEY_NAME));
        name.setFocusable(false);
        name.setClickable(false);
        section = (EditText)findViewById(R.id.section);
        button = (Button) findViewById(R.id.create_blackboard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create(name.getText().toString(),subject.getText().toString(),section.getText().toString());
            }
        });
    }


    public void create(final String name, final String subject, final String section){
        Toast.makeText(this, "fun call", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://blackboard.himanshushekhar.ml/createblackboard.php"
                ,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    Log.e("abcd", "onResponse: "+response);
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    System.out.println(response);
                    if (success.equals("1")){
                        id = loginManager.getUserDetails().get("id");
                        new AlertDialog.Builder(CreateClass.this)
                                .setMessage("CREATED BLACKBOARD")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(CreateClass.this,AfterLogin.class);
                                        startActivity(intent);
                                    }
                                })
                                .create()
                                .show();
                    }
                    else {
                        new AlertDialog.Builder(CreateClass.this)
                                .setMessage("ERROR")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(CreateClass.this,AfterLogin.class);
                                        startActivity(intent);
                                    }
                                })
                                .create()
                                .show();
                    }
                }catch (Exception e){
                    new AlertDialog.Builder(CreateClass.this)
                            .setMessage("Error"+e.getMessage())
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(CreateClass.this)
                        .setMessage("Error"+error.getMessage())
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("id",id);
                params.put("subject",subject);
                params.put("section",section);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CreateClass.this);
        requestQueue.add(stringRequest);
    }
}
