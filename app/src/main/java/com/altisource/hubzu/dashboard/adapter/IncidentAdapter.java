package com.altisource.hubzu.dashboard.adapter;

/**
 * Created by sunilhl on 11/11/16.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.network.Incident;
import com.altisource.hubzu.dashboard.ui.adapters.MoreItemViewHolder;
import java.util.List;


public class IncidentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Incident> mIncidentList;
    private Context mContext;
    private IncidentListAdapter.OnItemClickedListener listener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public IncidentAdapter(List<Incident> incidentList,IncidentListAdapter.OnItemClickedListener listener,Context context) {
        this.mIncidentList = incidentList;
        this.mContext = context;
        this.listener = listener;
    }

    // // 0 - success, 1 - fail, 2 - more
    @Override
    public int getItemViewType(int position) {
        return mIncidentList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStartId, tvStartTime, tvStatus, tvAssignedTo, tvComponent;
        public CardView mCardView;

        public MyViewHolder(View view) {
            super(view);
            mCardView = (CardView) view.findViewById(R.id.cardIncident);
            tvStartId = (TextView) view.findViewById(R.id.tvUserId);
            tvStartTime = (TextView) view.findViewById(R.id.tvListingId);
            tvStatus = (TextView) view.findViewById(R.id.tvCreatedOn);
            tvAssignedTo = (TextView) view.findViewById(R.id.tvAssignedTo);
            tvComponent = (TextView) view.findViewById(R.id.tvComponentName);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incident_list, parent, false);
                return new IncidentAdapter.MyViewHolder(v);
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false);
                return new MoreItemViewHolder(v, listener);
            default:
                return null;

      }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Incident incident = mIncidentList.get(position);
        ((MyViewHolder) holder).tvStartId.setText(String.valueOf(incident.getId()));
        ((MyViewHolder) holder).tvStartTime.setText(incident.getStatus());
        ((MyViewHolder) holder).tvStatus.setText(String.valueOf(incident.getStartTime()));
        ((MyViewHolder) holder).tvAssignedTo.setText(incident.getComponent());
        ((MyViewHolder) holder).tvComponent.setText(incident.getAssignedTo());
    }

    @Override
    public int getItemCount() {
        return mIncidentList.size();
    }
}

