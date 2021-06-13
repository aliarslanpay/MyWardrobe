package com.aliarslanpay.mywardrobe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.BatteryManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BatteryReceiver extends BroadcastReceiver {
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();
    //Uri imageData;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    System.out.println("Full");
                    //break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    System.out.println("Charging");/*
                    storageReference.child("images.jpg").putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            StorageReference newReference = FirebaseStorage.getInstance().getReference("images.jpg");
                        }
                    });*/
                    break;
            }


        }

    }
}
