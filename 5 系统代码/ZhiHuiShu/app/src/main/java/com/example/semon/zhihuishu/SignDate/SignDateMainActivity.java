package com.example.semon.zhihuishu.SignDate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.semon.zhihuishu.R;

public class SignDateMainActivity extends Activity {

    private SignDate signDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signdate_main);
        signDate = findViewById(R.id.signDate);
        signDate.setOnSignedSuccess(new OnSignedSuccess() {
            @Override
            public void OnSignedSuccess() {
                Log.e("wqf","签到成功");
            }
        });
    }
}
