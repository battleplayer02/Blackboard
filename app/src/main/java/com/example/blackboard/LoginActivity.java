package com.example.blackboard;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import javax.security.auth.login.LoginException;

public class LoginActivity extends AppCompatActivity {
    LoginManager session;
    EditText username;
    EditText password;
    String email;
    String id;
    ProgressDialog builder;
    @Override
    public void onStart() {
        super.onStart();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        builder = new ProgressDialog(LoginActivity.this);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        session = new LoginManager(this);
        username = ((EditText)findViewById(R.id.u));
        password = ((EditText)findViewById(R.id.p));

        ((TextView)findViewById(R.id.fp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });
        ((TextView)findViewById(R.id.create_new_account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,NewAccount.class));
                finish();
            }
        });
        ((Button)findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Logging You In......");
                builder.create();
                builder.show();
                if(password.getText().toString().equals("") && username.getText().toString().equals("")){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("Email and password Required")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }
                if(password.getText().toString().equals("") || username.getText().toString().equals("")){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("Email and password Required")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }
                login(username.getText().toString(),password.getText().toString());
            }
        });
    }

    public void login(final String e, final String p){
//                        Toast.makeText(LoginActivity.this, e+p, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://blackboard.himanshushekhar.ml/loginPage.php"
                ,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    Log.e("ss", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
//                    Toast.makeText(LoginActivity.this, success, Toast.LENGTH_SHORT).show();
                    if (success.equals("1")){
                        session.createLoginSession(object.getString("id"),object.getString("email"),object.getString("password"),object.getString("contact_number"),object.getString("name"));
                        builder.dismiss();
                        startActivity(new Intent(LoginActivity.this,LoginSucessful.class));
                    }
                    if (success.equals("0")){
                        builder.dismiss();
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage("Login Unsuccess")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                    }
                }catch (Exception e){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("Error5"+e.getMessage())
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
                error.printStackTrace();
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("Error6"+error.getMessage())
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
                System.out.println("e+p"+"="+e+p);
                params.put("email",e);
                params.put("password",p);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
}