package com.example.alpha_care.Fragments;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alpha_care.AdaptersToRecycleView.PetCardAdapter;
import com.example.alpha_care.CallBacks.CallBack_ContactPermission;
import com.example.alpha_care.CallBacks.CallBack_HomePageToBaseAddPet;
import com.example.alpha_care.CallBacks.CallBack_PetCard;
import com.example.alpha_care.CallBacks.CallBack_ReplaceFragment;
import com.example.alpha_care.MyFireStore;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {
    private PetCardAdapter petCardAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton homePage_FAB_addPet;
    private List <String> petsIDList;
    private List<Pet> petList;
    private CallBack_ContactPermission callBack_contactPermission = null;
    private CallBack_ReplaceFragment callBack_replaceFragment;
    private CallBack_HomePageToBaseAddPet callBack_homePageToBaseAddPet;


    public HomePageFragment(){
        petList = new ArrayList<>();
    }

    public HomePageFragment setCallBack_ContactPermission(CallBack_ContactPermission callBack_contactPermission){
        this.callBack_contactPermission = callBack_contactPermission;
        return this;
    }

    public HomePageFragment setCallBack_ReplaceFragment(CallBack_ReplaceFragment callBack_replaceFragment){
        this.callBack_replaceFragment = callBack_replaceFragment;
        return this;
    }

    public HomePageFragment setCallBack_HomePageToBaseAddPet(CallBack_HomePageToBaseAddPet callBack_homePageToBaseAddPet){
        this.callBack_homePageToBaseAddPet = callBack_homePageToBaseAddPet;
        return this;
    }

    public void addPetToList(Pet pet){
        petList.add(pet);
        Log.d("tagg", "addPetToList: " + pet.getName());
    }

    public HomePageFragment setPetsList(List<Pet> petList){
        this.petList = petList;
        if(petCardAdapter!=null)
            petCardAdapter.notifyDataSetChanged();
        return this;
    }

    public HomePageFragment setPetsIDList(List<String> petsIDList) {
        this.petsIDList = petsIDList;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.activity_pets_list, container, false);
        findViews(fragmentView);

        restartPetCardAdapterToListView();

        homePage_FAB_addPet.setOnClickListener(view -> {
            callBack_homePageToBaseAddPet.addPetClicked();
        });

        return fragmentView;
    }

    public void restartPetCardAdapterToListView() {
        //TODO: Retrieve pet data from the pet collection by the pet's UID in the user's list
        //petCardAdapter = new PetCardAdapter().setCallBack_PetCard(callBack_petCard);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(petCardAdapter);
    }

    private void findViews(View fragmentView) {
    }

    private CallBack_PetCard callBack_petCard = new CallBack_PetCard() {
        @Override
        public void clicked(Pet pet) {
            Log.d("tagg", "pet clicked recycleView: " + pet.getName());
            callBack_replaceFragment.replaceToPetFragment(pet);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        callBack_contactPermission.request();
    }
}
