package com.wangcong.picturelabeling;

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
import android.widget.TextView;

import com.wangcong.picturelabeling.Util.GlobalFlags;

import java.util.ArrayList;

public class main extends AppCompatActivity {
    public int all_id = 1, sys_id = 2, his_id = 3, user_id = 4;
    public int now_id = 0;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    public ArrayList<oneFragment> allFragments = new ArrayList<>();
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
                            allFragments.add(new oneFragment(fragment1, all_id));
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
                            allFragments.add(new oneFragment(fragment2, sys_id));
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
                            allFragments.add(new oneFragment(fragment3, his_id));
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
                            allFragments.add(new oneFragment(fragment4, user_id));
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
            transaction.hide(nowFragment).add(R.id.content, fragment);
        } else {
            transaction.hide(nowFragment).show(fragment);
        }
        //transaction.replace(R.id.content, fragment);
        //transaction.addToBackStack(null);
        //Log.d("main", "changeFragment: " + transaction.commit());
        transaction.commit();
    }

    public Fragment contain(int id) {
        for (oneFragment item : allFragments) {
            if (item.fragmentId == id) {
                return item.fragment;
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
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        AllPictureFragment fragment = new AllPictureFragment();
        allFragments.add(new oneFragment(fragment, all_id));
        getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        now_id = all_id;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        //获取headerView
        View headerView = navView.getHeaderView(0);
        TextView user = (TextView) headerView.findViewById(R.id.text_user_name_nav);
        user.setText(GlobalFlags.getUserID());

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_change:
                        Intent intent = new Intent(main.this, UserInfoChange.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_exit:
                        Intent intent1 = new Intent(main.this, login.class);
                        GlobalFlags.setIsLoggedIn(false);
                        startActivity(intent1);
                        break;
                    default:
                }
                return true;
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
        navView.setCheckedItem(R.id.nav_home);
        super.onRestart();
    }
}


class oneFragment {
    Fragment fragment;
    int fragmentId;

    public oneFragment(Fragment f, int id) {
        fragment = f;
        fragmentId = id;
        //Log.d("Create fragment", "oneFragment: once");
    }
}
