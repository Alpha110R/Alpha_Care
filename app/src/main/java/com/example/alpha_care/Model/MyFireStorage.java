package com.example.alpha_care.Model;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.alpha_care.Activities.AddPetToUserActivity;
import com.example.alpha_care.Activities.PetProfileActivity;
import com.example.alpha_care.Activities.PetsListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class MyFireStorage {
    private FirebaseStorage myFireStorage;
    private StorageReference storageRef;
    private StorageReference imagesRef;

    public MyFireStorage(){
        myFireStorage = FirebaseStorage.getInstance();
        storageRef = myFireStorage.getReference();
        imagesRef = storageRef.child("petImages");
    }

    public void uploadFile(Activity activity, Uri imageUri, ProgressBar progressBar) {
        if (imageUri != null) {
            StorageReference fileReference = imagesRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri, activity));
            Toast.makeText(activity, "Wait, uploading image", Toast.LENGTH_LONG).show();
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(activity instanceof AddPetToUserActivity){
                                        ((AddPetToUserActivity) activity).setPetImageUrl(String.valueOf(task.getResult()));//gives image or file string url
                                    }
                                }
                            });
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(activity, "Upload successful", Toast.LENGTH_LONG).show();
                            if(activity instanceof AddPetToUserActivity){
                                ((AddPetToUserActivity) activity).setSubmitButtonOn();// Only after the image has uploaded yhe user can submit
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(activity, "No file selected", Toast.LENGTH_LONG).show();
            if(activity instanceof AddPetToUserActivity){
                ((AddPetToUserActivity) activity).setSubmitButtonOn();// Only after the image has uploaded yhe user can submit
            }
        }

    }
    private String getFileExtension(Uri uri, Activity activity) {
        ContentResolver cR = activity.getContentResolver();
        return MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(cR.getType(uri));
    }
}
