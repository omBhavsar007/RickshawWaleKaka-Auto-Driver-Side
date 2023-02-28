package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ShowRequests extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Requests> list;
    DatabaseReference db,dbc,dbp;
    ArrayList<String> rlist = new ArrayList<String>();
    MyAdapter2 adapter;
    String pname,cname,sname,pno,id,str="";
    Button accept,reject;
    MyAdapter2.RecyclerViewClickListener listener;
    static  int PERMISSION_CODE = 100;
    String mobno;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShowRequests.this,HomePage.class).putExtra("mobno",mobno));
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_requests);
        recyclerView = findViewById(R.id.recylerView1);
        list = new ArrayList<>();
        mobno = getIntent().getStringExtra("mobno");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setOnAcceptClickListener();
        adapter = new MyAdapter2(this,list,listener);
        recyclerView.setAdapter(adapter);
        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);
        mobno = getIntent().getStringExtra("mobno");

        db = FirebaseDatabase.getInstance().getReference().child("Child");
        dbc = FirebaseDatabase.getInstance().getReference().child("Demo1");
        dbp = FirebaseDatabase.getInstance().getReference();

        dbc.child(mobno).child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    if(dataSnapshot.getValue().equals("Pending"))
                        rlist.add((String) dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //rlist = getIntent().getStringArrayListExtra("requestlist");
        //System.out.println(rlist);

        if(ContextCompat.checkSelfPermission(ShowRequests.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ShowRequests.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Requests requests = new Requests();

                    if(i<rlist.size())
                    {
                        if(rlist.get(i).equals((String)dataSnapshot.getKey()))
                        {
                            cname = (String) dataSnapshot.child("name").getValue();
                            pno = (String) dataSnapshot.child("parentnumber").getValue();
                            sname =  (String) dataSnapshot.child("schoolname").getValue();
                            id =  (String) dataSnapshot.child("id").getValue();
                            str = (String) dataSnapshot.child("childphoto").getValue();
                            requests.setChildname(cname);
                            requests.setParentno(pno);
                            requests.setId(id);
                            requests.setSchoolname(sname);
                            requests.setProfilePhoto(str);

                            dbp.child("Check2").child(pno).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    pname = String.valueOf(snapshot.child("name").getValue());
                                    requests.setParentname(pname);
                                    list.add(requests);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            i++;

                        }
                    }
                    else
                    {
                        break;
                    }
                    list.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    void setOnAcceptClickListener() {
        listener = new MyAdapter2.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                int get = v.getId();
                System.out.println(get);


                Button b1 = findViewById(v.getId());


                if(b1.getText().equals("REJECT"))
                {
                    Requests requests = list.get(position);
                    String id = requests.getId();
                    dbc.child(mobno).child("Request").child(id).setValue("Rejected").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ShowRequests.this, "Request Rejected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    System.out.println("YO");
                }
                else if(b1.getText().equals("Accept"))
                {
                    Requests requests = list.get(position);
                    String id = requests.getId();
                    String sname = requests.getSchoolname();
                    String pno = requests.getParentno();
                    System.out.println(mobno);

                    dbc.child(mobno).child("Schools").child(sname).child("Childs").child(id).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                System.out.println("Accepted");
                            }
                        }
                    });

                    dbp.child("Child").child(id).child("assignedAutoDriver").setValue(mobno).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                System.out.println("Accepted2");
                            }
                        }
                    });

                    dbc.child(mobno).child("Request").child(id).setValue("Accepted").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ShowRequests.this, "Request Accepted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else if(b1.getText().equals("call parent"))
                {
                    Requests requests = list.get(position);
                    String pno = requests.getParentno();
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:"+pno));
                    startActivity(i);
                }
                else if(b1.getText().equals("View Details"))
                {
                    Intent intent = new Intent(ShowRequests.this,ViewDetails.class);
                    //Requests requests = list.get(position);
                    //String id = requests.getId();
                    Requests requests = list.get(position);
                    String pno = requests.getParentno();
                    String cphoto = requests.getProfilePhoto();
                    String flag2 = "2";
                    intent.putExtra("id",id);
                    intent.putExtra("pno",pno);
                    intent.putExtra("cphoto",cphoto);
                    intent.putExtra("flag1",flag2);
                    intent.putExtra("mobno",mobno);

                    startActivity(intent);
                    finish();
                }
            }
        };
    }
}