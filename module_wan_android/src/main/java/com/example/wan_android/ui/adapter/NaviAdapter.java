package com.example.wan_android.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.library_base.BaseRecyclerViewAdapter;
import com.example.library_base.BaseRecyclerViewHolder;
import com.example.library_base.bean.NavBean;
import com.example.wan_android.R;
import com.example.wan_android.databinding.ItemWanAndroidNavBinding;

import java.util.List;

public class NaviAdapter extends BaseRecyclerViewAdapter<NavBean.DataBean>{
    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_wan_android_nav);

    }
    private class ViewHolder extends BaseRecyclerViewHolder<NavBean.DataBean,ItemWanAndroidNavBinding>{

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(NavBean.DataBean dataBean, int position) {
            if(dataBean !=null ){
                binding.tvTitle.setSelected(dataBean.isSelected());
                binding.setBean(dataBean);
                binding.tvTitle.setOnClickListener(v -> {
                    setSelected(position);
                    if (listener != null) {
                        listener.onSelected(position);
                    }
                });
            }

        }
    }
    public void setSelected(int position) {
        List<NavBean.DataBean> data = getData();
        for (int i = 0; i < data.size(); i++) {
            if (i == position) {
                data.get(i).setSelected(true);
            } else {
                data.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    private OnSelectListener listener;

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSelectListener {
        void onSelected(int position);
    }
}
