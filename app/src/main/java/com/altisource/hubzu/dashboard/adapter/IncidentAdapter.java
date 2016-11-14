package com.altisource.hubzu.dashboard.adapter;

/**
 * Created by sunilhl on 11/11/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.AIncident;
import com.altisource.hubzu.dashboard.ui.adapters.MoreItemViewHolder;

import java.util.ArrayList;
import java.util.List;


public class IncidentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AIncident> data;
    private IncidentAdapter.OnItemClickedListener listener;
    private TextView tvStartId, tvStartTime, tvStatus, tvAssignedTo, tvComponent;
    private static final String ARG_ID = "id";

    public IncidentAdapter(List<AIncident> data, IncidentAdapter.OnItemClickedListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incident_list, parent, false);
                return new IncidentAdapter.SuccessItemViewHolder(v);
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false);
                return new MoreItemViewHolder(v, listener);
            default:
                // Nothing to do
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AIncident item = data.get(position);

        if (holder instanceof IncidentAdapter.SuccessItemViewHolder) {
            ((IncidentAdapter.SuccessItemViewHolder) holder).tvStartId.setText(String.valueOf(item.getId()));
            ((IncidentAdapter.SuccessItemViewHolder) holder).tvStartTime.setText(item.getStatus());
            ((IncidentAdapter.SuccessItemViewHolder) holder).tvStatus.setText(String.valueOf(item.getStartTime()));
            ((IncidentAdapter.SuccessItemViewHolder) holder).tvAssignedTo.setText(item.getComponent());
            ((IncidentAdapter.SuccessItemViewHolder) holder).tvComponent.setText(item.getAssignedTo());

        } else {
            // Nothing to do
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void appendItemsToList(ArrayList<AIncident> moreItems) {
        if (data.get(data.size() - 1).getType() == 2) {
            // Should always be true. Remove the last "more" item
            data.remove(data.size() - 1);
        }
        data.addAll(moreItems);

        notifyDataSetChanged();
    }

    class SuccessItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStartId, tvStartTime, tvStatus, tvAssignedTo, tvComponent;
        //private CardView mCardView;

        public SuccessItemViewHolder(View itemView) {
            super(itemView);

            //mCardView = (CardView) itemView.findViewById(R.id.cardIncident);
            tvStartId = (TextView) itemView.findViewById(R.id.tvUserId);
            tvStartTime = (TextView) itemView.findViewById(R.id.tvListingId);
            tvStatus = (TextView) itemView.findViewById(R.id.tvCreatedOn);
            tvAssignedTo = (TextView) itemView.findViewById(R.id.tvAssignedTo);
            tvComponent = (TextView) itemView.findViewById(R.id.tvComponentName);

            itemView.findViewById(R.id.cardIncident).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (listener != null) {
                                AIncident id = data.get(getLayoutPosition());
                                listener.onIncidentSelected(
                                        id.getId()
                                        //id.getStartTime()
                                        /*id.getSourceId(),
                                        id.getComponentName(),
                                        id.getCreatedDate()*/
                                );
                            }
                        }
                    }
            );
        }
    }

    public interface OnItemClickedListener extends MoreItemViewHolder.OnMoreClickedListener {
        //void onInfoCLicked(String step, String error, String trace);
        void onIncidentSelected(Long Id);
    }
}