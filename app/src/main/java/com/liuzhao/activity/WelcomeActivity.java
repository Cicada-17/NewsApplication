package com.liuzhao.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liuzhao.R;
import com.liuzhao.widget.CustomVideoView;

/**
 * Created by Administrator on 2月05日0005.
 */

public class WelcomeActivity extends BaseActivity{
    private Button welcome_button;
    private CustomVideoView welcome_videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        welcome_button = (Button) this.findViewById(R.id.welcome_button);
        welcome_videoview = (CustomVideoView) this.findViewById(R.id.welcome_videoview);
        welcome_videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.welcome));
        welcome_videoview.start();
        welcome_videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){


            @Override
            public void onCompletion(MediaPlayer mp) {
                welcome_videoview.start();
            }
        });
        welcome_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (welcome_videoview.isPlaying()){
                    welcome_videoview.stopPlayback();
                    welcome_videoview = null;
                }
                openActivity(MainActivity.class);
                WelcomeActivity.this.finish();
            }
        });
    }

}
