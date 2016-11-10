package com.altisource.hubzu.dashboard.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;

/**
 * Created by naraykan on 09/11/16.
 */

public class ProcessDetailsAdapter {

    class FailureItemViewHolder extends RecyclerView.ViewHolder {
        TextView stageTv;
        ImageView info;

        public FailureItemViewHolder(View itemView) {
            super(itemView);
            stageTv = (TextView) itemView.findViewById(R.id.stage);
            info = (ImageView) itemView.findViewById(R.id.info);
        }
    }

    class SuccessItemViewHolder extends RecyclerView.ViewHolder {
        TextView stageTv;

        public SuccessItemViewHolder(View itemView) {
            super(itemView);
            stageTv = (TextView) itemView.findViewById(R.id.stage);
        }
    }
}
