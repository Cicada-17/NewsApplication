package com.liuzhao.activity;

import android.content.Intent;
import android.os.Bundle;

import com.liuzhao.Bean.RoomBean;
import com.liuzhao.R;
import com.liuzhao.biz.RoomManager;
import com.liuzhao.common.Config;
import com.liuzhao.common.DefineView;
import com.liuzhao.utils.OkhttpManager;
import com.squareup.okhttp.Request;

import java.io.IOException;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Administrator on 2月18日0018.
 */

public class VitamioActivity extends  BaseActivity implements DefineView{
    private static final String TAG = "VitamioActivity";
    private String path;
    private  String room_id;
    //private HashMap<String, String> options;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.vitamio_test_layout);

        path = "http://hls3a.douyucdn.cn/live/810226rUlhPHzrCL_550/playlist.m3u8?wsSecret=93cb988f873083bbdb691d82ed063c0d&wsTime=1487410014";
            /*options = new HashMap<>();
            options.put("rtmp_playpath", "");
            options.put("rtmp_swfurl", "");
            options.put("rtmp_live", "1");
            options.put("rtmp_pageurl", "");*/
        initView();
        initValidata();
        initListener();



    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        room_id = intent.getStringExtra("room_id");
        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);

    }

    @Override
    public void initValidata() {
         String url = Config.DOUYUROOM+room_id;
        OkhttpManager.getAsync(url, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) {
                RoomBean roomBean = RoomManager.getRoom(result);
                path =roomBean.getHls_url();
                bindData();

            }
        });
        //mVideoView.setVideoURI(Uri.parse(path), options);


    }

    @Override
    public void initListener() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });

    }

    @Override
    public void bindData() {
        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

    }
}

