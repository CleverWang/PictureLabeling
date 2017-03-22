package com.wangcong.picturelabeling;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wangcong.picturelabeling.Util.GlobalFlags;

/**
 * Created by 13307 on 2017/3/20.
 */

public class UserCenterFragment extends Fragment {
    Button changeUserInfo;
    Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_center_fragment, container, false);
        //显示用户名称
        TextView textView = (TextView) view.findViewById(R.id.text_user_name);
        textView.setText(GlobalFlags.getUserID());
        //修改用户信息
        changeUserInfo = (Button) view.findViewById(R.id.button_change_user_info);
        changeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserInfoChange.class);
                startActivity(intent);
            }
        });
        //退出登录
        logoutButton = (Button) view.findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), login.class);
                GlobalFlags.setIsLogout(true);
                GlobalFlags.setExitAll(false);
                startActivity(intent);
            }
        });
        return view;
    }
}
