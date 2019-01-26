package com.rainstorm.whoa.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.rainstorm.whoa.R;
import com.rainstorm.whoa.adapter.ImageListAdapter;
import com.rainstorm.whoa.base.App;
import com.rainstorm.whoa.base.Constant;
import com.rainstorm.whoa.bean.RssBean;
import com.rainstorm.whoa.widget.LoadingUi;
import com.rainstorm.whoa.utlis.RssParser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by liys on 2019-01-25.
 */
public class MainActivity extends AppCompatActivity {
    private static final int RSS_PARSED = 1;
    
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ImageListAdapter adapter;
    private String rssStr;
    private ArrayList<RssBean> allRssData = new ArrayList<>();
    private ArrayList<RssBean> usedRssData = new ArrayList<>();
    private int page = 1;
    private boolean isScrollToBottom = false;

    private ThreadPoolExecutor executor = App.getThreadPool();
    private ExecHandler handler = new ExecHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).statusBarColor(R.color.whoa).fitsSystemWindows(true).supportActionBar(true).init();

        initUI();
        getRssData();
    }
    
    private void initUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isScrollToBottom) {
                    page++;
                    addDataToRecycleView(page);
                }
                staggeredGridLayoutManager.invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isScrollToBottom = true;
                } else {
                    isScrollToBottom = false;
                }
            }
        });
        adapter = new ImageListAdapter(this);
        recyclerView.setAdapter(adapter);
        LoadingUi.getInstance().setDialog(this).show();
    }
    
    private void getRssData() {
        OkGo.<String>get(Constant.BASE_URL).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                rssStr = response.body();

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<RssBean> data = RssParser.parse(rssStr);
                        Message message = Message.obtain(handler, RSS_PARSED, data);
                        handler.sendMessage(message);
                    }
                });
            }

            @Override
            public void onError(Response<String> response) {
                String test = response.body();
            }
        });
    }

    private static class ExecHandler extends Handler {
        private WeakReference<MainActivity> reference;

        private ExecHandler(MainActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (reference == null || reference.get() == null) return;
            final MainActivity activity = reference.get();
            if (msg.what == RSS_PARSED) {
                ArrayList<RssBean> data = (ArrayList) msg.obj;
                if (null != data && data.size() > 0) {
                    activity.allRssData = data;
                    LoadingUi.getInstance().getDialog().hide();
                    activity.addDataToRecycleView(activity.page);
                }
            } 
        }
    }
    
    private void addDataToRecycleView(int page) {
        if (10 * page < allRssData.size()) {
            usedRssData.addAll(allRssData.subList(0 + 10 * (page - 1), 10 * page));
            adapter.setData(usedRssData);
        }
    }
}
