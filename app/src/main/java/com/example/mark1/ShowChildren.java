package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShowChildren extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Children> list;
    DatabaseReference db,dbc,dbp;
    MyAdapter adapter;
    ArrayList<String> clist = new ArrayList<String>();
    public String pname,cname,pno,parent,str="";
    MyAdapter.RecyclerViewClickListener1 listener;
    ImageView img;
    static  int PERMISSION_CODE = 100;
    String mobno;
    String sname;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShowChildren.this,HomePage.class).putExtra("mobno",mobno));
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_children);
        setOnClickListener();
        recyclerView = findViewById(R.id.recylerView);
        list = new ArrayList<>();
        dbp = FirebaseDatabase.getInstance().getReference().child("Demo1");
        db = FirebaseDatabase.getInstance().getReference().child("Child");
        dbc = FirebaseDatabase.getInstance().getReference();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list, listener);
        recyclerView.setAdapter(adapter);

        mobno = getIntent().getStringExtra("mobno");

        sname = getIntent().getStringExtra("sname");
        System.out.println(sname);

        if (sname != null) {
            dbp.child(mobno).child("Schools").child(sname).child("Childs").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        clist.add((String) dataSnapshot.getKey());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            img = findViewById(R.id.imageView6);

            if (ContextCompat.checkSelfPermission(ShowChildren.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ShowChildren.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
            }

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int i = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Children children = new Children();
                        if (i < clist.size()) {
                            if (clist.get(i).equals((String) dataSnapshot.getKey())) {
                                cname = (String) dataSnapshot.child("name").getValue();
                                pno = (String) dataSnapshot.child("parentnumber").getValue();
                                str = (String) dataSnapshot.child("childphoto").getValue();
                                String id = (String) dataSnapshot.child("id").getValue();
                                System.out.println(cname);
                                System.out.println(pno);
                                children.setChildName(cname);
                                children.setParentNo(pno);
                                children.setId(id);
                                children.setProfilePhoto(str);

                                dbc.child("Check2").child(pno).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        pname = (String) snapshot.child("name").getValue();
                                        children.setParentName(pname);
                                        list.add(children);
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                i++;
                            }
                        } else {
                            break;
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else
        {
            Toast.makeText(this, "Please select a school", Toast.LENGTH_SHORT).show();
        }


        /*

        for(int i=0;i<clist.size();i++)
        {
            dbc.child("Child").child(clist.get(i)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot snapshot = task.getResult();
                    if(task.isSuccessful())
                    {
                        if(task.getResult().exists())
                        {
                           cname = (String) snapshot.child("name").getValue();
                           pno = (String) snapshot.child("parentnumber").getValue();

                            db.child("Check2").child(pno).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        DataSnapshot snapshot1 = task.getResult();

                                        pname = (String) snapshot1.child("name").getValue();

                                        System.out.println(cname);
                                        System.out.println(pname);
                                        System.out.println(pno);
                                    }
                                }
                            });

                            Children children = new Children(cname,pname,pno);
                            list.add(children);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });

        }

         */
    }

    void setOnClickListener()
    {
        listener = new MyAdapter.RecyclerViewClickListener1() {
            @Override
            public void onClick(View v, int position) {
                int get = v.getId();
                System.out.println(v.getId());
                Button b1 = findViewById(v.getId());
                Button callparent = findViewById(v.getId());


                if(b1.getText().equals("Call Parent"))
                {
                    Children children = list.get(position);
                    String pno = children.getParentNo();
                    System.out.println(pno);
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:"+pno));
                    startActivity(i);
                }
                else if(b1.getText().equals("View Details"))
                {
                    Children children = list.get(position);
                    String pno = children.getParentNo();
                    System.out.println(pno);
                    String cphoto = children.getProfilePhoto();
                    String id = children.getId();
                    String flag = "1";

                    Intent intent = new Intent(ShowChildren.this,ViewDetails.class);
                    intent.putExtra("id",id);
                    intent.putExtra("pno",pno);
                    intent.putExtra("cphoto",cphoto);
                    intent.putExtra("flag1",flag);
                    intent.putExtra("sname",sname);
                    intent.putExtra("mobno",mobno);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }
}