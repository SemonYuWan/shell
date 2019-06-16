package com.example.semon.zhihuishu.MessageActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.MessageData;
import com.example.semon.zhihuishu.Database.MessageDataManager;
import com.example.semon.zhihuishu.R;

import java.io.File;

public class ViewMessageActivity extends AppCompatActivity {

    private MessageDataManager mMessageDataManager;
    private TextView messageTitle;
    private TextView messageContext;
    private ImageView messageImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        initData();
    }

    private void initData() {
        messageTitle = findViewById(R.id.message_title_view);
        messageContext = findViewById(R.id.message_context_view);
        messageImage = findViewById(R.id.message_image_view);

        mMessageDataManager = new MessageDataManager(this);
        mMessageDataManager.openDataBase();
        Intent itemMessageIntent = getIntent();
        String itemMessageId = itemMessageIntent.getStringExtra("messageId");
        MessageData itemMessage = new MessageData();
        itemMessage = mMessageDataManager.findMessageById(itemMessageId);
        messageTitle.setText(itemMessage.getTitle());
        messageContext.setText(itemMessage.getConext());
        messageImage.setImageURI(Uri.fromFile(new File(itemMessage.getImage())));
    }
}
