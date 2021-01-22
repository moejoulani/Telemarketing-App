package com.example.telemarketingapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Interface.ItemClickListener;
import com.example.telemarketingapp.R;


import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeTasksHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ItemClickListener listener;
    public Button DeleteTASK;
    public TextView subjectTASK,messageTASK,managerNameTASK;
    public CircleImageView circleTASK;

    public EmployeeTasksHolder(@NonNull View itemView) {
        super(itemView);
        DeleteTASK=(Button) itemView.findViewById(R.id.deleteTASKK);
        subjectTASK=(TextView) itemView.findViewById(R.id.subjectTASKK);
        messageTASK=(TextView)itemView.findViewById(R.id.messageTASKK);
        managerNameTASK=(TextView)itemView.findViewById(R.id.managernameTASKK);
        circleTASK=(CircleImageView)itemView.findViewById(R.id.circleTASKK);

    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener =listener;
    }
    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
