package com.example.mark1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterUser extends AppCompatActivity {


    EditText phone,name,email,pass,repass;
    Button register;
    TextView gotologin;
    DatabaseReference databaseReference;
    String mobno;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterUser.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        phone = (EditText) findViewById(R.id.inputPhone);
        name = (EditText) findViewById(R.id.inputName);
        email = (EditText) findViewById(R.id.editTextTextPersonName2);
        pass = (EditText) findViewById(R.id.editTextTextPassword);
        repass = (EditText) findViewById(R.id.editTextTextPassword2);
        register = (Button) findViewById(R.id.button);
        gotologin = (TextView) findViewById(R.id.gotoLogin);

        databaseReference  = FirebaseDatabase.getInstance().getReference();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUser.this,MainActivity.class));
                finish();
            }
        });
    }

    private void registerUser()
    {
        String no = phone.getText().toString();
        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Pass = pass.getText().toString();
        String Repass = repass.getText().toString();
        String id = databaseReference.push().getKey();


        if(TextUtils.isEmpty(Name))
        {
            name.setError("This Field Can't Be Empty");
            return;
        }

        if(TextUtils.isEmpty(no))
        {
            phone.setError("This Field Can't Be Empty");
            return;
        }
        if(TextUtils.isEmpty(Email))
        {
            email.setError("This Field Can't Be Empty");
            return;
        }
        if(TextUtils.isEmpty(Pass))
        {
            pass.setError("This Field Can't Be Empty");
            return;
        }
        if(TextUtils.isEmpty(Repass))
        {
            repass.setError("This Field Can't Be Empty");
            return;
        }

        User user = new User(no,Name,Email,Pass);
        if(Pass.equals(Repass))
        {
            databaseReference.child("Demo1").child(no).setValue(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterUser.this, "User Registration Successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "Please enter same password", Toast.LENGTH_SHORT).show();
        }
    }
}