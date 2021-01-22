package com.example.telemarketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class add_leads extends AppCompatActivity {
        Spinner spinner,spinnerEM;
        EditText Lname,Lphone,Lemail,Laddress,Lsource,Lnotes;
        Button new_lead;
        TextView close_btn;
        String leadCategory,EmployeeSelected;
        ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_leads);
        spinner=(Spinner)findViewById(R.id.spi);
        spinnerEM=(Spinner)findViewById(R.id.spi2EM);
        Lname =(EditText)findViewById(R.id.Lname);
        Lphone=(EditText)findViewById(R.id.Lphone);
        Lemail=(EditText)findViewById(R.id.Lemail);
        Laddress=(EditText)findViewById(R.id.Laddress);
        Lsource=(EditText)findViewById(R.id.Lsource);
        Lnotes=(EditText)findViewById(R.id.Lnote);
        close_btn=(TextView) findViewById(R.id.close_btn);
        new_lead=(Button)findViewById(R.id.newlead);
        loading =new ProgressDialog(this);

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
                    }
                    else{
                        Toast.makeText(add_leads.this,"You Must Add Category ...",Toast.LENGTH_SHORT).show();
                        new category_fragment();
                        new_lead.setEnabled(false);

                    }
                }
            }//task is succ
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,s);
        spinner.setAdapter(adapter);



        final ArrayList<String> emp =new ArrayList<>();
        emp.add("_____________");
        FirebaseFirestore db2=FirebaseFirestore.getInstance();
        DocumentReference reference1=db2.collection("Employee").document();
      db2.collection("Employee").whereEqualTo("company_email",ManagerPrevalent.currentOnlineManagers.getCompany_email()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful())
                    {
                        for(QueryDocumentSnapshot documentSnapshot :task.getResult())
                        {

                                String[] values = String.valueOf(documentSnapshot.getData().get("username")).replace("[", "").replace("]", "").split(",");

                                for (String value : values) {
                                    emp.add(value);
                                }



                        }
                    }
          }
      });

        ArrayAdapter<String> adapter2=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,emp);
        spinnerEM.setAdapter(adapter2);



        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new_lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLeadsField();
            }
        });





        }
        public void CheckLeadsField()
        {
            String leadName =Lname.getText().toString();
            String leadPhone=Lphone.getText().toString();
            String leadEmail=Lemail.getText().toString();
            String leadAddress=Laddress.getText().toString();
            String leadSource=Lsource.getText().toString();
            String leadNotes=Lnotes.getText().toString();
           leadCategory=spinner.getSelectedItem().toString();

           EmployeeSelected=spinnerEM.getSelectedItem().toString();

            if(TextUtils.isEmpty(leadName) || TextUtils.isEmpty(leadPhone) || TextUtils.isEmpty(leadEmail) || TextUtils.isEmpty(leadAddress) || TextUtils.isEmpty(leadSource) || TextUtils.isEmpty(leadNotes))
            {
                Toast.makeText(add_leads.this,"Please Fill All Field About Leads",Toast.LENGTH_SHORT).show();

            }
            else {
                        loading.setTitle("Please Wait...");
                        loading.setMessage("Adding New Lead Please Wait ....");
                        loading.setCanceledOnTouchOutside(false);
                        loading.show();
                        AddNewLead(leadName,leadPhone,leadEmail,leadAddress,leadSource,leadNotes,leadCategory,ManagerPrevalent.currentOnlineManagers.getCompany_email(),EmployeeSelected);


            }

        }
        public void AddNewLead(final String leadName, final String leadPhone, final String leadEmail, final String leadAddress, final String leadSource, final String leadNotes, final String leadCategory,final String companyEmail,final String EmployeeSelected)
        {
          final  FirebaseFirestore db=FirebaseFirestore.getInstance();
            DocumentReference reference=db.collection("Leads").document(leadPhone);
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful())
                    {
                        DocumentSnapshot snapshot =task.getResult();
                        if(snapshot.exists())
                        {
                            loading.dismiss();
                            Toast.makeText(add_leads.this,"This Number Is Already Exists .....!!!!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Map<String,Object> map =new HashMap<>();
                            map.put("leadName",leadName);
                            map.put("leadPhone",leadPhone);
                            map.put("leadEmail",leadEmail);
                            map.put("leadAddress",leadAddress);
                            map.put("leadSource",leadSource);
                            map.put("leadNotes",leadNotes);
                            map.put("leadCategory",leadCategory);
                            map.put("company_email",companyEmail);
                            map.put("EmployeeSelected",EmployeeSelected);

                            db.collection("Leads").document(leadPhone).set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    loading.dismiss();
                                    Toast.makeText(add_leads.this,"Added Lead Successfully",Toast.LENGTH_SHORT).show();
                                    empCat();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loading.dismiss();
                                    Toast.makeText(add_leads.this,"NETWORK ERROR ...Please Try Again Later .",Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_leads.this,"NETWORK ERROR ...PLEASE CHECK YOUR NETWORK",Toast.LENGTH_SHORT).show();
                }
            });

        }
        public void empCat()
        {
           final FirebaseFirestore db= FirebaseFirestore.getInstance();
            DocumentReference ref=db.collection("EmpCat").document(EmployeeSelected);
            ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Map<String,String> mapi=new HashMap<>();
                    mapi.put(leadCategory,leadCategory);
                    db.collection("EmpCat").document(EmployeeSelected).set(mapi,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(add_leads.this,"opop",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
}


