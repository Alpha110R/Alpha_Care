package com.example.alpha_care.AdaptersToRecycleView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.CallBacks.CallBack_PetCard;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Pet> petCardList;
    private CallBack_PetCard callBack_petCard;
    public PetCardAdapter (){}
    public PetCardAdapter setPetCardList(Map<String, Pet> petCardList){
        this.petCardList = convertMapToList(petCardList);
        return this;
    }

    public PetCardAdapter setCallBack_PetCard(CallBack_PetCard callBack_petCard) {
        this.callBack_petCard = callBack_petCard;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_pet_card, parent, false);
        PetCardHolder petCardHolder = new PetCardHolder(view);
        return petCardHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final PetCardHolder holder = (PetCardHolder) viewHolder;
        Pet pet = getPet(position);
/**
 * Connect to view
 */
        holder.homePage_LBL_petName.setText(pet.getName());
        holder.homePage_LBL_petAge.setText(pet.getAge()+"");
        holder.homePage_IMG_petImage.setImageResource(R.drawable.ic_addpet_empty);
    }

    @Override
    public int getItemCount() {
        return petCardList.size();
    }

    public Pet getPet(int position){
        return petCardList.get(position);
    }

    class PetCardHolder extends RecyclerView.ViewHolder {
        private MaterialTextView homePage_LBL_petName, homePage_LBL_petAge;
        private AppCompatImageView homePage_IMG_petImage;

        public PetCardHolder(View itemView) {
            super(itemView);
            homePage_LBL_petName = itemView.findViewById(R.id.homePage_LBL_petName);
            homePage_LBL_petAge = itemView.findViewById(R.id.homePage_LBL_petAge);
            homePage_IMG_petImage = itemView.findViewById(R.id.homePage_IMG_petImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack_petCard != null) {
                        Log.d("tagg", "pet clicked recycleView: " + getPet(getAdapterPosition()).getName());
                        callBack_petCard.clicked(getPet(getAdapterPosition()));
                    }
                }
            });
        }
    }

    //Convert Map to List to show the pets in the recycleView
    public List<Pet> convertMapToList(Map<String, Pet> petCardList){
        return new ArrayList<Pet>(petCardList.values());
    }

}
