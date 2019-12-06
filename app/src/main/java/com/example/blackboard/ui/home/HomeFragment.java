package com.example.blackboard.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
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
    RecyclerView recyclerView;
    BlackboardAdapter blackboardAdapter;
    ArrayList<Blackboard> blackboardArrayList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hhh, container, false);
        recyclerView = (RecyclerView)root.findViewById(R.id.recycle_blackboards);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        Regist();
        return root;
    }


    public void Regist()
    {    StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://blackboard.himanshushekhar.ml/blackboard.php" ,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println(response);
                        //converting the string to json array object
                        JSONArray array=new JSONArray(response);
                        //traversing through all the object
                        if (blackboardArrayList.size()>=0){
                            blackboardArrayList.clear();
                        }
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject word = array.getJSONObject(i);
                            Blackboard showBlackboard=new Blackboard();
                            showBlackboard.setSubject(word.optString("subject"));
                            showBlackboard.setClassName(word.optString("section"));
                            showBlackboard.setTeacher(word.optString("creator"));
                            showBlackboard.setId(word.optString("id"));
                            blackboardArrayList.add(showBlackboard);
                        }
                        //creating adapter object and setting it to recyclerview
                        BlackboardAdapter adapter = new BlackboardAdapter(getActivity(), blackboardArrayList);
                        recyclerView.setAdapter(adapter);
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
                protected Map<String ,String> getParams(){
                    Map<String,String> parsms = new HashMap();
                    parsms.put("email",new LoginManager(getContext()).getUserDetails().get("email"));
                    return parsms;
                }
            };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }
}