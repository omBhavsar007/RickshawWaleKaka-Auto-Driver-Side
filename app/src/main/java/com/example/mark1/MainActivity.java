package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txtPhone,txtPass;
    Button btnLogin;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    TextView newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        txtPhone = (EditText) findViewById(R.id.inputNumber);
        txtPass =  (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Demo1");

        newUser = (TextView) findViewById(R.id.gotoRegister);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterUser.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
        }
        });
    }

    private void checkLogin()
    {

        String mobileNo = txtPhone.getText().toString();
        String password = txtPass.getText().toString();

        progressDialog.show();
        if(TextUtils.isEmpty(mobileNo))
        {
            txtPhone.setError("Field Can't be Empty");
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            txtPass.setError("Field Can't be Empty");
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            return;
        }

        databaseReference.child(mobileNo).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                int flag = 0;
                DataSnapshot dataSnapshot = task.getResult();

                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        String pass = String.valueOf(dataSnapshot.child("password").getValue());
                        if(password.equals(pass))
                        {
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,HomePage.class).putExtra("mobno",mobileNo));
                            finish();
                        }
                        else
                        {
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }
                    }
                }
            }
        });
    }
}