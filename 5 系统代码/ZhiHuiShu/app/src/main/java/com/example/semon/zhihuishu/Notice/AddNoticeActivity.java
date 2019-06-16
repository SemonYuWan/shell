package com.example.semon.zhihuishu.Notice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.NoticeBean;
import com.example.semon.zhihuishu.Database.NoticeDataManager;
import com.example.semon.zhihuishu.R;

import java.util.UUID;

public class AddNoticeActivity extends AppCompatActivity {

    private NoticeDataManager mNoticeDataManager;
    private EditText noticeTitle,noticeContext,noticeType,noticeRemark;
    private Button addButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        noticeTitle = findViewById(R.id.notice_title_editText);
        noticeContext = findViewById(R.id.notice_context_editText);
        noticeType = findViewById(R.id.notice_type_editText);
        noticeRemark = findViewById(R.id.notice_remark_editText);
        addButton = findViewById(R.id.add_notice_button);
        addButton.setOnClickListener(addNotice_Listener);

        if (mNoticeDataManager == null) {
            mNoticeDataManager = new NoticeDataManager(this);
            mNoticeDataManager.openDataBase();                              //建立本地数据库
        }
    }
    View.OnClickListener addNotice_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_notice_button:
                    NoticeBean mNoticeBean = new NoticeBean();
                    mNoticeBean.setId(UUID.randomUUID().toString());
                    mNoticeBean.setTitle(noticeTitle.getText().toString().trim());
                    mNoticeBean.setContext(noticeContext.getText().toString().trim());
                    mNoticeBean.setType(noticeType.getText().toString().trim());
                    mNoticeBean.setRemark(noticeRemark.getText().toString().trim());
//                    mNoticeBean.setSchool_id();
                    if (mNoticeDataManager.addNotice(mNoticeBean)>0){
                        Toast.makeText(AddNoticeActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    break;
            }
        }
    };
}
