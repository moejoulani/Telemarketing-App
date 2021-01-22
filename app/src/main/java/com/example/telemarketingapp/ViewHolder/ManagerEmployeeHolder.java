package com.example.telemarketingapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Interface.ItemClickListener;
import com.example.telemarketingapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManagerEmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    ItemClickListener listener;
   public TextView empName;
    public Button empDeleteBtn;
    public TextView str;
    public CircleImageView imgo;
    public ManagerEmployeeHolder(@NonNull View itemView) {
        super(itemView);
        empName=(TextView)itemView.findViewById(R.id.employeeName);
        empDeleteBtn=(Button)itemView.findViewById(R.id.delete_btn_employee);
        str=(TextView)itemView.findViewById(R.id.strcat);
        imgo=(CircleImageView)itemView.findViewById(R.id.empo_image);


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
