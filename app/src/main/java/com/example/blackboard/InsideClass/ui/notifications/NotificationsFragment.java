package com.example.blackboard.InsideClass.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
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

public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    LoginManager sessionManager;
    TextView teacher;
    ArrayList<ShowMembers> blackboardArrayList = new ArrayList<>();
    String URL_PRODUCTS="http://blackboard.himanshushekhar.ml/classMembers.php";
    String creator;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = (RecyclerView)root.findViewById(R.id.recycle_members);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        creator=getActivity().getIntent().getStringExtra("creator");
        teacher=root.findViewById(R.id.teacherMember);
        teacher.setText(getActivity().getIntent().getStringExtra("creator"));
        Toast.makeText(getContext(), getActivity().getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
        sessionManager=new LoginManager(getContext());
        Regist();

        return root;
    }


    public void Regist()
    {    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        //converting the string to json array object
                        System.out.println("response"+response);
                        JSONArray array=new JSONArray(response);
                        //traversing through all the object


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject word = array.getJSONObject(i);
                            ShowMembers showBlackboard=new ShowMembers();
                            showBlackboard.setEmail(word.optString("email"));
                            blackboardArrayList.add(showBlackboard);
                        }
                        //creating adapter object and setting it to recyclerview
                        MembersAdapter adapter = new MembersAdapter(getActivity(), blackboardArrayList);
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
        @Override
        protected Map<String,String> getParams() throws AuthFailureError
        {
            Map<String,String> params=new HashMap<>();
            params.put("id",getActivity().getIntent().getStringExtra("id"));
            return params;
        }
    };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}