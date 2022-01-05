package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.util.Calendar;
import java.util.Locale;

public class ContactDetails extends AppCompatActivity {

    //view
    private TextView nameTv,phoneTv,emailTv,addedTimeTv,updatedTimeTv,noteTv;
    private ImageView profileIv;

    private String id;

    //database helper
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //init db
        dbHelper = new DbHelper(this);

        //get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        //init view
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);
        noteTv = findViewById(R.id.noteTv);

        profileIv = findViewById(R.id.profileIv);

        loadDataById();

    }

    private void loadDataById() {

        //get data from database
        //query for find data by id
        String selectQuery =  "SELECT * FROM "+Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                //get data
                String name =  ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                String image = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE));
                String phone = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                String email = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                String note = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE));
                String addTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME));
                String updateTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME));

                //convert time to dd/mm/yy hh:mm:aa format
                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                calendar.setTimeInMillis(Long.parseLong(addTime));
                String timeAdd = ""+ DateFormat.format("dd/MM/yy hh:mm:aa",calendar);

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = ""+ DateFormat.format("dd/MM/yy hh:mm:aa",calendar);

                //set data
                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
                noteTv.setText(note);
                addedTimeTv.setText(timeAdd);
                updatedTimeTv.setText(timeUpdate);

                if (image.equals("null")){
                    profileIv.setImageResource(R.drawable.ic_baseline_person_24);
                }else {
                    profileIv.setImageURI(Uri.parse(image));
                }

            }while (cursor.moveToNext());
        }

        db.close();

    }

    // run app
    // we have error in profileIv,dbHelper initialization
    // we successfully read our db and show details
}