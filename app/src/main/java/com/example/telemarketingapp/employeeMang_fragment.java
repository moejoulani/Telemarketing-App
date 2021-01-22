package com.example.telemarketingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Model.Employee;
import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.example.telemarketingapp.ViewHolder.ManagerEmployeeHolder;
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

public class employeeMang_fragment extends Fragment {
    ProgressDialog loading,ll;
    RecyclerView VrecyclerView;
    String str="";
    String str2;
    RecyclerView.LayoutManager layoutManager;
    private Button add_new_employee;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employeemang_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        add_new_employee=(Button)view.findViewById(R.id.add_newEMPLO);
        layoutManager = new LinearLayoutManager(getContext());
        VrecyclerView = view.findViewById(R.id.employeereco);
        VrecyclerView.setHasFixedSize(true);
        VrecyclerView.setLayoutManager(layoutManager);
        loading=new ProgressDialog(getContext());
ll=new ProgressDialog(getContext());

        add_new_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),employeecreateacc.class);
                startActivity(intent);
            }
        });









    }

    @Override
    public void onStart() {
        super.onStart();
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        final Query query=db.collection("Employee").whereEqualTo("company_email", ManagerPrevalent.currentOnlineManagers.getCompany_email());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    loading.dismiss();
                    FirestoreRecyclerOptions<Employee> options=new FirestoreRecyclerOptions.Builder<Employee>().setQuery(query,Employee.class).build();
                    FirestoreRecyclerAdapter<Employee, ManagerEmployeeHolder> adapter=new FirestoreRecyclerAdapter<Employee, ManagerEmployeeHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final ManagerEmployeeHolder holder, int position, @NonNull final Employee model) {
                           holder.empName.setText(model.getUsername());
                          // holder.imgo.setImageResource(model.getImage());
                            Picasso.get().load(model.getImage()).placeholder(R.drawable.employee_iconlol).into(holder.imgo);
                            Picasso.get().load(model.getImage()).placeholder(R.drawable.employee_iconlol);
                            FirebaseFirestore db23= FirebaseFirestore.getInstance();
                            DocumentReference reference23=db23.collection("EmpCat").document(model.getUsername());
                            reference23.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        String soso="";
                                        ll.dismiss();
                                        DocumentSnapshot document = task.getResult();
                                        if(document.exists()) {

                                            for (Object item : task.getResult().getData().values()) {
                                                String[] values = String.valueOf(item).replace("[", "").replace("]", "").split(",");

                                                for (String value : values) {
                                                    soso+=" , "+value;

                                                }


                                            }
                                            holder.str.setText(soso);


                                            //Toast.makeText(getContext(),"get str "+getStr(),Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    // setStr(str);

                                }



                            });
                           //holder.str.setText( vv(model.getUsername()));

                           setStr("");
                           holder.empDeleteBtn.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   db.collection("Employee").document(model.getUsername()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           Toast.makeText(getActivity(),"Deleted Employee Successfully",Toast.LENGTH_SHORT).show();

                                       }
                                   });
                               }
                           });
                        }

                        @NonNull
                        @Override
                        public ManagerEmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.employeemangitem,parent,false);
                            ManagerEmployeeHolder employeeHolder=new ManagerEmployeeHolder(view);
                            return employeeHolder;
                        }
                    };
                    VrecyclerView.setAdapter(adapter);
                   adapter.startListening();
                }
            }
        });
    }
    public String vv(final String strr)
    {
        ll.show();
          final String srp="";
        final String stro=str;
       // Toast.makeText(getContext(),"username : "+strr,Toast.LENGTH_SHORT).show();
        FirebaseFirestore db23= FirebaseFirestore.getInstance();
        DocumentReference reference23=db23.collection("EmpCat").document(strr);
        reference23.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        ll.dismiss();
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()) {

                            for (Object item : task.getResult().getData().values()) {
                                String[] values = String.valueOf(item).replace("[", "").replace("]", "").split(",");

                                for (String value : values) {
                                    str+=" , "+value;

                                }


                            }


                            //Toast.makeText(getContext(),"get str "+getStr(),Toast.LENGTH_SHORT).show();
                        }

                    }
               // setStr(str);

            }



        });
    /////////////////////////////////---------------------------------->>>>>>>><<<<<<<<<--------------------------------------------
       // Toast.makeText(getContext(),getStr(),Toast.LENGTH_SHORT).show();

        //Toast.makeText(getContext(),"username : "+strr,Toast.LENGTH_SHORT).show();
       // Toast.makeText(getContext(),"The s2 : "+str2,Toast.LENGTH_SHORT).show();
        return getStr();
    }
    public void setStr(String str)
    {
        this.str=str;
    }
    public String getStr()
    {
        return this.str;
    }


}
