package com.example.alpha_care.Fragments;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alpha_care.AdaptersToRecycleView.PetCardAdapter;
import com.example.alpha_care.CallBacks.CallBack_ContactPermission;
import com.example.alpha_care.CallBacks.CallBack_PetCard;
import com.example.alpha_care.CallBacks.CallBack_ReplaceFragment;
import com.example.alpha_care.DataManager;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.R;

public class HomePageFragment extends Fragment {
    private PetCardAdapter petCardAdapter;
    private RecyclerView recyclerView;
    private User user;
    private Activity activity;
    private CallBack_ContactPermission callBack_contactPermission = null;
    private CallBack_ReplaceFragment callBack_replaceFragment;

    public HomePageFragment(Activity context){
        this.activity = context;
    }

    public HomePageFragment setCallBack_ContactPermission(CallBack_ContactPermission callBack_contactPermission){
        this.callBack_contactPermission = callBack_contactPermission;
        return this;
    }

    public HomePageFragment setCallBack_ReplaceFragment(CallBack_ReplaceFragment callBack_replaceFragment){
        this.callBack_replaceFragment = callBack_replaceFragment;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.activity_home, container, false);
        findViews(fragmentView);
        user = DataManager.generateUser();
        restartPetCardAdapterToListView();

        return fragmentView;
    }

    private void restartPetCardAdapterToListView() {
        petCardAdapter = new PetCardAdapter().setPetCardList(user.getMyPets()).setCallBack_PetCard(callBack_petCard);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(petCardAdapter);
    }

    private void findViews(View fragmentView) {
        recyclerView = fragmentView.findViewById(R.id.homePage_LST_pets);
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
