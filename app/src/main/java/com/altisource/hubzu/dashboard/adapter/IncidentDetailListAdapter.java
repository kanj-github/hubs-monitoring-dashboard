package com.altisource.hubzu.dashboard.adapter;

/**
 * Created by sunilhl on 04/11/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.IncidentDetail;
import com.altisource.hubzu.dashboard.ui.adapters.MoreItemViewHolder;

import java.util.List;

public class IncidentDetailListAdapter extends RecyclerView.Adapter<IncidentDetailListAdapter.IncidentDetailViewHolder> {

    private List<IncidentDetail> mIncidentDetailList;
    private OnItemClickedListener mListener;

    public class IncidentDetailViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserId, tvListingId, tvCreatedOn, tvComponent;

        public IncidentDetailViewHolder(View view) {
            super(view);
            tvUserId = (TextView) view.findViewById(R.id.tvUserId);
            tvListingId = (TextView) view.findViewById(R.id.tvListingId);
            tvCreatedOn = (TextView) view.findViewById(R.id.tvCreatedOn);
            tvComponent = (TextView) view.findViewById(R.id.tvComponentName);
            view.findViewById(R.id.cardIncidentDetail).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mListener != null) {
                                IncidentDetail id = mIncidentDetailList.get(getLayoutPosition());
                                mListener.onIncidentSelected(
                                        id.getTransactionId(),
                                        id.getUserId(),
                                        id.getSourceId(),
                                        id.getComponentName(),
                                        id.getCreatedDate()
                                );
                            }
                        }
                    }
            );
        }
    }

    public IncidentDetailListAdapter(List<IncidentDetail> mIncidentDetailList, OnItemClickedListener mListener) {
        this.mIncidentDetailList = mIncidentDetailList;
        this.mListener = mListener;
    }

    @Override
    public IncidentDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_incident_detail, parent, false);
        return new IncidentDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IncidentDetailViewHolder holder, int position) {
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

    public interface OnItemClickedListener extends MoreItemViewHolder.OnMoreClickedListener {
        void onIncidentSelected(String transactionId, String userId, String listingId, String component, Long createdOn);
    }
}
