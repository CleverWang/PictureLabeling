package com.wangcong.picturelabeling.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wangcong.picturelabeling.R;
import com.wangcong.picturelabeling.Utils.GlobalFlags;
import com.wangcong.picturelabeling.Utils.HttpCallbackListener;
import com.wangcong.picturelabeling.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 13307 on 2017/3/21.
 */

public class ChangePWD extends AppCompatActivity {
    private EditText old_pwd, new_pwd, new_confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.change_pwd);

        old_pwd = (EditText) findViewById(R.id.edit_old_pwd);
        new_pwd = (EditText) findViewById(R.id.edit_new_pwd);
        new_confirm = (EditText) findViewById(R.id.edit_new_pwd_confirm);
        Button button = (Button) findViewById(R.id.button_confirm_pwd_change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpwd = old_pwd.getText().toString();
                String newpwd = new_pwd.getText().toString();
                String newconfirm = new_confirm.getText().toString();
                if (oldpwd.length() == 0 || newconfirm.length() == 0 || newpwd.length() == 0) {
                    Toast.makeText(ChangePWD.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                } else if (!newpwd.equals(newconfirm)) {
                    Toast.makeText(ChangePWD.this, "新密码两次输入不匹配！", Toast.LENGTH_SHORT).show();
                } else {
                    changePwd(oldpwd, newpwd);
                }
            }
        });
    }

    /**
     * 修改密码
     *
     * @param oldpwd 旧密码
     * @param newpwd 新密码
     */
    private void changePwd(String oldpwd, String newpwd) {
        String address = GlobalFlags.getIpAddress() + "passwordchange.jsp";
        String params = "pptelephone=" + GlobalFlags.getUserID() + "&oldpassword=" + oldpwd + "&newpassword=" + newpwd;
        //Log.d("changeinfo", "setUserInfo: " + address + " " + params);
        HttpUtil.sendHttpRequest(address, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("changepwd", "message: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            if (result == 1) {
                                Toast.makeText(ChangePWD.this, "密码修改成功，请重新登录！", Toast.LENGTH_SHORT).show();
                                GlobalFlags.setIsLoggedIn(false);//设置全局变量“已经登录”为false
                                Intent intent = new Intent(ChangePWD.this, Login.class);
                                startActivity(intent);
                            } else if (result == -1)
                                Toast.makeText(ChangePWD.this, "密码修改失败！", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(ChangePWD.this, "发生错误，请重试！", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ChangePWD.this, "发生错误，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
