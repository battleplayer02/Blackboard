package com.example.blackboard.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blackboard.EnrollToClass;
import com.example.blackboard.InsideClass.InsideClass;
import com.example.blackboard.R;

import java.util.List;

public class BlackboardAdapter extends RecyclerView.Adapter<BlackboardAdapter.BlackboardViewHolder> {

    private Context mCtx;
    private List<Blackboard> blackboardList;


    public BlackboardAdapter(Context mCtx, List<Blackboard> blackboardList) {
        this.mCtx = mCtx;
        this.blackboardList = blackboardList;
    }

    @NonNull
    @Override
    public BlackboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_blackboard,null);
        return new BlackboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlackboardViewHolder holder, int position) {
        final Blackboard blackboard = blackboardList.get(position);
        holder.teacher.setText(blackboard.getTeacher());
        holder.className.setText(blackboard.className);
        holder.subject.setText(blackboard.getSubject());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, InsideClass.class);
                intent.putExtra("id",blackboard.id);
                intent.putExtra("creator",blackboard.getTeacher());
                mCtx.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return blackboardList.size();
    }

    class BlackboardViewHolder extends RecyclerView.ViewHolder{

        TextView teacher,subject,className;
        ImageView classImage;
        LinearLayout cardView;
        public BlackboardViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher = itemView.findViewById(R.id.textTeacher);
            subject = itemView.findViewById(R.id.textSubject);
            className = itemView.findViewById(R.id.textClassName);
            classImage = itemView.findViewById(R.id.classImage);
            cardView = itemView.findViewById(R.id.blackboard_cards);
        }
    }

}
