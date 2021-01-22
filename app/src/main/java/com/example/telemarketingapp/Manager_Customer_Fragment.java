package com.example.telemarketingapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemarketingapp.Model.Customers;
import com.example.telemarketingapp.Prevalent.EmployeePrevalent;
import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.example.telemarketingapp.ViewHolder.CustomerEMHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Manager_Customer_Fragment extends Fragment {
    private Spinner sp2;
    private String category2;
    private ProgressDialog loading;
    private RecyclerView VrecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static final int   PER_CODE=123;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manager_customer_fragment,null);
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        loading=new ProgressDialog(getContext());
        sp2=(Spinner)view.findViewById(R.id.spi7);

        layoutManager = new LinearLayoutManager(getContext());
        VrecyclerView = view.findViewById(R.id.coolermaster);
        VrecyclerView.setHasFixedSize(true);
        VrecyclerView.setLayoutManager(layoutManager);

        final ArrayList<String> s=new ArrayList<>();
        s.add("-----");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference reference=db.collection("Category").document(ManagerPrevalent.currentOnlineManagers.getCompany_email());
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        for (Object item : task.getResult().getData().values()) {
                            String[] values = String.valueOf(item).replace("[", "").replace("]", "").split(",");

                            for (String value : values) {
                                s.add(value);
                            }


                        }
                    }else{
                        Toast.makeText(getContext(),"___________________",Toast.LENGTH_SHORT).show();
                    }

                }
            }//task is succ
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,s);
        sp2.setAdapter(adapter);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category2=sp2.getSelectedItem().toString();
                onStart2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });








    }
    public void onStart2()
    {
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        final Query query=db.collection("Customers").whereEqualTo("Category",category2).whereEqualTo("company_email",ManagerPrevalent.currentOnlineManagers.getCompany_email());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    loading.dismiss();
                    FirestoreRecyclerOptions<Customers> options=new FirestoreRecyclerOptions.Builder<Customers>().setQuery(query,Customers.class).build();
                    FirestoreRecyclerAdapter<Customers, CustomerEMHolder> adapter=new FirestoreRecyclerAdapter<Customers, CustomerEMHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CustomerEMHolder holder, int position, @NonNull final Customers model) {
                            holder.Cname.setText(model.getCustName());
                            holder.Cphone.setText(model.getCustPhone());
                            holder.Cemail.setText(model.getCustEmail());
                            holder.Csource.setText(model.getCustSource());
                            holder.Caddress.setText(model.getCustAddress());
                            holder.Cnotes.setText(model.getCustNotes());


                            //____________ ON CLICK LISTENER __________________________________
                            holder.deleteC.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final ProgressDialog progressDialog=new ProgressDialog(getContext());
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    db.collection("Customers").document(model.getCustPhone()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(),"Deleted Customer Successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });




                            //____________ ON CLICK LISTENER __________________________________

                            holder.callC.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:"+Long.parseLong(model.getCustPhone())));//change the number
                                    startActivity(callIntent);



                                }
                            });



                            //____________ ON CLICK LISTENER __________________________________END




                        }

                        @NonNull
                        @Override
                        public CustomerEMHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.em_cust_item,parent,false);
                            CustomerEMHolder customerEMHolder=new CustomerEMHolder(view);
                            return customerEMHolder;
                        }
                    };VrecyclerView.setAdapter(adapter);
                    adapter.startListening();

                }
            }
        });

    }
}
