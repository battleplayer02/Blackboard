package com.example.blackboard.InsideClass.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blackboard.InsideClass.InsideClass;
import com.example.blackboard.R;
import com.example.blackboard.ui.home.Blackboard;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.BlackboardViewHolder> {

    private Context mCtx;
    private ArrayList<Data> data;


    public DataAdapter(Context mCtx, ArrayList<Data> data) {
        this.mCtx = mCtx;
        this.data = data;
    }

    @NonNull
    @Override
    public BlackboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.content_blackboard,null);
        return new BlackboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlackboardViewHolder holder, int position) {
        Data val = data.get(position);
        holder.date_and_time.setText(val.getDate_and_time());
        holder.uploaded_by.setText(val.getUploaded_by());
        holder.data.setText(val.getData());
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class BlackboardViewHolder extends RecyclerView.ViewHolder{

        TextView uploaded_by;
        TextView data;
        TextView date_and_time;
        public BlackboardViewHolder(@NonNull View itemView) {
            super(itemView);
            uploaded_by = itemView.findViewById(R.id.sendername);
            data = itemView.findViewById(R.id.data);
            date_and_time = itemView.findViewById(R.id.time);
        }
    }

}
