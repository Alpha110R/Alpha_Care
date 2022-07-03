package com.example.alpha_care.AdaptersToRecycleView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.CallBacks.CallBack_PetCard;
import com.example.alpha_care.CallBacks.CallBack_UserNameContactCard;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AddContactCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> UserNameList;
    private CallBack_UserNameContactCard callBack_userNameContactCard;
    private Activity activity;
    public AddContactCardAdapter (Activity activity){
        this.activity = activity;
    }
    public AddContactCardAdapter setUserNameList(List<String> userNameList){
        this.UserNameList = userNameList;
        return this;
    }

    public AddContactCardAdapter setCallBack_UserNameContactCard(CallBack_UserNameContactCard callBack_userNameContactCard) {
        this.callBack_userNameContactCard = callBack_userNameContactCard;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addcontacttopet_searchcontact_card, parent, false);
        UserNameCardHolder userNameCardHolder = new UserNameCardHolder(view);
        return userNameCardHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final UserNameCardHolder holder = (UserNameCardHolder) viewHolder;
        String userNameContact = getUserNameContact(position);

        holder.addContact_LBL_userName.setText(userNameContact);
    }

    @Override
    public int getItemCount() {
        return UserNameList.size();
    }

    public String getUserNameContact(int position){
        return UserNameList.get(position);
    }

    class UserNameCardHolder extends RecyclerView.ViewHolder {
        private MaterialTextView addContact_LBL_userName;

        public UserNameCardHolder(View itemView) {
            super(itemView);
            addContact_LBL_userName = itemView.findViewById(R.id.addContact_LBL_userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack_userNameContactCard != null) {
                        callBack_userNameContactCard.clicked(getUserNameContact(getAdapterPosition()));
                    }
                }
            });
        }
    }

}
