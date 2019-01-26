package com.rainstorm.whoa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.jrummyapps.android.widget.AnimatedSvgView;
import com.rainstorm.whoa.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liys on 2019-01-25.
 */

public class LauncherActivity extends Activity{
    private AnimatedSvgView svgView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ImmersionBar.with(this).statusBarColor(R.color.whoa).fitsSystemWindows(true).init();

        initSVG();
        jumpToMain();
    }
    
    private void initSVG() {
        svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        svgView.postDelayed(new Runnable() {

            @Override public void run() {
                svgView.start();
            }
        }, 300);
    }

    private void jumpToMain() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, 3000);
    }
    
}
