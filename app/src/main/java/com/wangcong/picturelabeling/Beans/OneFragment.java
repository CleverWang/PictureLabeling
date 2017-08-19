package com.wangcong.picturelabeling.Beans;

import android.app.Fragment;

/**
 * Created by 13307 on 2017/5/3.
 */

public class OneFragment {

    private Fragment fragment;//当前fragment
    private int fragmentId;//当前fragment的ID

    public OneFragment(Fragment f, int id) {
        fragment = f;
        fragmentId = id;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }


}
