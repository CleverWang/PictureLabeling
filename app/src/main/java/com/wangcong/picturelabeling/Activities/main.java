package com.wangcong.picturelabeling.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcong.picturelabeling.Beans.OneFragment;
import com.wangcong.picturelabeling.Fragments.AllPictureFragment;
import com.wangcong.picturelabeling.Fragments.LabelHistoryFragment;
import com.wangcong.picturelabeling.Fragments.SystemPushFragment;
import com.wangcong.picturelabeling.R;
import com.wangcong.picturelabeling.Utils.ActivityCollector;
import com.wangcong.picturelabeling.Utils.GlobalFlags;
import com.wangcong.picturelabeling.Utils.HttpCallbackListener;
import com.wangcong.picturelabeling.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    public int all_id = 1, sys_id = 2, his_id = 3;
    public int now_id = 0;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    public ArrayList<OneFragment> allFragments = new ArrayList<>();
    private TextView score;
    private RatingBar ratingBar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.all_picture:
                    if (now_id != all_id) {
                        AllPictureFragment fragment1 = (AllPictureFragment) contain(all_id);
                        if (fragment1 == null) {
                            fragment1 = new AllPictureFragment();
                            allFragments.add(new OneFragment(fragment1, all_id));
                        }
                        changeFragment(fragment1, all_id);
                        now_id = all_id;
                    }
                    return true;
                case R.id.system_push:
                    if (now_id != sys_id) {
                        SystemPushFragment fragment2 = (SystemPushFragment) contain(sys_id);
                        if (fragment2 == null) {
                            fragment2 = new SystemPushFragment();
                            allFragments.add(new OneFragment(fragment2, sys_id));
                        }
                        changeFragment(fragment2, sys_id);
                        now_id = sys_id;
                    }
                    return true;
                case R.id.history_picture:
                    if (now_id != his_id) {
                        LabelHistoryFragment fragment3 = (LabelHistoryFragment) contain(his_id);
                        if (fragment3 == null) {
                            fragment3 = new LabelHistoryFragment();
                            allFragments.add(new OneFragment(fragment3, his_id));
                        }
                        changeFragment(fragment3, his_id);
                        now_id = his_id;
                    }
                    return true;
                /*case R.id.user_center:
                    if (now_id != user_id) {
                        UserCenterFragment fragment4 = (UserCenterFragment) contain(user_id);
                        if (fragment4 == null) {
                            fragment4 = new UserCenterFragment();
                            allFragments.add(new OneFragment(fragment4, user_id));
                        }
                        changeFragment(fragment4, user_id);
                        now_id = user_id;
                    }
                    return true;*/
            }
            return false;
        }

    };

    public void changeFragment(Fragment fragment, int toId) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (now_id < toId)
            transaction.setCustomAnimations(R.animator.fragment_slide_left_enter, R.animator.fragment_slide_left_exit);
        else
            transaction.setCustomAnimations(R.animator.fragment_slide_right_enter, R.animator.fragment_slide_right_exit);
        Fragment nowFragment = contain(now_id);
        if (!fragment.isAdded()) {
            //要显示的fragment没有在activity中，则隐藏当前fragment，并添加要显示的fragment到activity中并显示
            transaction.hide(nowFragment).add(R.id.content, fragment);
        } else {
            //要显示的fragment已在activity中，则隐藏当前fragment，并显示要显示的fragment
            transaction.hide(nowFragment).show(fragment);
        }
        //transaction.replace(R.id.content, fragment);
        //transaction.addToBackStack(null);
        //Log.d("Main", "changeFragment: " + transaction.commit());
        transaction.commit();
    }

    //根据id判断当前的fragment是否在所有fragment链表中，在则返回该id对应的fragment引用
    public Fragment contain(int id) {
        for (OneFragment item : allFragments) {
            if (item.getFragmentId() == id) {
                return item.getFragment();
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.activities.add(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //初始的fragment为“所有图片”fragment
        AllPictureFragment fragment = new AllPictureFragment();
        allFragments.add(new OneFragment(fragment, all_id));
        getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        now_id = all_id;
        //底部导航栏
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //侧面滑动菜单栏
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        //获取headerView
        View headerView = navView.getHeaderView(0);
        TextView user = (TextView) headerView.findViewById(R.id.text_user_name_nav);
        score = (TextView) headerView.findViewById(R.id.text_scores_nav);
        ratingBar = (RatingBar) headerView.findViewById(R.id.star_progress_nav);
        user.setText(GlobalFlags.getUserID());
        setHeadView();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_change:
                        Intent intent = new Intent(Main.this, UserInfoChange.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_exit:
                        Intent intent1 = new Intent(Main.this, Login.class);
                        GlobalFlags.setIsLoggedIn(false);
                        startActivity(intent1);
                        break;
                    default:
                }
                return true;
            }
        });
    }

    //获取个人信息，并在HeaderLayout里面设置
    private void setHeadView() {
        String address = GlobalFlags.getIpAddress() + "person.jsp";
        String params = "pptelephone=" + GlobalFlags.getUserID();
        //Log.d("changeinfo", "setUserInfo: " + address + " " + params);
        HttpUtil.sendHttpRequest(address, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("Main", "message: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int scor = jsonObject.getInt("pnum");
                            score.setText("积分：" + scor + "分");
                            String renwu = jsonObject.getString("prenwu");
                            ratingBar.setNumStars(Integer.parseInt(renwu));
                        } catch (JSONException e) {
                            Toast.makeText(Main.this, "错误：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onError(final Exception e) {
                //Log.d("Get from server", "error message: " + e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Main.this, "错误：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onRestart() {
        //在主界面时，菜单栏选中的是“主页”
        navView.setCheckedItem(R.id.nav_home);
        //判断是否要刷新
        if (GlobalFlags.isNeedtoRefresh()) {
            Fragment tfrag = contain(now_id);
            if (tfrag != null) {
                if (now_id == all_id)
                    ((AllPictureFragment) tfrag).getAllPicPaths();
                else if (now_id == sys_id)
                    ((SystemPushFragment) tfrag).getAllPicPaths();
            }
            tfrag = contain(his_id);
            if (tfrag != null) {
                ((LabelHistoryFragment) tfrag).getAllPicPaths();
            }
            GlobalFlags.setIsNeedtoRefresh(false);
        }
        super.onRestart();
    }
}