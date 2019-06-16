package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.ParentChildTaskData;
import com.example.semon.zhihuishu.Database.ParentChildTaskDataManager;
import com.example.semon.zhihuishu.R;

import java.io.File;

public class ViewTaskActivity extends AppCompatActivity {

    private ParentChildTaskDataManager mParentChildTaskDataManager;
    private TextView taskTitle;
    private TextView taskContext;
    private ImageView taskImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        initData();
    }

    private void initData() {
        taskTitle = findViewById(R.id.task_title_view);
        taskContext = findViewById(R.id.task_context_view);
        taskImage = findViewById(R.id.task_image_view);

        mParentChildTaskDataManager = new ParentChildTaskDataManager(this);
        mParentChildTaskDataManager.openDataBase();
        Intent itemParentChildTaskIntent = getIntent();
        String itemParentChildTaskId = itemParentChildTaskIntent.getStringExtra("taskId");
        ParentChildTaskData itemParentChildTask = new ParentChildTaskData();
        itemParentChildTask = mParentChildTaskDataManager.findParentChildTaskById(itemParentChildTaskId);
        taskTitle.setText(itemParentChildTask.getTask_title());
        taskContext.setText(itemParentChildTask.getTask_context());
        taskImage.setImageURI(Uri.fromFile(new File(itemParentChildTask.getTask_image())));
    }
}
