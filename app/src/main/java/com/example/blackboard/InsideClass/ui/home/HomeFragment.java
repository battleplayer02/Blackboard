package com.example.blackboard.InsideClass.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blackboard.LoginManager;
import com.example.blackboard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    DataAdapter dataAdapter;
    ArrayList<Data> messages;
    String id;
    EditText data;
    ImageView submit;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // create our mysql database connection
        id = getActivity().getIntent().getStringExtra("id");

        submit = root.findViewById(R.id.enter_chat1);
        data = root.findViewById(R.id.chat_edit_text1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendData(data.getText().toString());
                    data.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        recyclerView = (RecyclerView)root.findViewById(R.id.chat_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messages = new ArrayList();
        recyclerView.setAdapter(dataAdapter);
        setAllMessages();
        return root;
    }

    private void setAllMessages() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://blackboard.himanshushekhar.ml/messages.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                JSONArray array = new JSONArray(response);
                                if(messages.size()>=0){
                                    messages.clear();
                                }
                                for (int i=0;i<array.length();i++){
                                    JSONObject object = array.getJSONObject(i);
                                    messages.add(new Data(
                                            object.getString("sendername"),
                                            object.getString("time"),
                                            object.getString("data")
                                    ));
                                }

                                dataAdapter = new DataAdapter(getContext(),messages);
                                dataAdapter.notifyDataSetChanged();
                                recyclerView.setAdapter(dataAdapter);
//                                dataAdapter.notifyItemInserted(messages.size());
                            } catch (JSONException e) {
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
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap();
                    params.put("id",id);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

    }

    private void sendData(final String data) throws Exception{
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://blackboard.himanshushekhar.ml/sendmessgae.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("success");
                            if (success.equals("1")){

                                setAllMessages();
                            }
                        } catch (JSONException e) {
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
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap();
                        params.put("id",id);
                        params.put("user_id",new LoginManager(getContext()).getUserDetails().get("id"));
                        params.put("data",data);
                        return params;
                    }
                };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}