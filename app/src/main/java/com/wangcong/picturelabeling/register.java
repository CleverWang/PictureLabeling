package com.wangcong.picturelabeling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {
    EditText text1, text2, text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        ActivityCollector.activities.add(this);

        text1 = (EditText) findViewById(R.id.edit_register_account);
        text2 = (EditText) findViewById(R.id.edit_register_password);
        text3 = (EditText) findViewById(R.id.edit_password_confirm);
        Button button = (Button) findViewById(R.id.button_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = text1.getText().toString().trim();
                String pwd = text2.getText().toString().trim();
                String confirm = text3.getText().toString().trim();
                if (user_name.length() == 0) {
                    Toast.makeText(register.this, "请输入手机号注册！", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(register.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                } else if (!pwd.equals(confirm)) {
                    Toast.makeText(register.this, "两次密码不匹配！", Toast.LENGTH_SHORT).show();
                } else {
                    //设置输入框不可编辑
                    text1.setFocusableInTouchMode(false);
                    text2.setFocusableInTouchMode(false);
                    text3.setFocusableInTouchMode(false);
                   /* BmobQuery<Person> query = new BmobQuery<Person>();
                    query.addWhereEqualTo("name", user_name);
                    query.setLimit(1);
                    query.findObjects(new FindListener<Person>() {
                        @Override
                        public void done(List<Person> list, BmobException e) {
                            if (list != null && list.size() != 0) {
                                Toast.makeText(bluerectangle.this, "用户名已被注册！", Toast.LENGTH_SHORT).show();
                                text1_username.setText("");
                            } else {
                                bluerectangle.p2.setName(text1_username.getText().toString().trim());
                                String passwd = text2_pwd.getText().toString().trim();
                                if (passwd.length() == 0) {
                                    Toast.makeText(bluerectangle.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (passwd.equals(text3.getText().toString().trim())) {
                                        bluerectangle.p2.setAddress(passwd);
                                        bluerectangle.p2.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(bluerectangle.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                                    String name = new String(text1_username.getText().toString().trim());
                                                    String password = new String(text2_pwd.getText().toString().trim());
                                                    Intent intent = new Intent(bluerectangle.this, login.class);
                                                    intent.putExtra("username", name);
                                                    intent.putExtra("password", password);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(bluerectangle.this, "注册失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else
                                        Toast.makeText(bluerectangle.this, "输入的两次密码不同！", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });*/
                   //此处编写注册逻辑
                    Toast.makeText(register.this, "注册成功！", Toast.LENGTH_SHORT).show();

                    //注册成功，将用户名和密码传到登录界面用于填充
                    String name = user_name;
                    String password = pwd;
                    Intent intent = new Intent(register.this, login.class);
                    intent.putExtra("username", name);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });
    }
}
