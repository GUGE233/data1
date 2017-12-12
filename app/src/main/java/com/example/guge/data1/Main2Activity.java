package com.example.guge.data1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main2Activity extends AppCompatActivity {
    public static int mode = Context.MODE_WORLD_READABLE
            + Context.MODE_WORLD_WRITEABLE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btn_save = (Button)findViewById(R.id.save);
        Button btn_load = (Button)findViewById(R.id.load);
        Button btn_clear = (Button)findViewById(R.id.clear);
        Button btn_delete = (Button)findViewById(R.id.delete);
        final EditText ed_title = (EditText)findViewById(R.id.title);
        final EditText ed_content = (EditText)findViewById(R.id.content);

        //点击保存按钮
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //首先获取题目和文章信息
                String title = ed_title.getText().toString();
                String content = ed_content.getText().toString();
                //先寻找文件，找不到就新建，然后吧内容写入文件,最后弹出成功信息
                try{
                    FileOutputStream fos = openFileOutput(title,Context.MODE_PRIVATE);
                    fos.write(content.getBytes());
                    fos.flush();
                    fos.close();
                    Toast.makeText(Main2Activity.this,"文件保存成功",Toast.LENGTH_SHORT).show();
                }
                //如果写入的过程出错，就抛出异常，显示错误信息
                catch (Exception e){
                    Toast.makeText(Main2Activity.this,"文件保存失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //点击载入按钮
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //首先获取题目
                String title = ed_title.getText().toString();
                //先寻找文件，如果找到了文件，就吧文件的内容赋值给content
                try{
                    FileInputStream fis = openFileInput(title);
                    byte[] readBytes = new byte[fis.available()];
                    StringBuilder sb = new StringBuilder("");
                    int len = 0;
                    //读取文件内容
                    while((len = fis.read(readBytes))>0){
                        sb.append(new String(readBytes,0,len));
                    }
                    fis.close();
                    String content = sb.toString();
                    ed_content.setText(content);
                }
                //如果找不到文件，就弹出错误信息
                catch(Exception e){
                    Toast.makeText(Main2Activity.this,"文件读取失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //点击清除按钮
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把各种信息清空，还原原来的提示信息即可
                ed_title.setText("");
                ed_content.setText("");
                ed_title.setHint("Title");
                ed_content.setHint("File Content Here");
            }
        });
        //点击删除按钮
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //首先获取title中的内容
                String title = ed_title.getText().toString();
                //删除指定名称的文件,并弹出成功信息
                if(deleteFile(title)){
                    Toast.makeText(Main2Activity.this,"文件删除成功",Toast.LENGTH_SHORT).show();
                    ed_title.setText("");
                    ed_content.setText("");
                    ed_title.setHint("Title");
                    ed_content.setHint("File Content Here");

                }
                //如果找不到文件，弹出错误信息
                //deleteFile()函数在找不到文件的时候会返回false，所以可以直接写在if的条件判断里面
                //这样既可以尝试执行删除操作，也可以在找不到文件的时候自动返回false用于条件判断
                else{
                    Toast.makeText(Main2Activity.this,"文件删除失败",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}
