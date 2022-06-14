package com.example.alpha_care;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class EventCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<PetEventCard> petEventCardList;

    public EventCardAdapter (){ }

    public EventCardAdapter setPetEventCardList(List<PetEventCard> petEventCardList) {
        this.petEventCardList = petEventCardList;

        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.petprofile_event_card, parent, false);
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
        holder.eventCard_LBL_contactName.setText(petEventCard.getMadeEventContact().getFirstName() + " " + petEventCard.getMadeEventContact().getLastName());
        holder.eventCard_LBL_eventTime.setText(petEventCard.getDateExecution().toString());
    }

    @Override
    public int getItemCount() {
        return petEventCardList.size();
    }

    public PetEventCard getPetEventCard(int position){
        return petEventCardList.get(position);
    }

    class EventCardHolder extends RecyclerView.ViewHolder {
        private MaterialTextView eventCard_LBL_contactName, eventCard_LBL_eventTime;

        public EventCardHolder(View itemView) {
            super(itemView);
            eventCard_LBL_contactName = itemView.findViewById(R.id.eventCard_LBL_contactName);
            eventCard_LBL_eventTime = itemView.findViewById(R.id.eventCard_LBL_eventTime);
        }
    }
}
