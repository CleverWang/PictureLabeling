package com.wangcong.picturelabeling.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wangcong.picturelabeling.Activities.LabelPicture;
import com.wangcong.picturelabeling.Activities.ViewJudgedPic;
import com.wangcong.picturelabeling.Beans.OnePicHistory;
import com.wangcong.picturelabeling.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 13307 on 2017/4/12.
 */

public class NewImageAdapterHistory extends RecyclerView.Adapter<NewImageAdapterHistory.ViewHolder> {
    private Context context;
    private ArrayList<OnePicHistory> allHisPics;//保存所有历史图片信息

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView EveryView;
        ImageView Image;
        TextView label;

        public ViewHolder(View view) {
            super(view);
            EveryView = (CardView) view;
            Image = (ImageView) view.findViewById(R.id.image_cardview);
            label = (TextView) view.findViewById(R.id.text_cardview);
        }
    }

    public NewImageAdapterHistory(Context context, ArrayList<OnePicHistory> allPics) {
        this.context = context;
        allHisPics = allPics;
    }

    @Override
    public NewImageAdapterHistory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pic_card_view, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.EveryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击图片打标签
                int position = holder.getAdapterPosition();
                OnePicHistory one = allHisPics.get(position);
                if (one.getIsClickable().equals("0")) {//未判定
                    Intent intent = new Intent(context, LabelPicture.class);
                    intent.putExtra("PicPath", one.getPath());
                    intent.putExtra("PicId", one.getId());
                    intent.putExtra("PicLabel", one.getLabel());
                    intent.putExtra("Recommends", one.getRecommends());
                    context.startActivity(intent);
                } else//已判定
                {
                    //Toast.makeText(context, "该图片标签已判定完毕！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ViewJudgedPic.class);
                    intent.putExtra("PicPath", one.getPath());
                    context.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NewImageAdapterHistory.ViewHolder holder, int position) {
        OnePicHistory one = allHisPics.get(position);
        String imagePath = one.getPath();
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        //找到中文url中中文
        Matcher ma = p.matcher(imagePath);
        while (ma.find()) {
            String group = ma.group();
            try {
                imagePath = imagePath.replaceFirst(group, URLEncoder.encode(group, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                //Log.d("error", "onBindViewHolder: " + e.getMessage());
            }
        }
        Glide.with(context).load(imagePath).placeholder(R.drawable.loading).error(R.drawable.failed).into(holder.Image);
        holder.label.setText(one.getLabel());
    }

    @Override
    public int getItemCount() {
        return allHisPics.size();
    }
}
