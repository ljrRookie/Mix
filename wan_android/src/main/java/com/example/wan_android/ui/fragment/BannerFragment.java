package com.example.wan_android.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.example.library_base.BaseFragment;
import com.example.library_base.bean.WanAndroidBannerBean;
import com.example.library_base.ui.activity.WebViewActivity;
import com.example.library_base.utils.CommonUtils;
import com.example.library_base.utils.DensityUtil;
import com.example.library_base.utils.GlideImageLoader;
import com.example.library_base.utils.ImageLoadUtil;
import com.example.wan_android.R;
import com.example.wan_android.viewmodel.WanAndroidListViewModel;
import com.example.wan_android.databinding.FragmentWanAndroidBannerBinding;
import com.example.wan_android.databinding.HeaderWanAndroidBinding;
import com.example.wan_android.ui.adapter.WanAndroidAdapter;
import com.example.xrecyclerview.XRecyclerView;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

public class BannerFragment extends BaseFragment<WanAndroidListViewModel,FragmentWanAndroidBannerBinding>{
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private WanAndroidAdapter mAdapter;
    private HeaderWanAndroidBinding androidBinding;
    private boolean isLoadBanner = false;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android_banner;
    }
    public static BannerFragment newInstance() {
        return new BannerFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();
        // 准备就绪
        mIsPrepared = true;
        loadData();
    }
    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getHomeList, 500);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (isLoadBanner) {
            androidBinding.banner.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 不可见时轮播图停止滚动
        if (isLoadBanner) {
            androidBinding.banner.stopAutoPlay();
        }
    }
    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.xrvWan.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.clearHeader();
        mAdapter = new WanAndroidAdapter(getActivity());
        mAdapter.setNoImage();
        bindingView.xrvWan.setAdapter(mAdapter);
       androidBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_wan_android, null, false);
        bindingView.xrvWan.addHeaderView(androidBinding.getRoot());
        DensityUtil.formatBannerHeight(androidBinding.banner, androidBinding.llBannerImage);
        bindingView.srlWan.setOnRefreshListener(this::swipeRefresh);
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                getHomeList();
            }
        });
       viewModel.getWanAndroidBanner().observe(this, bean -> {
            if (bean != null) {
                showBannerView(bean.getmBannerImages(), bean.getmBannerTitles(), bean.getData());
                androidBinding.rlBanner.setVisibility(View.VISIBLE);
            } else {
                androidBinding.rlBanner.setVisibility(View.GONE);
            }
        });
    }
    /**
     * 设置banner图
     */
   public void showBannerView(ArrayList<String> bannerImages, ArrayList<String> mBannerTitle, List<WanAndroidBannerBean.DataBean> result) {
        androidBinding.rlBanner.setVisibility(View.VISIBLE);
        androidBinding.banner.setBannerTitles(mBannerTitle);
        androidBinding.banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        androidBinding.banner.setImages(bannerImages).setImageLoader(new GlideImageLoader()).start();
        androidBinding.banner.setOnBannerListener(position -> {
            if (result.get(position) != null && !TextUtils.isEmpty(result.get(position).getUrl())) {
                WebViewActivity.loadUrl(getContext(), result.get(position).getUrl(), result.get(position).getTitle());
            }
        });
        int size = bannerImages.size();
        int position1 = 0;
        int position2 = 0;
        if (size > 1) {
            position1 = size - 2;
            position2 = size - 1;
        }
        ImageLoadUtil.displayFadeImage(androidBinding.ivBannerOne, bannerImages.get(position1), 3);
        ImageLoadUtil.displayFadeImage(androidBinding.ivBannerTwo, bannerImages.get(position2), 3);
        int finalPosition = position1;
        int finalPosition2 = position2;
       isLoadBanner = true;
    }
    /**
     * 下拉刷新
     */
    private void swipeRefresh() {
        bindingView.srlWan.postDelayed(() -> {
            viewModel.setPage(0);
            bindingView.xrvWan.reset();
            getHomeList();
        }, 350);
    }
    private void getHomeList() {
        viewModel.getHomeList(null).observe(this, homeListBean -> {
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

                if (viewModel.getPage() == 0) {
                    mIsFirst = false;
                }
            } else {
                if (viewModel.getPage() == 0) {
                    showError();
                } else {
                    bindingView.xrvWan.refreshComplete();
                    bindingView.xrvWan.noMoreLoading();
                }
            }
        });
    }
}
