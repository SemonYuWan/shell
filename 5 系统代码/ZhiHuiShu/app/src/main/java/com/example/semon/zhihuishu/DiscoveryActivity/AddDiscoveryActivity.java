package com.example.semon.zhihuishu.DiscoveryActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.DiscoveryData;
import com.example.semon.zhihuishu.Database.DiscoveryDataManager;
import com.example.semon.zhihuishu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 添加发现
 */

public class AddDiscoveryActivity extends AppCompatActivity {

    private TextView newDiscoveryTitle;
    private MultiAutoCompleteTextView newDiscoveryContext;
    private Button addDiscoveryBtn;
    private DiscoveryDataManager mDiscoveryDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discovery);

        newDiscoveryTitle = findViewById(R.id.add_discovery_title);
        newDiscoveryContext = findViewById(R.id.add_discovery_context);
        addDiscoveryBtn = findViewById(R.id.add_discovery_btn);
        addDiscoveryBtn.setOnClickListener(addDiscoveryListener);

        if (mDiscoveryDataManager == null){
            mDiscoveryDataManager = new DiscoveryDataManager(this);
        }

    }

    View.OnClickListener addDiscoveryListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            DiscoveryData mDiscoveryData = new DiscoveryData();
            mDiscoveryData.setId(UUID.randomUUID().toString());
            mDiscoveryData.setTitle(newDiscoveryTitle.getText().toString().trim());
            mDiscoveryData.setConext(newDiscoveryContext.getText().toString().trim());
            mDiscoveryData.setCreateTime(simpleDateFormat.format(date));
            mDiscoveryData.setImage("/data/data/com.example.semon.zhihuishu/image/discovery10.jpg");
            mDiscoveryDataManager.openDataBase();
            if(mDiscoveryDataManager.addDiscovery(mDiscoveryData) > 0){
                Log.i("addDiscovery","发布发现文章成功");
                Toast.makeText(AddDiscoveryActivity.this,"发布文章成功！",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };
}
