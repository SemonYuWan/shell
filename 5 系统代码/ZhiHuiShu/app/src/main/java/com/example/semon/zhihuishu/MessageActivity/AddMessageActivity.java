package com.example.semon.zhihuishu.MessageActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.MessageData;
import com.example.semon.zhihuishu.Database.MessageDataManager;
import com.example.semon.zhihuishu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 添加发现
 */

public class AddMessageActivity extends AppCompatActivity {

    private TextView newMessageTitle;
    private MultiAutoCompleteTextView newMessageContext;
    private Button addMessageBtn;
    private MessageDataManager mMessageDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        newMessageTitle = findViewById(R.id.add_message_title);
        newMessageContext = findViewById(R.id.add_message_context);
        addMessageBtn = findViewById(R.id.add_message_btn);
        addMessageBtn.setOnClickListener(addMessageListener);

        if (mMessageDataManager == null){
            mMessageDataManager = new MessageDataManager(this);
        }

    }

    View.OnClickListener addMessageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            MessageData mMessageData = new MessageData();
            mMessageData.setId(UUID.randomUUID().toString());
            mMessageData.setTitle(newMessageTitle.getText().toString().trim());
            mMessageData.setConext(newMessageContext.getText().toString().trim());
            mMessageData.setCreateTime(simpleDateFormat.format(date));
            mMessageData.setImage("/data/data/com.example.semon.zhihuishu/image/message10.jpg");
            mMessageDataManager.openDataBase();
            if(mMessageDataManager.addMessage(mMessageData) > 0){
                Log.i("addMessage","发布发现文章成功");
                Toast.makeText(AddMessageActivity.this,"发布文章成功！",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };
}
