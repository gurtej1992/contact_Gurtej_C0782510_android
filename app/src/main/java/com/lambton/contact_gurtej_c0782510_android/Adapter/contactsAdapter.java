package com.lambton.contact_gurtej_c0782510_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lambton.contact_gurtej_c0782510_android.Model.Contact;
import com.lambton.contact_gurtej_c0782510_android.R;

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
        View itemView= LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        String name = contacts.get(position).getFname() + " "+ contacts.get(position).getLname();
        holder.title.setText(name);
        holder.address.setText(contacts.get(position).getAddress());
        holder.emailAdd.setText(contacts.get(position).getEmail());
        holder.phone.setText(String.valueOf(contacts.get(position).getNumber()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView phone,title,emailAdd, address;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            emailAdd=(TextView)itemView.findViewById(R.id.txtViewemail);
            phone =  (TextView)itemView.findViewById(R.id.txtViewPhone);
            address =  (TextView)itemView.findViewById(R.id.txtViewAdd);
            itemView.setOnClickListener(v ->{
                updateScreen(getAdapterPosition());
            });
            itemView.setOnLongClickListener(v -> {
                longPressIsInvoke(getAdapterPosition());
                return true;
            });
        }
    }

    public abstract void updateScreen(int i);
    public abstract void longPressIsInvoke(int i);


}
