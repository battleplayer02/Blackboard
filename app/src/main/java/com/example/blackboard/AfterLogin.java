package com.example.blackboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Base64;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AfterLogin extends AppCompatActivity {
    DrawerLayout drawer;
    TextView email_show;
    ImageView img;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.enroll:
                startActivity(new Intent(AfterLogin.this,EnrollToClass.class));
                break;
            case R.id.create_class:
                startActivity(new Intent(AfterLogin.this,CreateClass.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        System.out.println("chala");
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View header=navigationView.getHeaderView(0);
        email_show=header.findViewById(R.id.email_show);
        img = header.findViewById(R.id.pro_img);
        email_show.setText(new LoginManager(AfterLogin.this).getUserDetails().get("email"));
        setImage();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_share,R.id.nav_forgotpassword)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.after_login, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setImage()
    {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://blackboard.himanshushekhar.ml/retrive_image.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("abcd"+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("success");
                            if (success.equals("1"))
                            {
                                String pro_img=jsonObject.getString("img_path");
                                byte[] byteArray= Base64.decode(pro_img,Base64.DEFAULT);
                                Bitmap bmp= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                                img.setImageBitmap(bmp);
//                                AlertDialog.Builder builder=new AlertDialog.Builder(AfterLogin.this)
//                                        .setMessage("Image set successfully")
//                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                            }
//                                        });
//                                builder.create().show();
                            }
                            else
                            {
//                                AlertDialog.Builder builder=new AlertDialog.Builder(AfterLogin.this)
//                                        .setMessage("Else Image set Unsuccessfully")
//                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                            }
//                                        });
//                                builder.create().show();
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
//                            AlertDialog.Builder builder=new AlertDialog.Builder(AfterLogin.this)
//                                    .setMessage("Image set Unsuccessfully" + ex.getMessage())
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                        }
//                                    });
//                            builder.create().show();
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
                params.put("email",new LoginManager(AfterLogin.this).getUserDetails().get("email"));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(AfterLogin.this);
        requestQueue.add(stringRequest);
    }
}