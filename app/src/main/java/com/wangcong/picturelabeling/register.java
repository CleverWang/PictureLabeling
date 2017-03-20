package com.wangcong.picturelabeling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {
    EditText text1, text2, text3;
    //static Person p2 = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        ActivityCollector.activities.add(this);
        //Bmob.initialize(this, "2f221c1babaeca6d42723047fba1289e");
        text1 = (EditText) findViewById(R.id.register_account);
        text2 = (EditText) findViewById(R.id.register_password);
        text3 = (EditText) findViewById(R.id.password_confirm);
        Button button = (Button) findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = text1.getText().toString().trim();
                if (user_name.length() == 0) {
                    Toast.makeText(register.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                } else {

                   /* BmobQuery<Person> query = new BmobQuery<Person>();
                    query.addWhereEqualTo("name", user_name);
                    query.setLimit(1);
                    query.findObjects(new FindListener<Person>() {
                        @Override
                        public void done(List<Person> list, BmobException e) {
                            if (list != null && list.size() != 0) {
                                Toast.makeText(register.this, "用户名已被注册！", Toast.LENGTH_SHORT).show();
                                text1.setText("");
                            } else {
                                register.p2.setName(text1.getText().toString().trim());
                                String passwd = text2.getText().toString().trim();
                                if (passwd.length() == 0) {
                                    Toast.makeText(register.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (passwd.equals(text3.getText().toString().trim())) {
                                        register.p2.setAddress(passwd);
                                        register.p2.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                                    String name = new String(text1.getText().toString().trim());
                                                    String password = new String(text2.getText().toString().trim());
                                                    Intent intent = new Intent(register.this, login.class);
                                                    intent.putExtra("username", name);
                                                    intent.putExtra("password", password);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(register.this, "注册失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else
                                        Toast.makeText(register.this, "输入的两次密码不同！", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });*/


                }
            }
        });
    }
}
