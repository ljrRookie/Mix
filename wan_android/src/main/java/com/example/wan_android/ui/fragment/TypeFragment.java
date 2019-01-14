package com.example.wan_android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.library_base.BaseFragment;
import com.example.library_base.utils.CommonUtils;
import com.example.wan_android.R;
import com.example.wan_android.databinding.FragmentWanAndroidTypeBinding;
import com.example.wan_android.ui.adapter.TypeAdapter;
import com.example.wan_android.viewmodel.TypeViewModel;


public class TypeFragment extends BaseFragment<TypeViewModel,FragmentWanAndroidTypeBinding>{
    private FragmentActivity activity;
    private TypeAdapter typeAdapter;
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    public static TypeFragment newInstance() {
        return new TypeFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android_type;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.srlWan.setOnRefreshListener(() -> {
            bindingView.xrvWan.reset();
            getType();
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        bindingView.xrvWan.setLayoutManager(layoutManager);
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.setLoadingMoreEnabled(false);
        bindingView.xrvWan.setNestedScrollingEnabled(false);
        bindingView.xrvWan.clearHeader();
        typeAdapter = new TypeAdapter();
        bindingView.xrvWan.setAdapter(typeAdapter);
    }
    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.srlWan.setRefreshing(true);
       getType();
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getType();
    }
    private void getType() {
        viewModel.getType().observe(this, TypeBean -> {
            showContentView();
            if (bindingView.srlWan.isRefreshing()) {
                bindingView.srlWan.setRefreshing(false);
            }
            if (TypeBean != null
                    && TypeBean.getData() != null
                    && TypeBean.getData().size() > 0) {

                typeAdapter.clear();
                typeAdapter.addAll(TypeBean.getData());
                typeAdapter.notifyDataSetChanged();
                bindingView.xrvWan.refreshComplete();

                mIsFirst = false;
            } else {
                if (mIsFirst) {
                    showError();
                } else {
                    bindingView.xrvWan.refreshComplete();
                }
            }
        });
    }

}
