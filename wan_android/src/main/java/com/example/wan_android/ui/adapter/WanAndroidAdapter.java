package com.example.wan_android.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.library_base.BaseRecyclerViewAdapter;
import com.example.library_base.BaseRecyclerViewHolder;

import com.example.library_base.bean.ArticlesBean;
import com.example.library_base.listener.Navigator;
import com.example.library_base.user.UserUtils;
import com.example.library_base.utils.DebugUtil;
import com.example.library_base.utils.PerfectClickListener;
import com.example.library_base.utils.ToastUtil;
import com.example.library_base.viewmodel.CollectModel;
import com.example.wan_android.R;

import com.example.wan_android.databinding.ItemWanAndroidBannerBinding;
import com.example.wan_android.databinding.ItemWanAndroidBinding;

/**
 * Created by jingbin on 2016/11/25.
 */

public class WanAndroidAdapter extends BaseRecyclerViewAdapter<ArticlesBean> {

    private Activity activity;
    private CollectModel model;
    /**
     * 是我的收藏页进来的，全部是收藏状态。bean里面没有返回isCollect信息
     */
    public boolean isCollectList = false;
    /**
     * 不显示类别信息
     */
    public boolean isNoShowChapterName = false;
    /**
     * 列表中是否显示图片
     */
    private boolean isNoImage = false;

    public WanAndroidAdapter(Activity activity) {
        this.activity = activity;
        model = new CollectModel();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_wan_android_banner);

    }

    public void setCollectList() {
        this.isCollectList = true;
    }

    public void setNoShowChapterName() {
        this.isNoShowChapterName = true;
    }

    public void setNoImage() {
        this.isNoImage = true;
    }

   private class ViewHolder extends BaseRecyclerViewHolder<ArticlesBean,ItemWanAndroidBannerBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

       @Override
       public void onBindViewHolder(ArticlesBean bean, int position) {
           if (bean != null) {
               binding.setBean(bean);
               binding.setAdapter(WanAndroidAdapter.this);
               if (!TextUtils.isEmpty(bean.getEnvelopePic()) && !isNoImage) {
                   bean.setShowImage(true);
               } else {
                   bean.setShowImage(false);
               }

               binding.vbCollect.setOnClickListener(new PerfectClickListener() {
                   @Override
                   protected void onNoDoubleClick(View v) {
                       if (UserUtils.isLogin() && model != null) {
                           // 为什么状态值相反？因为点了之后控件已改变状态
                           DebugUtil.error("-----binding.vbCollect.isChecked():" + binding.vbCollect.isChecked());
                           if (!binding.vbCollect.isChecked()) {
                               model.unCollect(isCollectList, bean.getId(), bean.getOriginId(), new Navigator.OnCollectNavigator() {
                                   @Override
                                   public void onSuccess() {
                                       if (isCollectList) {
                                           int indexOf = getData().indexOf(bean);
                                           // 角标始终加一
                                           int adapterPosition = getAdapterPosition();

                                           DebugUtil.error("getAdapterPosition():" + getAdapterPosition());
                                           DebugUtil.error("indexOf:" + indexOf);
                                           // 移除数据增加删除动画
                                           getData().remove(indexOf);
                                           notifyItemRemoved(adapterPosition);
                                       } else {
                                           bean.setCollect(binding.vbCollect.isChecked());
                                           ToastUtil.showToastLong("已取消收藏");
                                       }
                                   }

                                   @Override
                                   public void onFailure() {
                                       bean.setCollect(true);
                                       notifyItemChanged(getAdapterPosition());
                                       ToastUtil.showToastLong("取消收藏失败");
                                   }
                               });
                           } else {
                               model.collect(bean.getId(), new Navigator.OnCollectNavigator() {
                                   @Override
                                   public void onSuccess() {
                                       bean.setCollect(true);
                                       ToastUtil.showToastLong("收藏成功");
                                   }

                                   @Override
                                   public void onFailure() {
                                       ToastUtil.showToastLong("收藏失败");
                                       bean.setCollect(false);
                                       notifyItemChanged(getAdapterPosition());
                                   }
                               });
                           }
                       } else {
                           bean.setCollect(false);
                           notifyItemChanged(getAdapterPosition());

                           ARouter.getInstance().build("/module_user/login").navigation();
                       }
                   }
               });
           }
       }




    }

    public void openDetail(ArticlesBean bean) {
      //  WebViewActivity.loadUrl(activity, bean.getLink(), bean.getTitle());
    }

    public void openArticleList(ArticlesBean bean) {
       // ArticleListActivity.start(activity, bean.getChapterId(), bean.getChapterName());
    }
}
