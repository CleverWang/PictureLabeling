package com.wangcong.picturelabeling.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.wangcong.picturelabeling.R;

public class ViewJudgedPic extends AppCompatActivity {
    private Toolbar toolbar;
    private PhotoView imageView;//待查看的图片
    private int clickCount = 0;//图片点击次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_judged_pic);

        toolbar = (Toolbar) findViewById(R.id.toolbar20);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //获取传过来的图片路径
        Intent intent = getIntent();
        String PicPath = intent.getStringExtra("PicPath");

        imageView = (PhotoView) findViewById(R.id.photoview_judged_pic);
        Glide.with(this).load(PicPath).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单击隐藏ToolBar
                if (clickCount == 0) {
                    clickCount++;
                    toolbar.setVisibility(View.GONE);
                } else {
                    //最后单击恢复隐藏的控件
                    toolbar.setVisibility(View.VISIBLE);
                    clickCount = 0;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
