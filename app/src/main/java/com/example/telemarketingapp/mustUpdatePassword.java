package com.example.telemarketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.telemarketingapp.Prevalent.EmployeePrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class mustUpdatePassword extends AppCompatActivity {
    private EditText password,re_password;
    private Button updateBTN;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_must_update_password);

        password=(EditText) findViewById(R.id.newPassMUS);
        re_password=(EditText)findViewById(R.id.rePassMUS);
        updateBTN=(Button)findViewById(R.id.btnUpdateMUS);

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UPDATEPASSWORD();
            }
        });


    }
    public void UPDATEPASSWORD()
    {
        String pass =password.getText().toString();
        String re   =re_password.getText().toString();
        if (password.length() < 8) {
            Toast.makeText(this, "The Password Must At Least 8 Characters", Toast.LENGTH_SHORT).show();
            loading.dismiss();

        } else if (!(password.equals(re))) {
            Toast.makeText(this, "The Passwords Is Not Equivlent ! ", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else{
            loading.setMessage("Please Wait");
            loading.setTitle("Updating Your Password");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference reference = db.collection("Employee").document(EmployeePrevalent.currentOnlineEmployee.getUsername());
            Map<String, Object> usermap = new HashMap<>();
            usermap.put("username", pass);

            reference.update(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loading.dismiss();
                    Toast.makeText(mustUpdatePassword.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });







}
    }

}
