package com.wangcong.picturelabeling.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wangcong.picturelabeling.R;
import com.wangcong.picturelabeling.Utils.ActivityCollector;

import java.util.ArrayList;

/**
 * Created by 13307 on 2017/4/22.
 */

public class SelectInterests extends AppCompatActivity {
    private String[] data_level_1 = {"经济金融", "企业管理", "法规法律", "社会民生", "科学教育", "健康生活", "体育运动", "文化艺术", "电子数码", "电脑网络", "娱乐休闲"};
    private String[][] data_level_2 = {{"经济金融"}, {"企业管理"}, {"法规法律"}, {"社会民生"}};
    private String[] data_level_3 = {"经济金融", "企业管理", "法规法律", "社会民生", "科学教育", "健康生活", "体育运动", "文化艺术"};
    private ArrayList<String> data2 = new ArrayList<>(), data3 = new ArrayList<>();//分别表示加载到二级、三级的ListView中的数据
    private ArrayList<String> interests = new ArrayList<>();//用户选择的所有兴趣
    private ListView level_1, level_2, level_3;
    private ArrayAdapter<String> adapter_2, adapter_1, adapter_3;
    private View one, two;//one用于保存一级ListView中之前选择的项目的View,two用于保存二级ListView中之前选择的项目的View
    private TextView results;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_interests);
        ActivityCollector.activities.add(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_interests);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        results = (TextView) findViewById(R.id.interests_results);

        level_1 = (ListView) findViewById(R.id.first_level);
        adapter_1 = new ArrayAdapter<String>(SelectInterests.this, android.R.layout.simple_list_item_1, data_level_1);
        level_1.setAdapter(adapter_1);
        level_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //清空三级ListView数据，还原到初始界面
                data3.clear();
                adapter_3.notifyDataSetChanged();
                if (two != null) {
                    two.setBackgroundColor(Color.parseColor("#eeeeee"));
                    two = null;
                    level_3.setBackgroundColor(Color.parseColor("#dcdcdc"));
                }
                //清空并加载二级ListView数据
                data2.clear();
                for (int i = 0; i < data_level_2[position].length; i++) {
                    data2.add(data_level_2[position][i]);
                }
                adapter_2.notifyDataSetChanged();
                level_2.setBackgroundColor(Color.parseColor("#eeeeee"));
                //判断当前选择的项目是否是之前选择的，如果不是，设置为选中效果
                if (one != view) {
                    if (one != null)
                        one.setBackgroundColor(Color.parseColor("#dcdcdc"));
                    view.setBackgroundColor(Color.parseColor("#eeeeee"));
                    one = view;
                }
            }
        });

        level_2 = (ListView) findViewById(R.id.second_level);
        adapter_2 = new ArrayAdapter<String>(SelectInterests.this, android.R.layout.simple_list_item_1, data2);
        level_2.setAdapter(adapter_2);
        level_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                data3.clear();
                for (int i = 0; i < data_level_3.length; i++) {
                    data3.add(data_level_3[i]);
                }
                adapter_3.notifyDataSetChanged();
                if (two != view) {
                    if (two != null)
                        two.setBackgroundColor(Color.parseColor("#eeeeee"));
                    view.setBackgroundColor(Color.parseColor("#ffffff"));
                    two = view;
                }
                level_3.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        level_3 = (ListView) findViewById(R.id.third_level);
        adapter_3 = new ArrayAdapter<String>(SelectInterests.this, android.R.layout.simple_list_item_multiple_choice, data3);
        level_3.setAdapter(adapter_3);
        level_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = data3.get(position);
                int index = isContained(s);
                //在interests中未找到选择的项目，则加入interests并在结果TextView中显示
                if (index == -1) {
                    interests.add(s);
                    if (results.getText().toString().equals("可多选"))
                        results.setText(s);
                    else
                        results.setText(results.getText().toString() + "，" + s);
                } else {//在interests中找到选择的项目，则从interests中移除并在将结果TextView更新
                    interests.remove(index);
                    if (interests.size() == 0)
                        results.setText("可多选");
                    else {
                        String t = "";
                        for (String item : interests)
                            t = t + "，" + item;
                        results.setText(t.substring(1));
                    }
                }
            }
        });
    }

    //在interests中寻找s，找到返回下标，否则返回-1
    private int isContained(String s) {
        for (int i = 0; i < interests.size(); i++) {
            if (interests.get(i).equals(s))
                return i;
        }
        return -1;
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
