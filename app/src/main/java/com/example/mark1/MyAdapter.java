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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Children> list;
    static MyAdapter.RecyclerViewClickListener1 listener;

    public MyAdapter(Context context, ArrayList<Children> list,RecyclerViewClickListener1 listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.childrenlist,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Children children = list.get(position);
        holder.cname.setText("Child: "+children.getChildName());
        holder.pname.setText("Parent: "+children.getParentName());
        holder.pno.setText("No: "+children.getParentNo());


        String str = children.getProfilePhoto();
        String no = children.getParentNo();

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

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView cname,pname,pno;
        ImageView img;
        Button viewdetails,callparent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cname = itemView.findViewById(R.id.text2);
            pname = itemView.findViewById(R.id.txtParentName);
            pno = itemView.findViewById(R.id.txtParentNo);
            img = itemView.findViewById(R.id.imageView6);
            viewdetails = itemView.findViewById(R.id.viewdetails2);
            callparent = itemView.findViewById(R.id.call2);

            viewdetails.setOnClickListener(this);
            callparent.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }


    public interface RecyclerViewClickListener1
    {
        void onClick(View v,int position);
    }
}
