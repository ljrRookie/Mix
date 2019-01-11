package com.example.mix;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.mix.adapter.viewpager.MyFragmentPagerAdapter;
import com.example.mix.databinding.ActivityMainBinding;
import com.example.mix.ui.left.TestFragemnt;
import com.example.mix.ui.left.WanAndroidFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private ViewPager vpContent;
    private ActivityMainBinding mBinding;
    private ImageView ivTitleTwo;
    private ImageView ivTitleOne;
    private ImageView ivTitleThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initId();
        initContentFragment();
        initDrawerLayout();
        initListener();
    }

    private void initDrawerLayout() {

    }

    private void initListener() {
        mBinding.ivTitleOne.setOnClickListener(this);
        mBinding.ivTitleTwo.setOnClickListener(this);
        mBinding.ivTitleThree.setOnClickListener(this);
        mBinding.navView.setNavigationItemSelectedListener(this);

    }
    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new WanAndroidFragment());
        mFragmentList.add(new TestFragemnt());
        mFragmentList.add(new TestFragemnt());
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(this);

        setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_two:
                // 不然cpu会有损耗
                if (vpContent.getCurrentItem() != 1) {
                    setCurrentItem(1);
                }
                break;
            case R.id.iv_title_one:
                if (vpContent.getCurrentItem() != 0) {
                    setCurrentItem(0);
                }
                break;
            case R.id.iv_title_three:
                if (vpContent.getCurrentItem() != 2) {
                    setCurrentItem(2);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 切换页面
     *
     * @param position 分类角标
     */
    private void setCurrentItem(int position) {
        boolean isOne = false;
        boolean isTwo = false;
        boolean isThree = false;
        switch (position) {
            case 0:
                isOne = true;
                break;
            case 1:
                isTwo = true;
                break;
            case 2:
                isThree = true;
                break;
            default:
                isTwo = true;
                break;
        }
        vpContent.setCurrentItem(position);
        ivTitleOne.setSelected(isOne);
        ivTitleTwo.setSelected(isTwo);
        ivTitleThree.setSelected(isThree);
    }
    private void initId() {
        drawerLayout = mBinding.drawerLayout;
        navView = mBinding.navView;
        toolbar = mBinding.toolbar;
        vpContent = mBinding.vpContent;
        ivTitleOne = mBinding.ivTitleOne;
        ivTitleTwo = mBinding.ivTitleTwo;
        ivTitleThree = mBinding.ivTitleThree;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                setCurrentItem(0);
                break;
            case 1:
                setCurrentItem(1);
                break;
            case 2:
                setCurrentItem(2);
                break;
            default:
                break;
        }

    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_info) {
            // Handle the camera action
        } else if (id == R.id.nav_quest) {

        } else if (id == R.id.nav_mode) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_me) {

        } else if (id == R.id.nav_login) {

        }else if (id == R.id.nav_collect) {

        }else if (id == R.id.nav_exit) {
            // 退出应用
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }
}
