package com.liuzhao.fragment.BottomFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuzhao.R;
import com.liuzhao.fragment.base.BaseFragment;

/**
 * Created by Administrator on 2月14日0014.
 */

public class FragmentOne extends BaseFragment{
    public static FragmentOne newInstance() {
        FragmentOne fragment = new FragmentOne();

        return fragment;
    }
    public FragmentOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fiagment_one,container,false);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
