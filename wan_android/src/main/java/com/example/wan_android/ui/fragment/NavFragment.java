package com.example.wan_android.ui.fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.library_base.BaseFragment;
import com.example.library_base.bean.NavBean;
import com.example.wan_android.R;
import com.example.wan_android.databinding.FragmentWanAndroidNavBinding;
import com.example.wan_android.ui.adapter.NaviAdapter;
import com.example.wan_android.ui.adapter.NaviContentAdapter;
import com.example.wan_android.viewmodel.NavViewModel;

public class NavFragment extends BaseFragment<NavViewModel,FragmentWanAndroidNavBinding>{
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FragmentActivity activity;
    private NaviAdapter naviAdapter;
    private NaviContentAdapter naviContentAdapter;
    private int oldPosition = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static NavFragment newInstance() {
        return new NavFragment();
    }
    @Override
    public int setContent() {
        return R.layout.fragment_wan_android_nav;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initRefreshView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        bindingView.xrvNavi.setLayoutManager(layoutManager);
        naviAdapter = new NaviAdapter();
        bindingView.xrvNavi.setAdapter(naviAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(activity);
        bindingView.xrvNaviDetail.setLayoutManager(layoutManager2);
        naviContentAdapter = new NaviContentAdapter(activity);
        bindingView.xrvNaviDetail.setAdapter(naviContentAdapter);
        naviAdapter.setOnSelectListener(position -> layoutManager2.scrollToPositionWithOffset(position, 0));
        bindingView.xrvNaviDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = layoutManager2.findFirstVisibleItemPosition();
                if (oldPosition != firstPosition) {
                    bindingView.xrvNavi.smoothScrollToPosition(firstPosition);
                    naviAdapter.setSelected(firstPosition);
                    oldPosition = firstPosition;
                }
            }
        });
    }
    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadCustomData();
    }

    private void loadCustomData() {
        viewModel.getNavData().observe(this, navBean -> {
            if (navBean != null
                    && navBean.getData() != null
                    && navBean.getData().size() > 0) {

                showContentView();
                naviAdapter.clear();
                naviAdapter.addAll(navBean.getData());
                naviAdapter.notifyDataSetChanged();
                naviAdapter.setSelected(0);

                naviContentAdapter.clear();
                naviContentAdapter.addAll(navBean.getData());
                naviContentAdapter.notifyDataSetChanged();

                mIsFirst = false;
            } else {
                showError();
            }
        });
    }
    @Override
    protected void onRefresh() {
        loadCustomData();
    }
}
