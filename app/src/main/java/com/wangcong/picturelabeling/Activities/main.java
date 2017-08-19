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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wangcong.picturelabeling.Beans.OneFragment;
import com.wangcong.picturelabeling.Fragments.AllPictureFragment;
import com.wangcong.picturelabeling.Fragments.LabelHistoryFragment;
import com.wangcong.picturelabeling.Fragments.SystemPushFragment;
import com.wangcong.picturelabeling.R;
import com.wangcong.picturelabeling.Utils.GlobalFlags;
import com.wangcong.picturelabeling.Utils.HttpCallbackListener;
import com.wangcong.picturelabeling.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wangcong.picturelabeling.R.drawable.staroff;

public class Main extends AppCompatActivity {
    public final int all_id = 1, sys_id = 2, his_id = 3;//标识所有图片、今日推送、历史记录fragment的ID
    public int now_id = 0;//当前fragment的ID
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private ImageView userIcon;//用户头像
    public ArrayList<OneFragment> allFragments = new ArrayList<>();//保存3个fragment
    private TextView score;//积分
    private LinearLayout rating;//任务进度
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                //当前点击的是所有图片fragment
                case R.id.all_picture:
                    //如果显示的不是当前fragment，切换fragment
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
                //当前点击的是今日推送fragment
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
                //当前点击的是历史记录fragment
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
            }
            return false;
        }

    };

    /**
     * 切换fragment
     *
     * @param fragment 要切换到的fragment
     * @param toId     要切换到的fragment的标识id
     */
    public void changeFragment(Fragment fragment, int toId) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //设置切换动画
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
        //Log.d("Main", "changeFragment: " + transaction.commit());
        transaction.commit();
    }

    /**
     * 根据id判断当前的fragment是否在所有fragment链表中，在则返回该id对应的fragment引用
     *
     * @param id 要判断的fragment的标识id
     * @return 存在则返回fragment的引用，否则返回null
     */
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //初始的fragment为所有图片fragment
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

        //获取滑动菜单栏上的headerView
        View headerView = navView.getHeaderView(0);
        TextView user = (TextView) headerView.findViewById(R.id.text_user_name_nav);
        user.setText(GlobalFlags.getUserID());//设置用户名
        score = (TextView) headerView.findViewById(R.id.text_scores_nav);
        rating = (LinearLayout) headerView.findViewById(R.id.linearlayout_rating);
        userIcon = (ImageView) headerView.findViewById(R.id.user_icon);
        setHeadView();//设置头像、积分、任务进度

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    //转跳主界面
                    case R.id.nav_home:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    //转跳修改用户信息界面
                    case R.id.nav_change:
                        Intent intent = new Intent(Main.this, UserInfoChange.class);
                        startActivity(intent);
                        break;
                    //退出登录
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

    /**
     * 获取个人信息，并在HeaderLayout里面设置
     */
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
                        //Log.d("Main", "personal message: " + response);
                        String renwu = "";
                        int icon = -1;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int scor = jsonObject.getInt("pnum");
                            score.setText("积分：" + scor + "分");
                            renwu = jsonObject.getString("prenwu");
                            ratingStars(Integer.parseInt(renwu));
                            icon = jsonObject.getInt("icon");
                            Glide.with(getApplication()).load(GlobalFlags.UserIcons[icon]).into(userIcon);
                            if (icon != -1)
                                GlobalFlags.setIconIndex(icon);
                            //Log.d("Main", "icon num: " + GlobalFlags.getIconIndex());
                        } catch (JSONException e) {
                            //Toast.makeText(Main.this, "错误：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            if (renwu.length() == 0) {
                                //ratingBar.setRating(0);
                                ratingStars(0);
                            }
                            if (icon == -1)
                                Glide.with(getApplication()).load(GlobalFlags.UserIcons[0]).into(userIcon);
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
                        Toast.makeText(Main.this, "个人信息加载失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 设置用户当前任务进度，总共5颗星
     *
     * @param num 任务进度（实星星个数）
     */
    private void ratingStars(int num) {
        rating.removeAllViews();
        //添加实星星
        for (int i = 0; i < num; i++) {
            ImageView star_on = new ImageView(this);
            star_on.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            star_on.setImageResource(R.drawable.staron);
            rating.addView(star_on);
        }
        //添加剩余空星星
        for (int i = 0; i < 5 - num; i++) {
            ImageView star_off = new ImageView(this);
            star_off.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            star_off.setImageResource(staroff);
            rating.addView(star_off);
        }
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
        //刷新HeadView
        setHeadView();
        //判断是否要刷新fragment
        if (GlobalFlags.isNeedtoRefresh()) {
            Fragment tfrag = contain(all_id);
            if (tfrag != null) {
                if (!((AllPictureFragment) tfrag).isSearched())//刷新所有图片fragment
                    ((AllPictureFragment) tfrag).getAllPicPaths();
                else
                    ((AllPictureFragment) tfrag).searchPics();//是搜索后再打标签返回的，刷新搜索界面
            }
            tfrag = contain(sys_id);
            if (tfrag != null) {
                ((SystemPushFragment) tfrag).getAllPicPaths();
            }
            tfrag = contain(his_id);
            if (tfrag != null) {
                ((LabelHistoryFragment) tfrag).getAllPicPaths();
            }
            GlobalFlags.setIsNeedtoRefresh(false);
        }
        //判断是否要重新加载用户头像
        /*if (GlobalFlags.isIconChanged()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getApplicationContext()).load(GlobalFlags.UserIcons[GlobalFlags.getIconIndex()]).into(userIcon);
                }
            });
            Glide.with(getApplicationContext()).load(GlobalFlags.UserIcons[GlobalFlags.getIconIndex()]).into(userIcon);
            GlobalFlags.setIsIconChanged(false);
        }*/
        super.onRestart();
    }
}
