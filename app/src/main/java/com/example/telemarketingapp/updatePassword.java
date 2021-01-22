package com.example.telemarketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemarketingapp.Prevalent.EmployeePrevalent;
import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class updatePassword extends AppCompatActivity {
        private TextView EMP_close_btn,EMP_change_profile_image;
        private EditText EMP_username,EMP_password,EMP_repassword;
        private Button EMP_update;
        private ProgressDialog loading;
        private String checker="";
    private String myUrl = "";
        private CircleImageView img;
        private Uri imageUri;
    private StorageReference storageProfilePicture;
    private StorageTask uploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        loading=new ProgressDialog(this);
        EMP_close_btn=(TextView)findViewById(R.id.close_btn_update_employee);
        EMP_change_profile_image=(TextView)findViewById(R.id.employee_text_profile_change);
        EMP_username=(EditText)findViewById(R.id.employee_username_change);
        EMP_password=(EditText)findViewById(R.id.employee_password_change);
        EMP_repassword=(EditText)findViewById(R.id.employee_repassword_change);
        EMP_update  =(Button)findViewById(R.id.employee_update_button);
        img=(CircleImageView) findViewById(R.id.employe_profile_image);

        userInfoDisplay(img, EMP_username, EMP_password);

        storageProfilePicture= FirebaseStorage.getInstance().getReference().child("profile pictures");
        EMP_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EMP_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();

                }
            }
        });

        EMP_change_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(updatePassword.this);
            }
        });



    }
    private void updateOnlyUserInfo() {
        //check for input must here ............
        String username = EMP_username.getText().toString();
        String password = EMP_password.getText().toString();
        String re_pass = EMP_repassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Enter The Username", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter The New Password", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (password.length() < 8) {
            Toast.makeText(this, "The Password Must At Least 8 Characters", Toast.LENGTH_SHORT).show();
            loading.dismiss();

        } else if (!(password.equals(re_pass))) {
            Toast.makeText(this, "The Passwords Is Not Equivlent ! ", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else {
            loading.setMessage("Please Wait");
            loading.setTitle("Updating Your Information");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference reference = db.collection("Employee").document(EmployeePrevalent.currentOnlineEmployee.getUsername());
            Map<String, Object> usermap = new HashMap<>();
            usermap.put("username", EMP_username.getText().toString());
            usermap.put("password", EMP_password.getText().toString());
            reference.update(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loading.dismiss();
                    Toast.makeText(updatePassword.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            img.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();
            loading.dismiss();
            startActivity(new Intent(updatePassword.this, updatePassword.class));
            finish();
        }
    }
    private void userInfoSaved() {

        String username = EMP_username.getText().toString();
        String password = EMP_password.getText().toString();
        String re_pass = EMP_repassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Enter The Username", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter The New Password", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (password.length() < 8) {
            Toast.makeText(this, "The Password Must At Least 8 Characters", Toast.LENGTH_SHORT).show();
            loading.dismiss();

        } else if (!(password.equals(re_pass))) {
            Toast.makeText(this, "The Passwords Is Not Equivlent ! ", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else if(checker.equals("clicked"))
        {
            UploadImage();

        }
    }
    private void UploadImage() {
        if (imageUri != null) {

            final StorageReference fileRef = storageProfilePicture.child(EmployeePrevalent.currentOnlineEmployee.getUsername() + ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            Toast.makeText(updatePassword.this,"imageUri != null",Toast.LENGTH_SHORT).show();
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then( Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();

                    }
                    Toast.makeText(updatePassword.this,"here we go",Toast.LENGTH_SHORT).show();
                    return fileRef.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(updatePassword.this,"task is success",Toast.LENGTH_SHORT).show();
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference reference = db.collection("Employee").document(EmployeePrevalent.currentOnlineEmployee.getUsername());
                        HashMap<String, Object> usermap = new HashMap<>();
                        usermap.put("username", EMP_username.getText().toString());
                        usermap.put("password", EMP_password.getText().toString());
                        usermap.put("image",myUrl);
                        reference.update(usermap);
                        finish();

                    }
                }
            });
        }
    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText) {
        Toast.makeText(this,"displayyyyyyyy",Toast.LENGTH_SHORT).show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference reference = db.collection("Employee").document(EmployeePrevalent.currentOnlineEmployee.getUsername());
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(updatePassword.this,"tsk success",Toast.LENGTH_SHORT).show();
                    DocumentSnapshot snap = task.getResult();
                    if (snap.exists()) {
                        Toast.makeText(updatePassword.this,"snap exist",Toast.LENGTH_SHORT).show();
                        String image = (String) snap.get("image");
                        String name = (String) snap.get("username");
                        String password = (String) snap.get("password");
                        Toast.makeText(updatePassword.this,name,Toast.LENGTH_SHORT).show();
                        Picasso.get().load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(password);



                        /*
                        String username = (String) docuemnt.get("username");  //if the field is String
                             Boolean b = (Boolean) document.get("isPublic");       //if the field is Boolean
                           Integer i = (Integer) document.get("age")

                       */
                    }
                }
            }
        });

    }




    public void update()
    {
       /*

       String password =upd_pass.getText().toString();
        String re_password=upd_repass.getText().toString();

          if(TextUtils.isEmpty(password) && TextUtils.isEmpty(re_password))
            {
                Toast.makeText(this,"Please Enter The Your Password !!",Toast.LENGTH_SHORT).show();

            }
    else if(!password.equals(re_password))
            {
                Toast.makeText(this,"Password is not equivalent !!",Toast.LENGTH_SHORT).show();
            }
    else if(password.length()<8)
            {

                Toast.makeText(this,"The Password Must At Least 8 Characters !!",Toast.LENGTH_SHORT).show();
            }
    else {

              loading.show();
             loading.setCanceledOnTouchOutside(false);
              FirebaseFirestore db = FirebaseFirestore.getInstance();
              DocumentReference ref = db.collection("Employee").document(EmployeePrevalent.currentOnlineEmployee.getUsername());
              Map<String, Object> map = new HashMap<>();
              map.put("password",password);
              ref.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                       loading.dismiss();
                        Toast.makeText(updatePassword.this,"Updated Password Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                  }
              });


*/


    }
}
