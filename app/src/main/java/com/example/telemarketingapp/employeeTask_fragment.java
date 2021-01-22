package com.example.telemarketingapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Model.Tasks;
import com.example.telemarketingapp.Prevalent.EmployeePrevalent;
import com.example.telemarketingapp.ViewHolder.EmployeeTasksHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class employeeTask_fragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employeetask_fff,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView =view.findViewById(R.id.rec_emp_task);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loading=new ProgressDialog(getContext());

    }

    @Override
    public void onStart() {
        super.onStart();
        loading.setCanceledOnTouchOutside(false);
        loading.setTitle("Loading Tasks....");
        loading.show();
        final FirebaseFirestore db =FirebaseFirestore.getInstance();
        final Query query=db.collection("TasksView").whereEqualTo("company_email", EmployeePrevalent.currentOnlineEmployee.getCompany_email()).whereEqualTo("employee",EmployeePrevalent.currentOnlineEmployee.getUsername());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    QuerySnapshot ttt =task.getResult();
                    if(ttt.isEmpty())
                    {
                        Toast.makeText(getContext(),"Is EMpty",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getContext(),"inside is successfull",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    FirestoreRecyclerOptions<Tasks> options =new FirestoreRecyclerOptions.Builder<Tasks>().setQuery(query,Tasks.class).build();
                    FirestoreRecyclerAdapter<Tasks, EmployeeTasksHolder> adapter=new FirestoreRecyclerAdapter<Tasks, EmployeeTasksHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull EmployeeTasksHolder holder, int position, @NonNull final Tasks model) {
                            Picasso.get().load(model.getCircle()).placeholder(R.drawable.employee_iconlol).into(holder.circleTASK);
                          Picasso.get().load(model.getCircle()).placeholder(R.drawable.employee_iconlol);
                            holder.managerNameTASK.setText(model.getManager());
                            holder.messageTASK.setText(model.getMessage());
                            holder.subjectTASK.setText(model.getSubject());
                            holder.DeleteTASK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    db.collection("TasksView").document(model.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(getActivity(),"Deleted Task Successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });


                        }

                        @NonNull
                        @Override
                        public EmployeeTasksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_item,parent,false);
                            EmployeeTasksHolder holder=new EmployeeTasksHolder(view);
                            return holder;
                        }
                    };
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                }
            }
        });
    }
}
// CHANGE DOCUMENT ID TO NUMBERS   .
// TEST THIS ACTIVITY ON MOBILE    .
// START WORKING WITH NOTIFICATION .
//  <<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>> //

