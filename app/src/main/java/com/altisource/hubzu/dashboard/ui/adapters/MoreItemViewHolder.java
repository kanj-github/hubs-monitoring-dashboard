package com.altisource.hubzu.dashboard.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.altisource.hubzu.dashboard.R;

/**
 * Created by naraykan on 10/11/16.
 */

public class MoreItemViewHolder extends RecyclerView.ViewHolder {
    OnMoreClickedListener listener;

    public MoreItemViewHolder(View itemView, final OnMoreClickedListener listener) {
        super(itemView);
        itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onMoreClicked();
                }
            }
        });
    }

    public interface OnMoreClickedListener {
        void onMoreClicked();
    }
}
