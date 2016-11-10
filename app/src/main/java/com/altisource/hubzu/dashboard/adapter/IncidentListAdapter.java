package com.altisource.hubzu.dashboard.adapter;

/**
 * Created by sunilhl on 04/11/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.network.Incident;

import java.util.List;

public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.MyViewHolder> {

    private List<Incident> mIncidentList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStartId, tvStartTime, tvStatus, tvAssignedTo, tvComponent;

        public MyViewHolder(View view) {
            super(view);
            tvStartId = (TextView) view.findViewById(R.id.tvStartId);
            tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            tvAssignedTo = (TextView) view.findViewById(R.id.tvAssignedTo);
            tvComponent = (TextView) view.findViewById(R.id.tvComponent);
        }
    }

    public IncidentListAdapter(List<Incident> incidentList) {
        this.mIncidentList = incidentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_list_row, parent, false);

        mContext = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Incident incident = mIncidentList.get(position);
        holder.tvStartId.setText(String.valueOf(incident.getId()));
        holder.tvStartTime.setText(incident.getStatus());
        holder.tvStatus.setText(String.valueOf(incident.getStartTime()));
        holder.tvAssignedTo.setText(incident.getComponent());
        holder.tvComponent.setText(incident.getAssignedTo());
    }

    @Override
    public int getItemCount() {
        return mIncidentList.size();
    }
}
