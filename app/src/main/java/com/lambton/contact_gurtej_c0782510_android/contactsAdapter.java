package com.lambton.contact_gurtej_c0782510_android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class contactsAdapter extends RecyclerView.Adapter<contactsAdapter.Viewholder> {

    Context context;
    public List<Contact> contacts;

    public contactsAdapter(Context context, List<Contact> list) {
        this.context = context;
        this.contacts = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.title.setText(contacts.get(position).getFname());
        holder.description.setText(contacts.get(position).getAddress());
        holder.emailAdd.setText(contacts.get(position).getEmail());


    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView date,title,description, emailAdd;
        ImageView note_img,delete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            description=(TextView)itemView.findViewById(R.id.desc);
            emailAdd =  (TextView)itemView.findViewById(R.id.txtSubjectItem);
            itemView.setOnClickListener(v ->{
                updateScreen(getAdapterPosition());
            });
            itemView.setOnLongClickListener(v -> {
                longPressIsInvoke(getAdapterPosition());
                return true;
            });

        }

//        @Override
//        public void onClick(View v) {
//
//            Intent i=new Intent(context, AddContactActivity.class);
//            i.putExtra("selectedIndex",getAdapterPosition());
//            context.startActivity(i);
//
//        }
//
//        @Override
//        public boolean onLongClick(View view) {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//            }
//            else{
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:"+ contacts.get(getAdapterPosition()).getNumber()));
//                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(callIntent);
//            }
//
//            return true;
//        }
    }

    public abstract void updateScreen(int i);
    public abstract void longPressIsInvoke(int i);


}
