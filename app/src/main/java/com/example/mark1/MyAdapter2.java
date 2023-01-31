package com.example.mark1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {

    Context context;
    ArrayList<Requests> list;
    static RecyclerViewClickListener listener;

    public MyAdapter2(Context context, ArrayList<Requests> list, RecyclerViewClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.requestlist,parent,false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Requests requests = list.get(position);
        holder.pname.setText("Parent: "+requests.getParentname());
        holder.cname.setText("Child: "+requests.getChildname());
        holder.sname.setText("School: "+requests.getSchoolname());
        holder.pno.setText("No: "+requests.getParentno());

        String str = requests.getProfilePhoto();
        String no = requests.getParentno();

        StorageReference sr = FirebaseStorage.getInstance().getReference("Childphotos/").child(no+"/").child(str);

        try {

            File localfile = File.createTempFile("tempfile",".jpg");

            sr.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    holder.img.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static  class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pname,cname,sname,pno;
        Button accept,reject,viewdetails,callparent;
        ImageView img;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.parentname1);
            cname = itemView.findViewById(R.id.childname);
            sname = itemView.findViewById(R.id.schoolname);
            pno = itemView.findViewById(R.id.parentNumber);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
            img = itemView.findViewById(R.id.imageView5);
            viewdetails = itemView.findViewById(R.id.viewdetails);
            callparent = itemView.findViewById(R.id.call);


            reject.setOnClickListener(this);
            accept.setOnClickListener(this);
            viewdetails.setOnClickListener(this);
            callparent.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener
    {
        void onClick(View v,int position);
    }
}
