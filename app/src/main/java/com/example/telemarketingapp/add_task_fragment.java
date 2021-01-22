package com.example.telemarketingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.TargetOrBuilder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class add_task_fragment extends Fragment {
    private EditText subject,message;
    private Button send_btn;
    private Spinner spinner;
    private ProgressDialog loading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_task,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        subject=(EditText)view.findViewById(R.id.task_subject);
        message=(EditText)view.findViewById(R.id.task_message);
        send_btn=(Button)view.findViewById(R.id.send_task_btn);
        spinner=(Spinner)view.findViewById(R.id.spiTASK);
        loading=new ProgressDialog(getContext());

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SEND_TASK();
            }
        });


        final ArrayList<String> emp =new ArrayList<>();
        emp.add("_____________");
        FirebaseFirestore db2=FirebaseFirestore.getInstance();
        DocumentReference reference1=db2.collection("Employee").document();
        db2.collection("Employee"). whereEqualTo("company_email", ManagerPrevalent.currentOnlineManagers.getCompany_email()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        ArrayAdapter<String> adapter2=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,emp);
        spinner.setAdapter(adapter2);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SEND_TASK();
            }
        });



    }
    public void SEND_TASK()
    {
        final FirebaseFirestore db2 =FirebaseFirestore.getInstance();
        final String subj =subject.getText().toString();
        final String messa=message.getText().toString();
        final String employee=spinner.getSelectedItem().toString();
        if(TextUtils.isEmpty(subj) || TextUtils.isEmpty(messa))
        {
            Toast.makeText(getContext(),"You Must Fill The Field..",Toast.LENGTH_SHORT).show();
        }
        else{

            loading.show();
        final    FirebaseFirestore db =FirebaseFirestore.getInstance();
            final DocumentReference reference=db.collection("Tasks").document(employee);
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot ss =task.getResult();
                        if(ss.exists()){
                            Toast.makeText(getContext(),"Exists",Toast.LENGTH_SHORT).show();
                        }


                     final    Map<String,String> map =new HashMap<>();
                        map.put("subject",subj);
                        map.put("message",messa);
                        map.put("employee",employee);
                      //  map.put("id",String.valueOf(random));
                        map.put("manager",ManagerPrevalent.currentOnlineManagers.getUsername());
                        map.put("circle",ManagerPrevalent.currentOnlineManagers.getImage());
                        map.put("company_email",ManagerPrevalent.currentOnlineManagers.getCompany_email());
                        db.collection("Tasks").document(employee).set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                map.put("id",reference.getId());

                                Toast.makeText(getContext(),"The Task Sent Successfully",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }
        final double random =getRandomDoubleBetweenRange(0,1);
        final FirebaseFirestore db3 =FirebaseFirestore.getInstance();
        final DocumentReference dc =db3.collection("TasksView").document(String.valueOf(random));
        dc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    Map<String ,String> tsk =new HashMap<>();
                    tsk.put("subject",subj);
                    tsk.put("message",messa);
                    tsk.put("employee",employee);
                      tsk.put("id",String.valueOf(random));
                    tsk.put("manager",ManagerPrevalent.currentOnlineManagers.getUsername());
                    tsk.put("circle",ManagerPrevalent.currentOnlineManagers.getImage());
                    tsk.put("company_email",ManagerPrevalent.currentOnlineManagers.getCompany_email());
                    db3.collection("TasksView").document(String.valueOf(random)).set(tsk,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            loading.dismiss();
                            Toast.makeText(getContext(),"The Task Sent Successfully to taskview",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });




        Query q =db2.collection("Tasks").whereEqualTo("employee",employee).whereEqualTo("company_email",ManagerPrevalent.currentOnlineManagers.getCompany_email());
        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().size()>0)
                    {
                        for(QueryDocumentSnapshot documentSnapshot :task.getResult())
                        {
                            Toast.makeText(getContext(),String.valueOf(task.getResult().size()),Toast.LENGTH_SHORT).show();
                            Map<String,String> paNotifications=new HashMap<>();
                            paNotifications.put("from",ManagerPrevalent.currentOnlineManagers.getUsername());
                            paNotifications.put("type","request");
                            db2.collection("Employee").document(documentSnapshot.getId()).collection("Notifications").document().set(paNotifications,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                             Toast.makeText(getContext(),"Sent Notification Successfully",Toast.LENGTH_SHORT).show();
                                    Fragment fragment = new homeTELE();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.screen_area, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            });
                        }
                    }
                }
            }
        });
    }
    public static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }
    public void ref() {
        Fragment fragment = new homeTELE();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screen_area, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}
