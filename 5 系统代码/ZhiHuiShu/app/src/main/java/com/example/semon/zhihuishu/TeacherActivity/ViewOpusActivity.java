package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.OpusData;
import com.example.semon.zhihuishu.Database.OpusDataManager;
import com.example.semon.zhihuishu.R;

import java.io.File;

public class ViewOpusActivity extends AppCompatActivity {

    private OpusDataManager mOpusDataManager;
    private TextView opusTitle;
    private TextView opusContext;
    private ImageView opusImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_opus);

        initData();
    }

    private void initData() {
        opusTitle = findViewById(R.id.opus_title_view);
        opusContext = findViewById(R.id.opus_context_view);
        opusImage = findViewById(R.id.opus_image_view);

        mOpusDataManager = new OpusDataManager(this);
        mOpusDataManager.openDataBase();
        Intent itemOpusIntent = getIntent();
        String itemOpusId = itemOpusIntent.getStringExtra("opusId");
        OpusData itemOpus = new OpusData();
        itemOpus = mOpusDataManager.findOpusById(itemOpusId);
        opusTitle.setText(itemOpus.getOpus_title());
        opusContext.setText(itemOpus.getOpus_context());
        opusImage.setImageURI(Uri.fromFile(new File(itemOpus.getOpusImage())));
    }
}
