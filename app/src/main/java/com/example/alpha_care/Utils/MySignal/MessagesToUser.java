package com.example.alpha_care.Utils.MySignal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.Activities.PetsListActivity;
import com.example.alpha_care.AdaptersToRecycleView.PetCardAdapter;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.R;
import com.example.alpha_care.Repository;

import java.util.List;

public class MessagesToUser {
    private Context appContext;
    private static MessagesToUser me;

    public MessagesToUser(){}

    private MessagesToUser(Context appContext){
        this.appContext = appContext;
    }

    public static void initMessagesToUser(Context appContext){
        if(me == null)
            me = new MessagesToUser(appContext.getApplicationContext());
    }
    public static MessagesToUser getMe(){return me;}

    public void makeToastMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();
    }

    public void dialogToRemoveItemInRecycleView(Activity activity, RecyclerView.ViewHolder viewHolder, List<Pet> petList, PetCardAdapter petCardAdapter){
        int position = viewHolder.getAdapterPosition();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity); //alert for confirm to delete
        builder.setMessage("Are you sure to delete " + petList.get(position).getName() + "?");    //set message
        builder.setTitle("Delete");
        builder.setIcon(R.drawable.ic_alert);
        builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Repository.getMe().deletePetAtCurrentUser(activity, petList.get(position).getPetID());
                petList.remove(position);
                petCardAdapter.notifyDataSetChanged();
                return;
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
            @Override
            public void onClick(DialogInterface dialog, int which) {
                petCardAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                petCardAdapter.notifyItemRangeChanged(position, petCardAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                return;
            }
        }).show();  //show alert dialog
        //Remove swiped item from list and notify the RecyclerView
    }
}
