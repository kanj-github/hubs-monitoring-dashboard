package com.altisource.hubzu.dashboard.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.Constants;
import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.UserActivityItem;

import java.util.ArrayList;

/**
 * Created by naraykan on 10/11/16.
 */

public class UserActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<UserActivityItem> data;
    private MoreItemViewHolder.OnMoreClickedListener mListener;
    private Context mContext;

    public UserActivityListAdapter(
            Context mContext,
            ArrayList<UserActivityItem> data,
            MoreItemViewHolder.OnMoreClickedListener mListener
    ) {
        this.data = data;
        this.mListener = mListener;
        this.mContext = mContext;
    }

    // 0 - activity, 1 - more
    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_activity, parent, false);
                return new ActivityViewHolder(v);
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false);
                return new MoreItemViewHolder(v, mListener);
            default:
                // Get ready for fuck up
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserActivityItem item = data.get(position);

        if (holder instanceof ActivityViewHolder) {
            ActivityViewHolder avh = (ActivityViewHolder) holder;
            avh.start.setText(item.getStartTime());
            avh.end.setText(item.getEndTime());
            avh.status.setText(item.getStatus());
            if (Constants.INCIDENT_PROCESS_DETAIL_STATE_SUCCESS.equals(item.getStatus())) {
                avh.status.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_green_light));
            } else {
                avh.status.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_red_light));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void appendItemsToList(ArrayList<UserActivityItem> moreItems) {
        if (data.get(data.size() - 1).getType() == 1) {
            // Should always be true. Remove the last "more" item
            data.remove(data.size() - 1);
        }
        data.addAll(moreItems);

        notifyDataSetChanged();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView start, end, status;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            start = (TextView) itemView.findViewById(R.id.start);
            end = (TextView) itemView.findViewById(R.id.end);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
