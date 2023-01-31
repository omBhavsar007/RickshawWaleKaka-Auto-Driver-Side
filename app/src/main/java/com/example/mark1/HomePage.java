package com.example.mark1;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DatabaseReference db,dbc;
    Button btnS;
    ArrayList<String> childs = new ArrayList<String>();
    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTextView;
    String[] items = {"one","two","Three"};
    String selectedschools;
    ArrayList<String> schools = new ArrayList<String>();
    ArrayList<String> rlist = new ArrayList<String>();
    String mobno;
    TextView txtRem,txtRem1,txtRem2,txtRem3,txtRem4,txtRem5;
    String remaining = "",remaining1 = "",remaining2 = "",remaining3 = "",remaining4 = "",remaining5 = "";
    NavigationMenuItemView itemView;
    int flag = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HomePage.this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnS = findViewById(R.id.btnShowChilds);
        db = FirebaseDatabase.getInstance().getReference().child("Demo1");
        mobno = getIntent().getStringExtra("mobno");
        txtRem = findViewById(R.id.textView20);
        itemView = findViewById(R.id.nav_add_school);

        txtRem.setText("");


        db.child(mobno).child("age").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(0).setTitle("Edit Profile");
                        flag++;
                    }
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(0).setTitle("please add age,gender,Photo");
                        txtRem.setText("PLEASE ADD All THE INFORMATION IN MENU");
                    }

                }
            }
        });

        db.child(mobno).child("gender").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(0).setTitle("Edit Profile");
                        flag++;
                    }
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(0).setTitle("please add age,gender,Photo");
                        txtRem.setText("PLEASE ADD All THE INFORMATION IN MENU");
                    }

                }
            }
        });


        db.child(mobno).child("ProfilePhoto").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(0).setTitle("Edit Profile");
                        flag++;
                        System.out.println(flag);
                    }
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(0).setTitle("please add age,gender,Photo");
                        txtRem.setText("PLEASE ADD All THE INFORMATION IN MENU");
                    }

                }
            }
        });

        db.child(mobno).child("Schools").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                  if(task.getResult().exists())
                  {
                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                          navigationView.getMenu().getItem(1).setTitle("Add Schools");
                          navigationView.getMenu().getItem(4).setVisible(true);
                          flag++;
                          System.out.println(flag);
                      }
                  }
                  else
                  {
                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                          navigationView.getMenu().getItem(1).setTitle("please add schools");
                          navigationView.getMenu().getItem(4).setVisible(false);
                          txtRem.setText("PLEASE ADD All THE INFORMATION IN MENU");
                      }

                  }
            }
        });

        db.child(mobno).child("VerificationDetails").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(3).setTitle("Add Verification Details");
                        flag++;
                        System.out.println(flag);
                    }
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(3).setTitle("please add Verification Details");
                        txtRem.setText("PLEASE ADD All THE INFORMATION IN MENU");
                    }

                }
            }
        });

        db.child(mobno).child("Vehicle").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(2).setTitle("Add Vehicle Details");
                        flag++;
                        System.out.println(flag);
                        if(flag == 6)
                        {
                            txtRem.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            txtRem.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        navigationView.getMenu().getItem(2).setTitle("please add Vehicle Details");
                        txtRem.setText("PLEASE ADD All THE INFORMATION IN MENU");
                    }

                }
            }
        });

        db.child(mobno).child("Schools").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    if(dataSnapshot.child("Areas").exists())
                    {

                    }
                    else
                    {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        db.child(mobno).child("Schools").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:  snapshot.getChildren())
                {
                    schools.add((String) dataSnapshot.child("schoolName").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.child(mobno).child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    if(dataSnapshot.getValue().equals("Pending"))
                        rlist.add((String) dataSnapshot.getKey());
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    navigationView.getMenu().getItem(6).getSubMenu().getItem(0).setTitle("Requests("+rlist.size()+")");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        autoCompleteTextView = findViewById(R.id.autoCompleteTextView1);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_main, schools);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedschools = parent.getItemAtPosition(position).toString();
                System.out.println(selectedschools);
            }
        });

        System.out.println(childs);

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedschools != null) {
                    Intent intent = new Intent(HomePage.this, ShowChildren.class);
                    intent.putExtra("childlist", childs);
                    intent.putExtra("sname", selectedschools);
                    intent.putExtra("mobno", mobno);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(HomePage.this, "Please Select A School", Toast.LENGTH_SHORT).show();
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_profile:
                        Log.i("MENU_DRAWER_TAG","Edit Profile is Clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomePage.this,EditProfile.class).putExtra("mobno",mobno));
                        finish();
                        break;

                    case R.id.nav_add_school:
                        Log.i("MENU_DRAWER_TAG","Add School is Clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomePage.this,AddSchools.class).putExtra("mobno",mobno));
                        finish();
                        break;

                    case R.id.nav_add_vehicle_details:
                        Log.i("MENU_DRAWER_TAG","Add Vehicle Details is Clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomePage.this,VehicleDetail1.class).putExtra("mobno",mobno));
                        finish();
                        break;

                    case R.id.nav_add_verification_details:
                        Log.i("MENU_DRAWER_TAG","Add verification Details is Clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomePage.this,VerificationDetails.class).putExtra("mobno",mobno));
                        finish();
                        break;

                    case R.id.nav_add_area:
                        Log.i("MENU_DRAWER_TAG","Logout is Clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomePage.this,AddArea.class).putExtra("mobno",mobno));
                        finish();
                        break;

                    case R.id.request:
                        Log.i("MENU_DRAWER_TAG","request is Clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        if(rlist.isEmpty())
                        {
                            Toast.makeText(HomePage.this, "No Requests Found", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        Intent intent = new Intent(HomePage.this,ShowRequests.class);
                        intent.putExtra("requestlist",rlist);
                        intent.putExtra("mobno",mobno);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.nav_logout:
                        Log.i("MENU_DRAWER_TAG","Logout is Clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomePage.this,MainActivity.class));
                        finish();
                        break;
                }

                return true;
            }
        });


    }
}