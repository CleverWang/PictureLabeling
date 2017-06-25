package com.wangcong.picturelabeling.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wangcong.picturelabeling.Activities.LabelPicture;
import com.wangcong.picturelabeling.Beans.OnePic;
import com.wangcong.picturelabeling.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 13307 on 2017/4/12.
 */

public class NewImageAdapter extends RecyclerView.Adapter<NewImageAdapter.ViewHolder> {
    private Context context;
    //private int ImageHeight;
    //private ArrayList<String> imagePaths;
    //private ArrayList<String> imageIds;
    private ArrayList<OnePic> allPics;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View EveryView;
        ImageView Image;

        public ViewHolder(View view) {
            super(view);
            EveryView = view;
            Image = (ImageView) view.findViewById(R.id.every_pic);
        }
    }

    public NewImageAdapter(Context context, ArrayList<OnePic> all) {
        this.context = context;
        allPics = all;
        //imagePaths = paths;
        //imageIds = ids;
        //this.ImageHeight = imageHeight;
    }

    @Override
    public NewImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.everypic, parent, false);
        //Log.d("new", "onCreateViewHolder: "+view.getWidth());
        //view.setLayoutParams(new RecyclerView.LayoutParams(view.getWidth(), view.getWidth()));
        final ViewHolder holder = new ViewHolder(view);
        holder.EveryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                /*Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mContext.startActivity(intent);*/
                Intent intent = new Intent(context, LabelPicture.class);
                intent.putExtra("PicPath", allPics.get(position).getPath());
                intent.putExtra("PicId", allPics.get(position).getId());
                intent.putExtra("Recommends", allPics.get(position).getRecommends());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewImageAdapter.ViewHolder holder, int position) {
        String imagePath = allPics.get(position).getPath();
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        //找到中文url中中文
        Matcher ma = p.matcher(imagePath);
        while (ma.find()) {
            String group = ma.group();
            try {
                imagePath = imagePath.replaceFirst(group, URLEncoder.encode(group, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                Log.d("error", "onBindViewHolder: " + e.getMessage());
            }
        }
        if (imagePath.contains("http")) {
            //String imagePath = GlobalFlags.getIpAddress() + "image/atm1.jpg";
            //Log.d("load", "onBindViewHolder: " + imagePath);
            Glide.with(context).load(imagePath).placeholder(R.drawable.loading).error(R.drawable.failed).into(holder.Image);
        } else
            Glide.with(context).load(Integer.parseInt(imagePath)).placeholder(R.drawable.loading).error(R.drawable.failed).into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return allPics.size();
    }
}
