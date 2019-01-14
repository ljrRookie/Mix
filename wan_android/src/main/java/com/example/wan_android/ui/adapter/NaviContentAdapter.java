package com.example.wan_android.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library_base.BaseRecyclerViewAdapter;
import com.example.library_base.BaseRecyclerViewHolder;
import com.example.library_base.bean.ArticlesBean;
import com.example.library_base.bean.NavBean;
import com.example.library_base.utils.DensityUtil;
import com.example.wan_android.R;
import com.example.wan_android.databinding.ItemWanAndroidNavContentBinding;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class NaviContentAdapter extends BaseRecyclerViewAdapter<NavBean.DataBean> {
    private Context activity;
    public NaviContentAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return  new ViewHolder(viewGroup, R.layout.item_wan_android_nav_content);

    }
    private class ViewHolder extends BaseRecyclerViewHolder<NavBean.DataBean,ItemWanAndroidNavContentBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final NavBean.DataBean dataBean, final int position) {
            if (dataBean != null) {
                binding.setBean(dataBean);
                showTagView(binding.tflContent, dataBean.getArticles());
            }
        }
    }

    private void showTagView(TagFlowLayout flowlayoutHot, final List<ArticlesBean> beanList) {
        flowlayoutHot.setAdapter(new TagAdapter<ArticlesBean>(beanList) {
            @Override
            public View getView(FlowLayout parent, int position, ArticlesBean bean) {
                TextView textView = getTextView();
                textView.setText(Html.fromHtml(bean.getTitle()));
                return textView;
            }
        });
        flowlayoutHot.setOnTagClickListener((view, position, parent) -> {
          //  WebViewActivity.loadUrl(view.getContext(), beanList.get(position).getLink(), beanList.get(position).getTitle());
            return true;
        });
    }

    private TextView getTextView() {
        final TextView hotText = new TextView(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        hotText.setLayoutParams(lp);
        hotText.setTextSize(13);
        int left, top, right, bottom;
        hotText.setMaxLines(1);
        left = top = right = bottom = DensityUtil.dip2px(5);
        hotText.setBackgroundResource(R.drawable.shape_navi_tag);
        hotText.setGravity(Gravity.CENTER);
        lp.setMargins(left, top, right, bottom);
        return hotText;
    }
}
