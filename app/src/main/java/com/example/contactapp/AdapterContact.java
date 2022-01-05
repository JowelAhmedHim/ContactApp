package com.example.contactapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    private Context context;
    private ArrayList<ModelContact> contactList;

    // add constructor
    // alt + ins

    public AdapterContact(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_item,parent,false);
        ContactViewHolder vh = new ContactViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        ModelContact modelContact = contactList.get(position);

        //get data
        //we need only 3 data
        String id = modelContact.getId();
        String image = modelContact.getImage();
        String name = modelContact.getName();

        //set data in view
        holder.contactName.setText(name);
        if (image.equals("")){
            holder.contactImage.setImageResource(R.drawable.ic_baseline_person_24);
        }else {
            holder.contactImage.setImageURI(Uri.parse(image));
        }

        //handle click listener
        holder.contactDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //handle item click and show contact details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent to move to contactsDetails Activity with contact id as reference
                Intent intent = new Intent(context,ContactDetails.class);
                intent.putExtra("contactId",id);
                context.startActivity(intent); // now get data from details Activity
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        //view for row_contact_item
        ImageView contactImage,contactDial;
        TextView contactName;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            //init view
            contactImage = itemView.findViewById(R.id.contact_image);
            contactDial = itemView.findViewById(R.id.contact_number_dial);
            contactName = itemView.findViewById(R.id.contact_name);
        }
    }
}
