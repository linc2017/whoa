package com.rainstorm.whoa.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rainstorm.whoa.R;
import com.rainstorm.whoa.activity.ViewPagerActivity;
import com.rainstorm.whoa.bean.RssBean;
import com.rainstorm.whoa.utlis.DisplayUtils;
import com.rainstorm.whoa.GlideApp;
import com.rainstorm.whoa.widget.GlideRoundTransform;

import java.util.ArrayList;

/**
 * Created by liys on 2019-01-25.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.LinearViewHolder>{

    private Context mContext;
    private ArrayList<RssBean> usedRssData =new ArrayList<>();
    private int oldSize = 0;

    public ImageListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ImageListAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycleview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final ImageListAdapter.LinearViewHolder holder, int position) {
        RssBean rssBean = usedRssData.get(position);
        final ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        layoutParams.width = DisplayUtils.getScreenWidth((Activity) mContext) / 2 - DisplayUtils.dp2px(mContext,2);
        if(rssBean.scale != 0){
            layoutParams.height = (int) (layoutParams.width / rssBean.scale);
        }
        
        GlideApp.with(mContext).load(usedRssData.get(position).imageLink).placeholder(R.color.mistyrose).transform(new GlideRoundTransform(mContext, 2)).into(holder.imageView);
    }
    
    public void setData(ArrayList<RssBean> list) {
        if (null != list) {
            oldSize = this.usedRssData.size();
            this.usedRssData = list;
            if (oldSize < list.size()) {
                notifyItemInserted(oldSize - 1);
            }
            // TODO: If we use waterfall,download images with scale would be better.
            // But if app need to calculate scale,we may pre-download a page,for example,if user on page 2 now,we will download page 3 to calculate,though maybe user don't want to watch page 3.
            // If app need to calculate scale,maybe means no really good way to implement waterfall.
            setImageScale();
        }
    }
    
    private void setImageScale() {
        for (final RssBean rssBean : usedRssData) {
            if(rssBean.scale == 0){
                GlideApp.with(mContext).load(rssBean.imageLink).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        float scale = resource.getIntrinsicWidth() / (float) resource.getIntrinsicHeight();
                        rssBean.scale = scale;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return usedRssData.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        public LinearViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            imageView=(ImageView) itemView.findViewById(R.id.rss_image);
        }
        
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ViewPagerActivity.class);
            intent.putExtra("current_position", getAdapterPosition());
            intent.putExtra("image_list", usedRssData);
            mContext.startActivity(intent);
        }
    }
}

