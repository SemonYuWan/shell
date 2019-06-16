package com.example.semon.zhihuishu.DiscoveryActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.DiscoveryData;
import com.example.semon.zhihuishu.Database.DiscoveryDataManager;
import com.example.semon.zhihuishu.R;

import java.io.File;

public class ViewDiscoveryActivity extends AppCompatActivity {

    private DiscoveryDataManager mDiscoveryDataManager;
    private TextView discoveryTitle;
    private TextView discoveryContext;
    private ImageView discoveryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_discovery);

        initData();
    }

    private void initData() {
        discoveryTitle = findViewById(R.id.discovery_title_view);
        discoveryContext = findViewById(R.id.discovery_context_view);
        discoveryImage = findViewById(R.id.discovery_image_view);

        mDiscoveryDataManager = new DiscoveryDataManager(this);
        mDiscoveryDataManager.openDataBase();
        Intent itemDiscoveryIntent = getIntent();
        String itemDiscoveryId = itemDiscoveryIntent.getStringExtra("discoveryId");
        DiscoveryData itemDiscovery = new DiscoveryData();
        itemDiscovery = mDiscoveryDataManager.findDiscoveryById(itemDiscoveryId);
        discoveryTitle.setText(itemDiscovery.getTitle());
        discoveryContext.setText(itemDiscovery.getConext());
        discoveryImage.setImageURI(Uri.fromFile(new File(itemDiscovery.getImage())));
    }
}
