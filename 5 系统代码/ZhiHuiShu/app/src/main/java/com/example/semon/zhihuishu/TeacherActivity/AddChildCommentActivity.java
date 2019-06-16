package com.example.semon.zhihuishu.TeacherActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.ChildCommentData;
import com.example.semon.zhihuishu.Database.ChildCommentDataManager;
import com.example.semon.zhihuishu.R;

import java.util.UUID;

public class AddChildCommentActivity extends AppCompatActivity {

    private ChildCommentDataManager mChildCommentDataManager;
    private EditText commentTitle,commentContext,commentRemark;
    private Button addButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_comment);

        commentTitle = findViewById(R.id.child_comment_title_editText);
        commentContext = findViewById(R.id.child_comment_context_editText);
        commentRemark = findViewById(R.id.child_comment_remark_editText);
        addButton = findViewById(R.id.add_child_comment_button);
        addButton.setOnClickListener(addComment_Listener);

        if (mChildCommentDataManager == null) {
            mChildCommentDataManager = new ChildCommentDataManager(this);
            mChildCommentDataManager.openDataBase();                              //建立本地数据库
        }
    }
    View.OnClickListener addComment_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_child_comment_button:
                    ChildCommentData mChildCommentData = new ChildCommentData();
                    mChildCommentData.setId(UUID.randomUUID().toString());
                    mChildCommentData.setComment_title(commentTitle.getText().toString().trim());
                    mChildCommentData.setComment_context(commentContext.getText().toString().trim());
                    mChildCommentData.setRemark(commentRemark.getText().toString().trim());
//                    mNoticeBean.setSchool_id();
                    if (mChildCommentDataManager.addChildComment(mChildCommentData)>0){
                        Toast.makeText(AddChildCommentActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    break;
            }
        }
    };
}
