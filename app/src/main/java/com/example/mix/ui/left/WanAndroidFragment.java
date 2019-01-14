package com.example.mix.ui.left;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.example.library_base.BaseFragment;

import com.example.library_base.viewmodel.NoViewModel;
import com.example.mix.R;
import com.example.mix.adapter.viewpager.MyFragmentPagerAdapter;
import com.example.mix.databinding.FragmentWanAndroidBinding;
import com.example.wan_android.ui.fragment.NavFragment;
import com.example.wan_android.ui.fragment.TypeFragment;
import com.example.wan_android.ui.fragment.BannerFragment;

import java.util.ArrayList;

public class WanAndroidFragment extends BaseFragment<NoViewModel,FragmentWanAndroidBinding> {
    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);
    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        initFragemntList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        bindingView.vpBook.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpBook.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        bindingView.tabBook.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabBook.setupWithViewPager(bindingView.vpBook);
        showContentView();
    }

    private void initFragemntList() {
        mTitleList.clear();
        mTitleList.add("玩安卓");
        mTitleList.add("安卓体系");
        mTitleList.add("知识导航");
        mFragments.add(BannerFragment.newInstance());
        mFragments.add(TypeFragment.newInstance());
        mFragments.add(NavFragment.newInstance());
    }

}
