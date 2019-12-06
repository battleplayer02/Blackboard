package com.example.blackboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AccountDetails extends AppCompatActivity {
    RadioGroup gender;
    EditText name,ctnno;
    int f1=0,f2=0;
    Button pro_img,login;
    String g,email;
    private ImageView imageView;
    ProgressDialog progressDialog;
    private final int GALLERY = 1;
    private String upload_URL = "http://blackboard.himanshushekhar.ml/uploadVolley.php";
    JSONObject jsonObject;
    RequestQueue rQueue;
    private static String URL_REGIST="http://blackboard.himanshushekhar.ml/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        imageView = (ImageView)findViewById(R.id.iv);
        login=(Button)findViewById(R.id.login);
        name=(EditText)findViewById(R.id.name);
        ctnno=(EditText)findViewById(R.id.ctn_no);
        gender=(RadioGroup)findViewById(R.id.gender);
        email=getIntent().getStringExtra("email");
        pro_img=(Button)findViewById(R.id.btn_pro_img);


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==1){
                    g = "Male";
                }
                else {
                    g = "Female";
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountDetails.this,g+""+email,Toast.LENGTH_LONG).show();
                progressDialog.show();
                Regist();

            }
        });

        pro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY);
            }
        });


    }
    public void fun(){

        if (f1==f2 && f1==1){
            progressDialog.dismiss();
            startActivity(new Intent(AccountDetails.this,AfterLogin.class));
            finish();
        }
    }


    public void Regist()
    {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("success");
                            if (success.equals("1"))
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(AccountDetails.this)
                                        .setMessage("Details Saved")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(AccountDetails.this,AfterLogin.class));
                                                finish();
                                            }
                                        });
                                builder.create().show();
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            AlertDialog.Builder builder=new AlertDialog.Builder(AccountDetails.this)
                                    .setMessage("Error")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    });
                            builder.create().show();
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
                params.put("email",email);
                params.put("name",name.getText().toString());
                params.put("gender",g);
                params.put("contact",ctnno.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(AccountDetails.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageView.setImageBitmap(bitmap);
                    uploadImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AccountDetails.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        try {
            jsonObject = new JSONObject();
            String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
            jsonObject.put("email",email);
            //  Log.e("Image name", etxtUpload.getText().toString().trim());
            jsonObject.put("image", encodedImage);
            // jsonObject.put("aa", "aa");
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("JSONObject Here", e.toString());
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://blackboard.himanshushekhar.ml/uploadVolley.php", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("aaaaaaa", jsonObject.toString());
                        rQueue.getCache().clear();
                        f2 = 1;
                        fun();
                        Toast.makeText(getApplication(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AccountDetails.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("aaaaaaa", volleyError.toString());
            }
        });
        System.out.println(jsonObject);
        rQueue = Volley.newRequestQueue(this);
        rQueue.add(jsonObjectRequest);
    }
}