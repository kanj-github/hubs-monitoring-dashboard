package com.altisource.hubzu.dashboard.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.PendingIncidentItem;
import com.altisource.hubzu.dashboard.network.PendingIncident;

import java.util.ArrayList;

public class PendingIncidentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PendingIncidentItem> data;
    private OnItemClickedListener mListener;

    public PendingIncidentsAdapter(ArrayList<PendingIncidentItem> data, OnItemClickedListener mListener) {
        this.data = data;
        this.mListener = mListener;
    }

    // 0 - pending incident, 1 - more
    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_incident, parent, false);
                return new PendingIncidentHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false);
                return new MoreItemViewHolder(view, mListener);
            default:
                // Get ready for fuck up
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PendingIncidentHolder) {
            PendingIncidentHolder pih = (PendingIncidentHolder) holder;
            PendingIncidentItem item = data.get(position);
            String errorMessage = item.getErrorMessage();
            pih.error.setText(errorMessage.substring(0, errorMessage.indexOf(":")));
            pih.number.setText("" + item.getNumberOfUsersImpacted());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void appendItemsToList(ArrayList<PendingIncidentItem> moreItems) {
        if (data.get(data.size() - 1).getType() == 1) {
            // Should always be true. Remove the last "more" item
            data.remove(data.size() - 1);
        }
        data.addAll(moreItems);

        notifyDataSetChanged();
    }

    public class PendingIncidentHolder extends RecyclerView.ViewHolder {
        public final TextView error, number;

        public PendingIncidentHolder(View view) {
            super(view);
            error = (TextView) view.findViewById(R.id.error);
            number = (TextView) view.findViewById(R.id.number);
            view.findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PendingIncidentItem pii = data.get(getLayoutPosition());
                    if (mListener != null) {
                        mListener.onInfoCLicked(pii.getErrorMessage(), pii.getStackTrace());
                    }
                }
            });
        }
    }

    public interface OnItemClickedListener extends MoreItemViewHolder.OnMoreClickedListener {
        void onInfoCLicked(String error, String trace);
    }
}
