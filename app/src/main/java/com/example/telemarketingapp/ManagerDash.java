package com.example.telemarketingapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.telemarketingapp.Prevalent.ManagerPrevalent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;

import java.net.PortUnreachableException;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class ManagerDash extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        TextView topName;
        CircleImageView imgview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dash);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                   //     .setAction("Action", null).show();
                Intent intent=new Intent(ManagerDash.this,add_leads.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        topName=(TextView)headerView.findViewById(R.id.usernameToptextview);
        topName.setText(ManagerPrevalent.currentOnlineManagers.getUsername());
        imgview=(CircleImageView)headerView.findViewById(R.id.imageView);
        Picasso.get().load(ManagerPrevalent.currentOnlineManagers.getImage()).placeholder(R.drawable.profileiii).into(imgview);
        Picasso.get().load(ManagerPrevalent.currentOnlineManagers.getImage()).placeholder(R.drawable.profileiii);



        //_____________________________________________________________________________________________________









        //_____________________________________________________________________________________________________




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
        getMenuInflater().inflate(R.menu.manager_dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,managerSetting.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment=null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment=new homeTELE();

        }
        else if (id == R.id.nav_gallery) {

            fragment=new category_fragment();

        }
        else if(id==R.id.send_task)
        {
            fragment=new add_task_fragment();
        }
     /*   else if (id==R.id.nav_gallery2)
        {
            fragment=new deleteCategory_fragment();
        }
        */
        else if (id == R.id.nav_slideshow) {
          fragment=new lead_fragment();

        }
        else if (id == R.id.nav_tools) {
            fragment=new Manager_Customer_Fragment();

        }
        else if (id == R.id.nav_share) {
         fragment=new employeeMang_fragment();

        }
        else if (id == R.id.nav_send) {
            finish();
                Intent intent=new Intent(ManagerDash.this,signin.class);
                startActivity(intent);
            Paper.book().destroy();

        }
        if(fragment!=null)
        {
            FragmentManager fm =getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.screen_area,fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
