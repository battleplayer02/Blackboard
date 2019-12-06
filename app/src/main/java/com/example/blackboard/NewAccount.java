package com.example.blackboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class NewAccount extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText email,pw,cnf_pw;
    String URL_REQUEST;
    String line = "",result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        email = (EditText)findViewById(R.id.email);
        pw =  (EditText)findViewById(R.id.new_password);
        cnf_pw =  (EditText)findViewById(R.id.cnf_new_password);

        ((Button)findViewById(R.id.btn_next_page)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if(email.getText().toString().equals("")){
                    Toast.makeText(NewAccount.this, "Email Cannot Be Empty", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(NewAccount.this);
                    builder.setMessage("Email Required");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.create().show();
                }
                else if (!pw.getText().toString().equals(cnf_pw.getText().toString())){
                    Toast.makeText(NewAccount.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(NewAccount.this);
                    builder.setMessage("Password Doesn't Match");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.create().show();
                }
                else {
                    URL_REQUEST = "http://blackboard.himanshushekhar.ml/createaccount.php";
                    register(email.getText().toString(),pw.getText().toString());
                }
            }
        });
    }

    public void register(final String email, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_REQUEST
            ,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    if (success.equals("1")){
                        new AlertDialog.Builder(NewAccount.this)
                                .setMessage("Registration Success")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        login(email,password);
                                        Intent intent = new Intent(NewAccount.this,AccountDetails.class);
                                        intent.putExtra("email",email);
                                        progressDialog.dismiss();
                                        startActivity(intent);
                                    }
                                })
                        .create()
                        .show();
                    }
                }catch (Exception e){
                    new AlertDialog.Builder(NewAccount.this)
                            .setMessage("Error"+e.getMessage()+response)
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
                new AlertDialog.Builder(NewAccount.this)
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
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(NewAccount.this);
        requestQueue.add(stringRequest);
    }

    public void login(final String e, final String p){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://blackboard.himanshushekhar.ml/loginPage.php",new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    LoginManager session = new LoginManager(NewAccount.this);
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    if (success.equals("1")){
                        String id = object.getString("id");
                        String email = object.getString("email");
                        session.createLoginSession(id,email,object.getString("password"),object.getString("contact_number"),object.getString("name"));
                    }

                }catch (Exception e){
                    new AlertDialog.Builder(NewAccount.this)
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
                new AlertDialog.Builder(NewAccount.this)
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
                params.put("email",e);
                params.put("password",p);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(NewAccount.this);
        requestQueue.add(stringRequest);
    }
}