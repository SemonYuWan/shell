package com.example.semon.zhihuishu.Notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.NoticeBean;
import com.example.semon.zhihuishu.Database.NoticeDataManager;
import com.example.semon.zhihuishu.R;

public class ViewNoticeActivity extends AppCompatActivity {

    private NoticeDataManager mNoticeDataManager;
    private TextView noticeTitleView;//标题
    private TextView noticeContextView;//内容
    private TextView createTimeView;//创建时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);

        initData();

    }

    private void initData() {
        noticeTitleView = findViewById(R.id.notice_title_view);
        noticeContextView = findViewById(R.id.notice_context_view);
        createTimeView = findViewById(R.id.notice_createTime_view);

        mNoticeDataManager = new NoticeDataManager(this);
        mNoticeDataManager.openDataBase();
        Intent itemNoticeIntent = getIntent();
        String itemNoticeId = itemNoticeIntent.getStringExtra("noticeId");
        NoticeBean itemNoticeBean = new NoticeBean();
        itemNoticeBean = mNoticeDataManager.findNoticeById(itemNoticeId);
        noticeTitleView.setText(itemNoticeBean.getTitle());
        noticeContextView.setText(itemNoticeBean.getContext());
        createTimeView.setText(itemNoticeBean.getCreateTime());
    }
}
