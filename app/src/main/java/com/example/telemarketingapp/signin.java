package com.example.telemarketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemarketingapp.Model.Employee;
import com.example.telemarketingapp.Model.Managers;
import com.example.telemarketingapp.Prevalent.EmployeePrevalent;
import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class signin extends AppCompatActivity {
private TextView createAcc;
private EditText Inputusername,Inputpassword;
private ProgressDialog loading;
private Button login;
private CheckBox box;

private RadioButton radiomanager,radioemployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        createAcc=(TextView)findViewById(R.id.createAcc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signin.this,companycreateaccount.class);
                startActivity(intent);
            }
        });
        login   =(Button)findViewById(R.id.login);
        radioemployee =(RadioButton)findViewById(R.id.radio_employee);
        radiomanager  =(RadioButton)findViewById(R.id.radio_manager);
        Inputusername=(EditText)findViewById(R.id.username);
        Inputpassword=(EditText)findViewById(R.id.password);
        box          =(CheckBox)findViewById(R.id.remebermeid);
        loading=new ProgressDialog(this);
        Paper.init(this);
        String MangUsername=Paper.book().read(ManagerPrevalent.ManagerUsernameKey);
        String MangPassword=Paper.book().read(ManagerPrevalent.ManagerPasswordKey);

        if(MangUsername != "" && MangPassword != "")
        {
            if(!TextUtils.isEmpty(MangUsername) && !TextUtils.isEmpty(MangPassword))
            {
                loading.show();
                AllowAccessManager(MangUsername,MangPassword);
            }
        }
        String EmpUsername=Paper.book().read(EmployeePrevalent.EmployeeUsernameKey);
        String EmpPassowrd=Paper.book().read(EmployeePrevalent.EmployeePasswordKey);
        if(EmpUsername != "" && EmpPassowrd !="")
        {
            if(!TextUtils.isEmpty(EmpUsername) && !TextUtils.isEmpty(EmpUsername))
            {
                loading.show();
                AllowAccessEmployee(EmpUsername,EmpPassowrd);
            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });


    }
    public void LoginUser()                                 // CHECK USER INPUT VALIDATION BEFOR ACCESS TO THE ACCOUNT
    {
        loading.setTitle("Login to account");
        loading.setMessage("Please Wait ....");
        loading.setCanceledOnTouchOutside(false);

        String username = Inputusername.getText().toString();
        String password = Inputpassword.getText().toString();

        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this,"Please Enter Your Email !!!",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Enter Your Password !!!",Toast.LENGTH_SHORT).show();
        }
        if(radiomanager.isChecked())
        {
            loading.show();
            AllowAccessManager(username,password);
        }
        else if(radioemployee.isChecked())
        {
            loading.show();
            AllowAccessEmployee(username,password);
        }
        else if(!(radioemployee.isChecked()) && !(radiomanager.isChecked()))
        {
            Toast.makeText(this,"Please Select Either Employee Or Manager !!!",Toast.LENGTH_SHORT).show();
        }
        else if(radioemployee.isChecked() && radiomanager.isChecked())
        {
            Toast.makeText(this,"Please Select Either Employee Or Manager Not Both!!!",Toast.LENGTH_SHORT).show();
        }

    }
    public void AllowAccessManager(String username, final String password)          // ALLOW ACCESS
    {

        if(box.isChecked())
        {
            Paper.book().write(ManagerPrevalent.ManagerUsernameKey, username);
            Paper.book().write(ManagerPrevalent.ManagerPasswordKey, password);
        }

        final FirebaseFirestore db =FirebaseFirestore.getInstance();
        final DocumentReference reference=db.collection("Managers").document(username);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {

                            DocumentSnapshot snapshot =task.getResult();
                            Managers managerData =snapshot.toObject(Managers.class);
                            if(snapshot.exists())
                            {
                                if(managerData.getPassword().equals(password))
                                {
                                 //  String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                    Toast.makeText(signin.this,"Login In Successfully",Toast.LENGTH_SHORT).show();
                                    ManagerPrevalent.currentOnlineManagers=managerData;

                                    loading.dismiss();
                                    Intent intent=new Intent(signin.this,ManagerDash.class);
                                /*    Map<String,String> map =new HashMap<>();
                                    map.put("deviceToken",deviceToken);
                                    db.collection("Managers").document(ManagerPrevalent.currentOnlineManagers.getUsername()).collection("Token").document(ManagerPrevalent.currentOnlineManagers.getUsername()).set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(signin.this,"Device Token successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    */
                                    startActivity(intent);


                                }
                                else{
                                    Toast.makeText(signin.this,"The Password Is Incorrect Please Try Again ...!!",Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                }
                            }
                            else{
                                Toast.makeText(signin.this,"The Username is Incorrect Please Try Again Or Create New Account...!!",Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }

                        }
            }
        });


    }
    public void AllowAccessEmployee(String username, final String password)
    {
        if(box.isChecked())
        {
            Paper.book().write(EmployeePrevalent.EmployeeUsernameKey, username);
            Paper.book().write(EmployeePrevalent.EmployeePasswordKey, password);
        }

        final FirebaseFirestore db =FirebaseFirestore.getInstance();
        final DocumentReference reference =db.collection("Employee").document(username);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot snapshot =task.getResult();
                        Employee employeeData =snapshot.toObject(Employee.class);
                        if(snapshot.exists())
                        {
                            if(employeeData.getPassword().equals(password))
                            {
                                Toast.makeText(signin.this,"Login In Successfully",Toast.LENGTH_SHORT).show();
                                EmployeePrevalent.currentOnlineEmployee=employeeData;
                                loading.dismiss();
                                Intent intent=new Intent(signin.this,employeedash.class);
                                //  String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                 /*    Map<String,String> map =new HashMap<>();
                                    map.put("deviceToken",deviceToken);
                                    db.collection("Managers").document(ManagerPrevalent.currentOnlineManagers.getUsername()).collection("Token").document(ManagerPrevalent.currentOnlineManagers.getUsername()).set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(signin.this,"Device Token successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    */
                                 String deviceToken =FirebaseInstanceId.getInstance().getToken();
                                 Map<String,Object> map =new HashMap<>();
                                 map.put("deviceToken",deviceToken);
                                 db.collection("Employee").document(EmployeePrevalent.currentOnlineEmployee.getUsername()).collection("Token").document(EmployeePrevalent.currentOnlineEmployee.getUsername()).set(map,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(signin.this,"Device Token Successfully ",Toast.LENGTH_SHORT).show();
                                     }
                                 });
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(signin.this,"The Password Is Incorrect Please Try Again ...!!",Toast.LENGTH_SHORT).show();
                                loading.dismiss();


                            }

                        }
                        else{
                            Toast.makeText(signin.this,"The Email is Incorrect Please Try Again Or Create New Account...!!",Toast.LENGTH_SHORT).show();
                            loading.dismiss();


                        }

                    }
            }
        });

    }



}
