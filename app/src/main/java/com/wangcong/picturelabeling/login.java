package com.wangcong.picturelabeling;

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

/**
 * Created by Wang Cong on 2016/9/28.
 */

public class login extends AppCompatActivity {
    EditText text1, text2;
    String a, b;
    static boolean isPasswordChanged = false;
    static boolean isLogout = false;
    CheckBox rem_pw, auto_login;
    SharedPreferences sp;
    static String user = "";
    static boolean exitAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        ActivityCollector.activities.add(this);
        exitAll = false;

        text1 = (EditText) findViewById(R.id.login_account);
        text2 = (EditText) findViewById(R.id.login_password);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        rem_pw = (CheckBox) findViewById(R.id.memory);
        auto_login = (CheckBox) findViewById(R.id.autologin);

        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            text1.setText(sp.getString("USER_NAME", ""));
            text2.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                if (isPasswordChanged == false) {
                    auto_login.setChecked(true);
                    login.user = sp.getString("USER_NAME", "");
                    //跳转界面
                    Intent intent = new Intent(login.this, main.class);
                    login.this.startActivity(intent);
                } else ;
                //Toast.makeText(login.this, "请重新输入密码！", Toast.LENGTH_SHORT).show();
            }
        }

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        if (name != null && password != null) {
            rem_pw.setChecked(false);
            auto_login.setChecked(false);
            text1.setText(name);
            text2.setText(password);
        }

        Button button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = text1.getText().toString().trim();
                b = text2.getText().toString().trim();
                login.user = a;
                if (text1.getText().toString().trim().isEmpty() || text2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(login.this, "密码或账号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(login.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    if (rem_pw.isChecked()) {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", a);
                        editor.putString("PASSWORD", b);
                        editor.commit();
                    }
                    Intent intent = new Intent(login.this, main.class);
                    startActivity(intent);
                }

                /*query = new BmobQuery<Person>();
                query.addWhereEqualTo("name", a);
                query.addWhereEqualTo("address", b);
                query.setLimit(1);
                query.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        if (list != null && list.size() != 0) {
                            Toast.makeText(login.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            if (rem_pw.isChecked()) {
                                //记住用户名、密码、
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("USER_NAME", list.get(0).getName());
                                editor.putString("PASSWORD", list.get(0).getAddress());
                                editor.commit();
                            }
                            Intent intent = new Intent(login.this, main.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(login.this, "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        });

        Button button1 = (Button) findViewById(R.id.loginregister);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });

        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {
                    sp.edit().putBoolean("ISCHECK", true).commit();
                } else {
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }
            }
        });

        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
                } else {
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (login.isPasswordChanged == true || login.isLogout == true) {
            ActivityCollector.finishAll();
        } else
            super.onBackPressed();
    }

    public void onResume() {
        super.onResume();
        if (exitAll)
            finish();
    }
}
