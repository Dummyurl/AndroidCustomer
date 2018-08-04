package com.samyotech.fabcustomer.controller;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samyotech.fabcustomer.interfacess.FirebaseDataTravel;

public class FirebaseController {
    Context context;
    DatabaseReference locationTable;
    FirebaseDatabase uDatabase;
    FirebaseDataTravel travel;

    public FirebaseController(Context context) {
        this.context = context;
        uDatabase=FirebaseDatabase.getInstance();
        locationTable= uDatabase.getReference("location");
    }

    /*api call*/
    public void getLocationData(){
        locationTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    /*for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        LocationModel locationModel=dataSnapshot1.getValue(LocationModel.class);
                        System.out.println("-------------------data----------"+String.valueOf(locationModel.getTitle())+"       "+String.valueOf(locationModel.getLatitude()));
                    }*/
                    /*send data to class*/
                    travel.trasferData(dataSnapshot);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void registerTravel(FirebaseDataTravel travel1){
        travel=travel1;
    }

}
