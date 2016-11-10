package com.altisource.hubzu.dashboard.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.ProcessDetailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naraykan on 09/11/16.
 */

public class ProcessDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ProcessDetailItem> data;
    private OnItemClickedListener listener;

    public ProcessDetailsAdapter(List<ProcessDetailItem> data, OnItemClickedListener listener) {
        this.data = data;
        this.listener = listener;
    }

    // // 0 - success, 1 - fail, 2 - more
    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process_detail_success, parent, false);
                return new SuccessItemViewHolder(v);
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process_detail_failure, parent, false);
                return new FailureItemViewHolder(v);
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false);
                return new MoreItemViewHolder(v, listener);
            default:
                // Get ready for fuck up
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProcessDetailItem item = data.get(position);

        if (holder instanceof SuccessItemViewHolder) {
            ((SuccessItemViewHolder) holder).stageTv.setText(item.getStage());
        } else if (holder instanceof FailureItemViewHolder) {
            ((FailureItemViewHolder) holder).stageTv.setText(item.getStage());
        } else {
            // Nothing to do
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void appendItemsToList(ArrayList<ProcessDetailItem> moreItems) {
        if (data.get(data.size() - 1).getType() == 2) {
            // Should always be true. Remove the last "more" item
            data.remove(data.size() - 1);
        }
        data.addAll(moreItems);

        notifyDataSetChanged();
    }

    public List<ProcessDetailItem> getListData() {
        return data;
    }

    class FailureItemViewHolder extends RecyclerView.ViewHolder {
        TextView stageTv;

        public FailureItemViewHolder(View itemView) {
            super(itemView);
            stageTv = (TextView) itemView.findViewById(R.id.stage);
            itemView.findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProcessDetailItem item = data.get(getLayoutPosition());
                    if (listener != null) {
                        listener.onInfoCLicked(
                                item.getStage(),
                                item.getError(),
                                item.getStackTrace()
                        );
                    }
                }
            });
        }
    }

    class SuccessItemViewHolder extends RecyclerView.ViewHolder {
        TextView stageTv;

        public SuccessItemViewHolder(View itemView) {
            super(itemView);
            stageTv = (TextView) itemView.findViewById(R.id.stage);
        }
    }

    public interface OnItemClickedListener extends MoreItemViewHolder.OnMoreClickedListener {
        void onInfoCLicked(String step, String error, String trace);
    }
}
