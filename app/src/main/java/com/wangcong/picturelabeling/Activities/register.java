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

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText text_username, text_pwd, text_confirm;//注册用户名、密码、确认密码输入框
    private String user_name, pwd;//注册用户名、密码
    private HttpCallbackListener listener = new HttpCallbackListener() {
        @Override
        public void onFinish(final String response) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Log.d("JSON", "onFinish: " + response);
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("reg_answer");
                        if (result.equals("1")) {
                            Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            //注册成功，将用户名和密码传到登录界面
                            String name = user_name;
                            String password = pwd;
                            Intent intent = new Intent();
                            intent.putExtra("username", name);
                            intent.putExtra("password", password);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else if (result.equals("-1")) {
                            //Log.d("JSON", "onFinish: " + result);
                            Toast.makeText(Register.this, "该用户已被注册！", Toast.LENGTH_SHORT).show();
                        }
                        //Log.d("JSON", "onFinish: " + result);
                    } catch (JSONException e) {
                        Toast.makeText(Register.this, "发生错误，请重试！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onError(final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Register.this, "发生错误，请重试！", Toast.LENGTH_SHORT).show();
                }
            });
            //Log.d("Error", "onError: " + e.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);

        //初始化控件
        text_username = (EditText) findViewById(R.id.edit_register_account);
        text_pwd = (EditText) findViewById(R.id.edit_register_password);
        text_confirm = (EditText) findViewById(R.id.edit_password_confirm);
        Button button = (Button) findViewById(R.id.button_register);

        //注册逻辑
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = text_username.getText().toString().trim();
                pwd = text_pwd.getText().toString().trim();
                String confirm = text_confirm.getText().toString().trim();
                if (user_name.length() == 0) {
                    Toast.makeText(Register.this, "请输入手机号注册！", Toast.LENGTH_SHORT).show();
                } else if (checkCellphone(user_name) == false) {
                    Toast.makeText(Register.this, "输入的手机号格式错误！", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(Register.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                } else if (!pwd.equals(confirm)) {
                    Toast.makeText(Register.this, "两次密码不匹配！", Toast.LENGTH_SHORT).show();
                } else {
                    String ip = GlobalFlags.getIpAddress() + "register.jsp";
                    String params = "pptelephone=" + user_name + "&ppassword=" + pwd;
                    HttpUtil.sendHttpRequest(ip, params, listener);
                }
            }
        });
    }

    /**
     * 验证手机号
     *
     * @param cellphone 手机号
     * @return 布尔值
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^1[0-9]{10}$";
        return Pattern.matches(regex, cellphone);
    }
}
