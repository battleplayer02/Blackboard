package com.example.blackboard.InsideClass.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blackboard.R;

import java.util.ArrayList;

public class MembersAdapter  extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder>{
    private Context mCtx;
    ArrayList<ShowMembers> blackboardList;
    public MembersAdapter(FragmentActivity activity, ArrayList<ShowMembers> blackboardArrayList) {
        this.mCtx = activity;
        this.blackboardList = blackboardArrayList;
    }



    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.members,null);
        return new MembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder holder, int position) {
        ShowMembers blackboard = blackboardList.get(position);
        holder.member.setText(blackboard.getEmail());
    }



    @Override
    public int getItemCount() {
        return blackboardList.size();
    }

    class MembersViewHolder extends RecyclerView.ViewHolder{

        public TextView member;
        public MembersViewHolder(@NonNull View itemView) {
            super(itemView);
            member = itemView.findViewById(R.id.textMember);

        }
    }
}