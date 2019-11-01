package com.desaco.fundroid.word_excel.poi_word;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.desaco.fundroid.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PoiWordMainActivity extends AppCompatActivity {

    private Context mContext;
    private int REQUEST_WRITE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_word_main);

        mContext = this;

        //利用doc模板生成doc文件
        findViewById(R.id.btn_poi_doc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputStream templetDocStream = getAssets().open("请假单模板2.doc");
//                    String targetDocPath = mContext.getExternalFilesDir("poi").getPath() + File.separator + "请假单2.doc";//这个目录，不需要申请存储权限

                    String targetDocPath = null;
                    //获得SD卡根目录路径
                    File sdDir = Environment.getExternalStorageDirectory();
                    String sdpath = sdDir.getAbsolutePath();
                    //获取指定图片路径
                    String filepath = sdpath + File.separator + "books/请假单2.doc";
                    File desFile = new File(filepath);
                    if (!desFile.exists()) {
                        desFile.createNewFile();
                    }
                    targetDocPath = filepath.toString();
                    Log.e("desaco","targetDocPath="+targetDocPath);

                    Map<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("$writeDate$", "2018年10月14日");
                    dataMap.put("$name$", "HaiyuKing");
                    dataMap.put("$dept$", "移动开发组");
                    dataMap.put("$leaveType$", "☑倒休 √年假 ✔事假 ☐病假 ☐婚假 ☐产假 ☐其他");
                    dataMap.put("$leaveReason$", "倒休一天。");
                    dataMap.put("$leaveStartDate$", "2018年10月14日上午");
                    dataMap.put("$leaveEndDate$", "2018年10月14日下午");
                    dataMap.put("$leaveDay$", "1");
                    dataMap.put("$leaveLeader$", "同意");
                    dataMap.put("$leaveDeptLeaderImg$", "同意！");

                    PoiUtils.writeToDoc(templetDocStream, targetDocPath, dataMap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        // 判断是否6.0以上的手机   不是就不用
        if (Build.VERSION.SDK_INT >= 23) {
            // 判断是否有这个权限
            if (ContextCompat.checkSelfPermission(PoiWordMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 2、申请权限: 参数二：权限的数组；参数三：请求码
                ActivityCompat.requestPermissions(PoiWordMainActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_WRITE);
            } else {
                //
            }
        }

    }
}
