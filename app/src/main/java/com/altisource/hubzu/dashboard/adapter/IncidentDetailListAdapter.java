package com.altisource.hubzu.dashboard.adapter;

/**
 * Created by sunilhl on 04/11/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.IncidentDetail;

import java.util.List;

public class IncidentDetailListAdapter extends RecyclerView.Adapter<IncidentDetailListAdapter.MyViewHolder> {

    private List<IncidentDetail> mIncidentDetailList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserId, tvListingId, tvCreatedOn, tvComponent;
        public CardView mCardView;

        public MyViewHolder(View view) {
            super(view);
            mCardView = (CardView) view.findViewById(R.id.cardIncidentDetail);
            tvUserId = (TextView) view.findViewById(R.id.tvUserId);
            tvListingId = (TextView) view.findViewById(R.id.tvListingId);
            tvCreatedOn = (TextView) view.findViewById(R.id.tvCreatedOn);
            tvComponent = (TextView) view.findViewById(R.id.tvComponentName);
        }
    }

    public IncidentDetailListAdapter(List<IncidentDetail> incidentDetailList, Context context) {
        this.mIncidentDetailList = incidentDetailList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_incident_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        IncidentDetail incident = mIncidentDetailList.get(position);
        holder.tvUserId.setText(String.valueOf(incident.getUserId()));
        holder.tvListingId.setText(String.valueOf(incident.getSourceId()));
        holder.tvCreatedOn.setText(String.valueOf(incident.getCreatedDate()));
        holder.tvComponent.setText(incident.getComponentName());
    }

    @Override
    public int getItemCount() {
        return mIncidentDetailList.size();
    }
}
