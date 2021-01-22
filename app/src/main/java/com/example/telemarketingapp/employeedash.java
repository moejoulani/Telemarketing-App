package com.example.telemarketingapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.telemarketingapp.Model.Employee;
import com.example.telemarketingapp.Prevalent.EmployeePrevalent;
import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class employeedash extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
       private TextView topName;
       private CircleImageView imgview;
       private String checkPASS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeedash);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      /*

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        topName=(TextView)headerView.findViewById(R.id.topemployeename);
        topName.setText(EmployeePrevalent.currentOnlineEmployee.getUsername());
        imgview=(CircleImageView)headerView.findViewById(R.id.imageView22);
        Picasso.get().load(EmployeePrevalent.currentOnlineEmployee.getImage()).placeholder(R.drawable.profileiii).into(imgview);
        Picasso.get().load(EmployeePrevalent.currentOnlineEmployee.getImage()).placeholder(R.drawable.profileiii);

      /*  FirebaseFirestore dbb =FirebaseFirestore.getInstance();
        DocumentReference reference =dbb.collection("Employee").document(EmployeePrevalent.currentOnlineEmployee.getUsername());
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot snapshot =task.getResult();
                 checkPASS=snapshot.getString("password");
                }
            }
        });
        */
        Toast.makeText(this,String.valueOf(EmployeePrevalent.currentOnlineEmployee.getPassword().length()),Toast.LENGTH_SHORT).show();
        if(EmployeePrevalent.currentOnlineEmployee.getPassword().length()<8)
        {
            Intent intent=new Intent(this,mustUpdatePassword.class);
            startActivity(intent);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.employeedash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings2) {
                Intent intent=new Intent(employeedash.this,info.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        if (id == R.id.nav_home2)
        {
            // Handle the camera action
            fragment=new homeTELE();
        } else if (id == R.id.nav_gallery2)
        {
            fragment=new employee_lead_fragment();

        }
        else if (id == R.id.nav_slideshow2)
        {
         fragment=new employee_prospect_fragment();
        }
        else if (id == R.id.nav_tools2)
        {
          fragment=new employee_customer_fragment();
        }
        else if(id==R.id.taskempoicono)
        {
            fragment=new employeeTask_fragment();
        }
        else if(id==R.id.nav_send6)
        {
            Intent intent=new Intent(employeedash.this,updatePassword.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_send2)
        {
            finish();
            Intent intent=new Intent(employeedash.this,signin.class);
            Paper.book().destroy();
            startActivity(intent);
        }
        if(fragment!=null)
        {
            FragmentManager fm =getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.screen_area2,fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
