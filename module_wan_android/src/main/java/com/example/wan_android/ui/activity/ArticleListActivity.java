package com.example.wan_android.ui.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.example.library_base.BaseActivity;
import com.example.library_base.bean.HomeListBean;
import com.example.library_base.utils.CommonUtils;
import com.example.wan_android.R;
import com.example.wan_android.databinding.ActivityArticleListBinding;
import com.example.wan_android.ui.adapter.WanAndroidAdapter;
import com.example.wan_android.viewmodel.WanAndroidListViewModel;
import com.example.xrecyclerview.XRecyclerView;

public class ArticleListActivity extends BaseActivity<WanAndroidListViewModel,ActivityArticleListBinding>{

    private WanAndroidAdapter mAdapter;
    private int cid = 0;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_article_list;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        cid = getIntent().getIntExtra("cid", 0);
        String chapterName = getIntent().getStringExtra("chapterName");
        setTitle(chapterName);
        mAdapter.setNoShowChapterName();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.xrvWan.setLayoutManager(new LinearLayoutManager(this));
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.clearHeader();
        mAdapter = new WanAndroidAdapter(this);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.srlWan.setOnRefreshListener(() -> bindingView.srlWan.postDelayed(() -> {
            bindingView.xrvWan.reset();
            viewModel.setPage(0);
            initData();
        }, 500));
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                initData();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.getHomeList(cid).observe(this, this::showContent);
    }
    private void showContent(HomeListBean homeListBean) {
        showContentView();
        if (bindingView.srlWan.isRefreshing()) {
            bindingView.srlWan.setRefreshing(false);
        }

        if (homeListBean != null) {
            if (viewModel.getPage() == 0) {
                mAdapter.clear();
            }
            mAdapter.addAll(homeListBean.getData().getDatas());
            mAdapter.notifyDataSetChanged();
            bindingView.xrvWan.refreshComplete();

        } else {
            if (viewModel.getPage() == 0) {
                showError();
            } else {
                bindingView.xrvWan.noMoreLoading();
            }
        }
    }
    @Override
    protected void onRefresh() {
        super.onRefresh();
        initData();
    }

    public static void start(Context mContext, int cid, String chapterName) {
        Intent intent = new Intent(mContext, ArticleListActivity.class);
        intent.putExtra("cid", cid);
        intent.putExtra("chapterName", chapterName);
        mContext.startActivity(intent);
    }
}
