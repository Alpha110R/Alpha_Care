package com.example.alpha_care.AdaptersToRecycleView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.CallBacks.CallBack_EventCardDelete;
import com.example.alpha_care.Enums.EnumPetEventType;
import com.example.alpha_care.Objects.PetEventCard;
import com.example.alpha_care.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class EventCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<PetEventCard> petEventCardList;
    private CallBack_EventCardDelete callBack_eventCardDelete;

    public EventCardAdapter (){ }

    public EventCardAdapter setPetEventCardList(List<PetEventCard> petEventCardList) {
        this.petEventCardList = petEventCardList;
        return this;
    }

    public EventCardAdapter setCallBack_EventCardDelete(CallBack_EventCardDelete callBack_eventCardDelete){
        this.callBack_eventCardDelete = callBack_eventCardDelete;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.petprofilepage_event_card, parent, false);
        EventCardHolder eventCardHolder = new EventCardHolder(view);
        return eventCardHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final EventCardHolder holder = (EventCardHolder) viewHolder;
        PetEventCard petEventCard = getPetEventCard(position);
/**
 * Connect to view
 */
        holder.eventCard_LBL_contactName.setText(petEventCard.getEventCardCreatorContact());
        holder.eventCard_LBL_eventTime.setText(petEventCard.getDateExecution().toString());
        holder.eventCard_LBL_deleteEventCard.setOnClickListener(view -> {
            callBack_eventCardDelete.clicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return petEventCardList == null ? 0 : petEventCardList.size();
    }

    public PetEventCard getPetEventCard(int position){
        return petEventCardList.get(position);
    }

    class EventCardHolder extends RecyclerView.ViewHolder {
        private MaterialTextView eventCard_LBL_contactName, eventCard_LBL_eventTime;
        private MaterialButton eventCard_LBL_deleteEventCard;

        public EventCardHolder(View itemView) {
            super(itemView);
            eventCard_LBL_contactName = itemView.findViewById(R.id.eventCard_LBL_contactName);
            eventCard_LBL_eventTime = itemView.findViewById(R.id.eventCard_LBL_eventTime);
            eventCard_LBL_deleteEventCard = itemView.findViewById(R.id.eventCard_LBL_deleteEventCard);
        }
    }
}
