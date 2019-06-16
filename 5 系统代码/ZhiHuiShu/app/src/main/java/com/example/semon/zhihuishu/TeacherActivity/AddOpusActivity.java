package com.example.semon.zhihuishu.TeacherActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.OpusData;
import com.example.semon.zhihuishu.Database.OpusDataManager;
import com.example.semon.zhihuishu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 添加学生作品
 */

public class AddOpusActivity extends AppCompatActivity {

    private TextView newOpusTitle;
    private MultiAutoCompleteTextView newOpusContext;
    private Button addOpusBtn;
    private OpusDataManager mOpusDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_opus);

        newOpusTitle = findViewById(R.id.add_opus_title);
        newOpusContext = findViewById(R.id.add_opus_context);
        addOpusBtn = findViewById(R.id.add_opus_btn);
        addOpusBtn.setOnClickListener(addOpusListener);

        if (mOpusDataManager == null){
            mOpusDataManager = new OpusDataManager(this);
        }

    }

    View.OnClickListener addOpusListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            OpusData mOpusData = new OpusData();
            mOpusData.setId(UUID.randomUUID().toString());
            mOpusData.setOpus_title(newOpusTitle.getText().toString().trim());
            mOpusData.setOpus_context(newOpusContext.getText().toString().trim());
            mOpusData.setCreateTime(simpleDateFormat.format(date));
            mOpusData.setOpusImage("/data/data/com.example.semon.zhihuishu/image/daxiongmao.jpg");
            mOpusDataManager.openDataBase();
            if(mOpusDataManager.addOpus(mOpusData) > 0){
                Log.i("addOpus","发布学生作品成功");
                Toast.makeText(AddOpusActivity.this,"发布学生作品成功！",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };
}
