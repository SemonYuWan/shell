package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.ChildCommentData;
import com.example.semon.zhihuishu.Database.ChildCommentDataManager;
import com.example.semon.zhihuishu.R;

public class ViewChildCommentActivity extends AppCompatActivity {

    private ChildCommentDataManager mChildCommentDataManager;
    private TextView commentTitleView;//
    private TextView commentContextView;//
    private TextView createTimeView;//创建时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_child_comment);

        initData();

    }

    private void initData() {
        commentTitleView = findViewById(R.id.child_comment_title_view);
        commentContextView = findViewById(R.id.child_comment_context_view);
        createTimeView = findViewById(R.id.child_comment_createTime_view);

        mChildCommentDataManager = new ChildCommentDataManager(this);
        mChildCommentDataManager.openDataBase();
        Intent itemChildCommentIntent = getIntent();
        String itemChildCommentId = itemChildCommentIntent.getStringExtra("childCommentId");
        ChildCommentData itemChildComment = new ChildCommentData();
        itemChildComment = mChildCommentDataManager.findChildCommentById(itemChildCommentId);
        commentTitleView.setText(itemChildComment.getComment_title());
        commentContextView.setText(itemChildComment.getComment_context());
        createTimeView.setText(itemChildComment.getCreateTime());
    }
}
