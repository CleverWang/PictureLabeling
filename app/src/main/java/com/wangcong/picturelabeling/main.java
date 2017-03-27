package com.wangcong.picturelabeling;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

public class main extends AppCompatActivity {
    public int all_id = 1, sys_id = 2, his_id = 3, user_id = 4;
    public int now_id = 0;
    public ArrayList<oneFrgment> allFragments = new ArrayList<>();
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
                            allFragments.add(new oneFrgment(fragment1, all_id));
                        }
                        changeFragment(fragment1);
                        now_id = all_id;
                    }
                    return true;
                case R.id.system_push:
                    if (now_id != sys_id) {
                        SystemPushFragment fragment2 = (SystemPushFragment) contain(sys_id);
                        if (fragment2 == null) {
                            fragment2 = new SystemPushFragment();
                            allFragments.add(new oneFrgment(fragment2, sys_id));
                        }
                        changeFragment(fragment2);
                        now_id = sys_id;
                    }
                    return true;
                case R.id.history_picture:
                    if (now_id != his_id) {
                        LabelHistoryFragment fragment3 = (LabelHistoryFragment) contain(his_id);
                        if (fragment3 == null) {
                            fragment3 = new LabelHistoryFragment();
                            allFragments.add(new oneFrgment(fragment3, his_id));
                        }
                        changeFragment(fragment3);
                        now_id = his_id;
                    }
                    return true;
                case R.id.user_center:
                    if (now_id != user_id) {
                        UserCenterFragment fragment4 = (UserCenterFragment) contain(user_id);
                        if (fragment4 == null) {
                            fragment4 = new UserCenterFragment();
                            allFragments.add(new oneFrgment(fragment4, user_id));
                        }
                        changeFragment(fragment4);
                        now_id = user_id;
                    }
                    return true;
            }
            return false;
        }

    };

    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    public Fragment contain(int id) {
        for (oneFrgment item : allFragments) {
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

        AllPictureFragment fragment=new AllPictureFragment();
        allFragments.add(new oneFrgment(fragment, all_id));
        changeFragment(fragment);
        now_id=all_id;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}

class oneFrgment {
    Fragment fragment;
    int fragmentId;

    public oneFrgment(Fragment f, int id) {
        fragment = f;
        fragmentId = id;
        //Log.d("Create fragment", "oneFrgment: once");
    }
}
