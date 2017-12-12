package com.example.guge.data1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //首先要做的是把SharedPreferences配置好，并声明一个editor
    public static final String PREFERENCE_NAME = "SavePass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sharedPreferences =
                getSharedPreferences(PREFERENCE_NAME,0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        //从xml文件中读取一个判断是否没设密码的布尔变量
        //如果没有，就说明还没有设置密码，缺省值为true
        final boolean first_open = sharedPreferences.getBoolean("first_open", true);

        final EditText editText1 = (EditText) findViewById(R.id.pass1);
        final EditText editText2 = (EditText) findViewById(R.id.pass2);

        //如果已经设置了密码
        if (!first_open) {
            //不显示输入确认密码的输入栏，也不占用空间
            editText2.setVisibility(View.GONE);
            //原来灰色的提示文字也作出改变
            editText1.setHint("Password");

        }

        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        Button btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击OK按钮后执行的操作
                //如果还没有设置密码
                if (first_open) {
                    String pass1 = "";
                    String pass2 = "";
                    pass1 = editText1.getText().toString();
                    pass2 = editText2.getText().toString();
                    if (pass1.equals("") || pass2.equals("")) {
                        Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    //两个密码输入相同
                    else if (pass1.equals(pass2)) {
                        //在xml文件中加入boolean变量，设定为false
                        //下次打开应用就可以读取到这个值为false的变量，让系统直到已经设置了密码
                        editor.putBoolean("first_open", false);
                        //把密码存进xml文件里
                        editor.putString("password", pass1);
                        editor.commit();
                        //进入编辑界面
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent);


                    }
                    //两个密码输入不同
                    else {
                        Toast.makeText(MainActivity.this, "两次密码输入不同", Toast.LENGTH_SHORT).show();
                    }
                }
                //如果已经设置了密码,只需要检查一下和之前设定的密码是否相同就可以了
                else {
                    String pass = "";
                    pass = editText1.getText().toString();
                    String correct_pass = sharedPreferences.getString("password", "error!");
                    if (pass.equals(correct_pass)) {
                        //进入编辑界面
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //点击CLEAR按钮后执行的操作
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first_open) {
                    editText1.setText("");
                    editText2.setText("");
                    editText1.setHint("New Password");
                    editText2.setHint("Confirm Password");
                } else {
                    editText1.setText("");
                    editText1.setHint("New Password");
                }
            }
        });
    }

}
