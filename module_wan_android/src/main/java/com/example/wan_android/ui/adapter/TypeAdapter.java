package com.example.wan_android.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.library_base.BaseRecyclerViewAdapter;
import com.example.library_base.BaseRecyclerViewHolder;
import com.example.library_base.bean.TypeBean;
import com.example.wan_android.R;
import com.example.wan_android.databinding.ItemWanAndroidTypeBinding;
import com.example.wan_android.ui.activity.ArticleListActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class TypeAdapter extends BaseRecyclerViewAdapter<TypeBean.DataBean> {
    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_wan_android_type);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<TypeBean.DataBean, ItemWanAndroidTypeBinding> {


        public ViewHolder(ViewGroup viewGroup, int item_wan_android_type) {
            super(viewGroup,item_wan_android_type);
        }

        @Override
        public void onBindViewHolder(TypeBean.DataBean data, int position) {
            if(data !=null){
                String name = data.getName();
                binding.setBean(data);
                List<TypeBean.DataBean.ChildrenBean> children = data.getChildren();
                if (children != null && children.size() > 0) {
                    showTreeView(binding.tflTree, children);
                }
            }
        }

      


    }

    private void showTreeView(TagFlowLayout flowlayoutHot, List<TypeBean.DataBean.ChildrenBean> children) {
        flowlayoutHot.setAdapter(new TagAdapter<TypeBean.DataBean.ChildrenBean>(children) {
            @Override
            public View getView(FlowLayout parent, int position, TypeBean.DataBean.ChildrenBean o) {
                TextView textView = (TextView) View.inflate(parent.getContext(), R.layout.layout_tree_tag, null);
                textView.setText(Html.fromHtml(o.getName()));
                return textView;
            }
        });
        flowlayoutHot.setOnTagClickListener((view, position, parent) -> {
            TypeBean.DataBean.ChildrenBean childrenBean = children.get(position);
            ArticleListActivity.start(view.getContext(), childrenBean.getId(), childrenBean.getName());
            return true;
        });
    }
}