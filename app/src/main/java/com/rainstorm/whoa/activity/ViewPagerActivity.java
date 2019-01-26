package com.rainstorm.whoa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.gyf.barlibrary.ImmersionBar;
import com.rainstorm.whoa.R;
import com.rainstorm.whoa.bean.RssBean;
import com.rainstorm.whoa.utlis.GlideApp;
import com.rainstorm.whoa.widget.ViewPagerScroller;

import java.util.ArrayList;

/**
 * Created by liys on 2019-01-25.
 */
public class ViewPagerActivity extends Activity {

    private ViewPager mPager;
    private ArrayList<RssBean> usedRssData =new ArrayList<>();
    private int curPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ImmersionBar.with(this).statusBarColor(R.color.whoa).fitsSystemWindows(true).init();

        initIntent();
        initViewPager();
    }
    
    private void initIntent() {
        Intent intent = getIntent();
        usedRssData = (ArrayList<RssBean>) intent.getSerializableExtra("image_list");
        curPosition = intent.getIntExtra("current_position", 0);
    }
    
    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return usedRssData.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(ViewPagerActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                GlideApp.with(ViewPagerActivity.this).load(usedRssData.get(position).imageLink).into(view);
                container.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewPagerActivity.this.finish();
                    }
                });
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        ViewPagerScroller pagerScroller = new ViewPagerScroller(this);
        pagerScroller.initViewPagerScroll(mPager);
        mPager.setCurrentItem(curPosition, false);
    }
}
