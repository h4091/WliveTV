package com.ywl5320.wlivetv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ywl on 15-10-11.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{

    private List<String> datas;

    public RecycleAdapter(List<String> datas) {
        super();
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), android.R.layout.simple_list_item_1, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView;
        }
    }
}
