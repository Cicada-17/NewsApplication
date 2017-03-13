package com.liuzhao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.liuzhao.Bean.CategoryBean;

import java.util.List;

/**
 * Created by Administrator on 2月18日0018.
 */

public class FixedDouyuAdapter extends FragmentStatePagerAdapter {
    private List<CategoryBean> categoryBeen;
    public void setCategoryBeen(List<CategoryBean> categoryBeen){
        this.categoryBeen = categoryBeen;
    }
    private String[] titles;

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    private List<Fragment> fragments;
    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public FixedDouyuAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment=null;
        try{
            fragment=(Fragment)super.instantiateItem(container,position);
        }catch (Exception e){

        }
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return  categoryBeen.get(position%categoryBeen.size()).getTitle();
    }
}
