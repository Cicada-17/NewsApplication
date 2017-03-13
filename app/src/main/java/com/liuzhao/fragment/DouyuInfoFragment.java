package com.liuzhao.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuzhao.Bean.CategoryBean;
import com.liuzhao.R;
import com.liuzhao.adapter.FixedDouyuAdapter;
import com.liuzhao.common.DefineView;
import com.liuzhao.fragment.base.BaseFragment;
import com.liuzhao.utils.CategoryDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2月18日0018.
 */

public class DouyuInfoFragment extends BaseFragment implements DefineView,ViewPager.OnPageChangeListener {
    private View mView;
    private TabLayout tab_layout;
    private ViewPager info_viewpager;
    private FixedDouyuAdapter fixedDouyuAdapter;
    private List<Fragment> fragments;
    private List<CategoryBean> categoryBeen ;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView==null){
            mView = inflater.inflate(R.layout.main_info_fragment_layout,container,false);
            categoryBeen = CategoryDataUtils.getDouyuBeans();
            initView();
            initValidata();
            initListener();
            bindData();
        }
        return mView;
    }

    @Override
    public void initView() {
        tab_layout = (TabLayout) mView.findViewById(R.id.tab_layout);
        info_viewpager = (ViewPager) mView.findViewById(R.id.info_viewpager);

    }

    @Override
    public void initValidata() {
        Log.d("zhibo0",categoryBeen.size()+"");
        fixedDouyuAdapter =new FixedDouyuAdapter(getChildFragmentManager());
        fixedDouyuAdapter.setCategoryBeen(categoryBeen);
        fragments = new ArrayList<Fragment>();

        Log.d("zhibo1",categoryBeen.size()+"");
        for (int i=0;i<categoryBeen.size();i++){
                DouyuFragment baseFragment = null;

                baseFragment = DouyuFragment.newInstance(categoryBeen.get(i));
                Log.d("liuzhaomain",categoryBeen.get(i).getTitle());

            fragments.add(baseFragment);
        }

        fixedDouyuAdapter.setFragments(fragments);

        info_viewpager.setAdapter(fixedDouyuAdapter);
        /**
         * tablayout 与 viewpager绑定
         */
        tab_layout.setupWithViewPager(info_viewpager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);



    }

    @Override
    public void initListener() {
        info_viewpager.setOnPageChangeListener(this);
    }

    @Override
    public void bindData() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
