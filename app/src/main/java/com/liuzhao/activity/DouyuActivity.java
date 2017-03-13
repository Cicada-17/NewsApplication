package com.liuzhao.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.liuzhao.R;
import com.liuzhao.common.DefineView;

/**
 * Created by Administrator on 2月18日0018.
 */

public class DouyuActivity extends BaseActivity implements DefineView, View.OnClickListener {
    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.douyu_layout);
        setStatusBar();
        initView();
        initListener();
        initValidata();
        bindData();
    }

    @Override
    public void initView() {
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

    }

    @Override
    public void bindData() {

    }

    @Override
    public void onClick(View view) {

    }
}
