package com.example.blackboard;

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

import java.util.HashMap;
import java.util.Map;

public class EnrollToClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_to_class);

        ((Button)findViewById(R.id.join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = ((EditText)findViewById(R.id.code)).getText().toString();
                sendCode(code);
            }
        });

    }


    public void sendCode(final String code){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://blackboard.himanshushekhar.ml/joinclass.php"
                ,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    if (success.equals("1")){
                        new AlertDialog.Builder(EnrollToClass.this)
                                .setMessage("Joined Sucessfully")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(EnrollToClass.this,AfterLogin.class));
                                    }
                                })
                                .create()
                                .show();
                    }
                }catch (Exception e){
                    new AlertDialog.Builder(EnrollToClass.this)
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
                new AlertDialog.Builder(EnrollToClass.this)
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
                params.put("creator",new LoginManager(EnrollToClass.this).getUserDetails().get("email"));
                params.put("code",code);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EnrollToClass.this);
        requestQueue.add(stringRequest);
    }



}
