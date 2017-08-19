package com.wangcong.picturelabeling.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wangcong.picturelabeling.R;
import com.wangcong.picturelabeling.Utils.GlobalFlags;

import java.util.ArrayList;

/**
 * Created by 13307 on 2017/5/9.
 */

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Integer> icons;//存储头像ID

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.every_icon);
        }
    }

    public IconAdapter(Context context, ArrayList<Integer> allicon) {
        this.context = context;
        icons = allicon;
    }

    @Override
    public IconAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.everyicon, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择好头像后，记录头像ID
                int position = holder.getAdapterPosition();
                if (GlobalFlags.getIconIndex() != position) {
                    GlobalFlags.setIconIndex(position);
                    GlobalFlags.setIsIconChanged(true);
                }
                if (Activity.class.isInstance(context))
                    ((Activity) context).finish();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final IconAdapter.ViewHolder holder, int position) {
        //载入每一个头像
        int iconPath = icons.get(position);
        Glide.with(context).load(iconPath).placeholder(R.drawable.loading).error(R.drawable.failed).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }
}
