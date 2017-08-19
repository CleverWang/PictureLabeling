package com.wangcong.picturelabeling.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.wangcong.picturelabeling.R;
import com.wangcong.picturelabeling.Utils.GlobalFlags;
import com.wangcong.picturelabeling.Utils.HttpCallbackListener;
import com.wangcong.picturelabeling.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText text_username, text_pwd;//用户名、密码输入框
    private String username, pwd;//保存用户名、密码
    private CheckBox rem_pw, auto_login;//记住密码、自动登录选择框
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        //Log.d("Login", "onCreate: called");

        text_username = (EditText) findViewById(R.id.edit_login_account);
        text_pwd = (EditText) findViewById(R.id.edit_login_password);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        rem_pw = (CheckBox) findViewById(R.id.memory);
        auto_login = (CheckBox) findViewById(R.id.autologin);

        //判断记住密码选择框状态
        if (sp.getBoolean("ISCHECK", false)) {
            //如为选择态，填入保存的用户信息
            rem_pw.setChecked(true);
            text_username.setText(sp.getString("USER_NAME", ""));
            text_pwd.setText(sp.getString("PASSWORD", ""));
            text_username.setSelection(text_username.getText().length());//光标移到文字最后

            //判断自动登陆选择框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                ////如为选择态，联网判断用户名和密码
                auto_login.setChecked(true);
                GlobalFlags.setUserID(sp.getString("USER_NAME", ""));//设置全局变量“用户名”
                LoginValidation(sp.getString("USER_NAME", ""), sp.getString("PASSWORD", ""));

                /*********调试代码
                 GlobalFlags.setUserID(sp.getString("USER_NAME", ""));
                 GlobalFlags.setIsLoggedIn(true);
                 Intent intent = new Intent(Login.this, Main.class);
                 startActivity(intent);*/
                //Toast.makeText(Login.this, "请重新输入密码！", Toast.LENGTH_SHORT).show();
            }
        }

        Button button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = text_username.getText().toString().trim();
                pwd = text_pwd.getText().toString().trim();
                GlobalFlags.setUserID(username);
                if (username.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(Login.this, "密码或账号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //*********调试代码
                    /*Toast.makeText(Login.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    if (rem_pw.isChecked()) {
                        //记住用户名、密码
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", username);
                        editor.putString("PASSWORD", pwd);
                        editor.commit();
                    }
                    GlobalFlags.setIsLoggedIn(true);
                    Intent intent = new Intent(Login.this, Main.class);
                    startActivity(intent);*/
                    LoginValidation(username, pwd);
                }
            }
        });

        //转跳注册界面
        Button button1 = (Button) findViewById(R.id.button_register);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivityForResult(intent, 1);
            }
        });


        //监听记住密码选择框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {
                    sp.edit().putBoolean("ISCHECK", true).commit();
                } else {
                    auto_login.setChecked(false);//记住密码未选择，自动登录不能选
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }
            }
        });

        //监听自动登录选择框事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {
                    if (auto_login.isChecked()) {
                        sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
                    } else {
                        sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                    }
                } else {
                    //记住密码未选择，自动登录不能选
                    auto_login.setChecked(false);
                }
            }
        });
    }

    /**
     * 用户名和密码验证
     *
     * @param user_name 用户名
     * @param pwd       密码
     */
    private void LoginValidation(final String user_name, final String pwd) {
        String address = GlobalFlags.getIpAddress() + "login.jsp";
        String params = "pptelephone=" + user_name + "&ppassword=" + pwd;
        HttpUtil.sendHttpRequest(address, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("Login", "message: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("pptelephone");
                            if (result.equals(user_name)) {
                                //此处处理从server返回的信息，如果登录成功，则执行下面代码，否则错误信息
                                Toast.makeText(Login.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                if (rem_pw.isChecked()) {
                                    //记住用户名、密码
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("USER_NAME", user_name);
                                    editor.putString("PASSWORD", pwd);
                                    editor.commit();
                                }
                                GlobalFlags.setIsLoggedIn(true);//设置全局变量“已经登录”
                                Intent intent = new Intent(Login.this, Main.class);
                                startActivity(intent);
                            } else if (result.equals("-1")) {
                                Toast.makeText(Login.this, "登录失败！", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Login.this, "发生错误，请重试！", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Login.this, "发生错误，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //从注册界面返回用户名和密码
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    //获取新注册的用户名和密码，填入登录输入框
                    String name = intent.getStringExtra("username");
                    String password = intent.getStringExtra("password");
                    //Log.d("Login", "onActivityResult: called" + name + " " + password);
                    if (name != null && password != null && name.length() != 0 && password.length() != 0) {
                        rem_pw.setChecked(false);
                        auto_login.setChecked(false);
                        text_username.setText(name);
                        text_pwd.setText(password);
                        text_username.setSelection(text_username.getText().length());//光标移到文字最后
                    }
                }
                break;
            default:
        }
    }

    //自动登录后，在主界面按返回直接退出
    @Override
    public void onRestart() {
        super.onRestart();
        //Log.d("Login", "onRestart: called");
        if (GlobalFlags.isLoggedIn()) {
            this.finish();
        }
    }
}
