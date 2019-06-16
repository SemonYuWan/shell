package com.example.semon.zhihuishu.TeacherActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.ParentChildTaskData;
import com.example.semon.zhihuishu.Database.ParentChildTaskDataManager;
import com.example.semon.zhihuishu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 添加发现
 */

public class AddTaskActivity extends AppCompatActivity {

    private TextView newTaskTitle;
    private MultiAutoCompleteTextView newTaskContext;
    private Button addTaskBtn;
    private ParentChildTaskDataManager mParentChildTaskDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        newTaskTitle = findViewById(R.id.add_task_title);
        newTaskContext = findViewById(R.id.add_task_context);
        addTaskBtn = findViewById(R.id.add_task_btn);
        addTaskBtn.setOnClickListener(addTaskListener);

        if (mParentChildTaskDataManager == null){
            mParentChildTaskDataManager = new ParentChildTaskDataManager(this);
        }

    }

    View.OnClickListener addTaskListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            ParentChildTaskData mParentChildTaskData = new ParentChildTaskData();
            mParentChildTaskData.setId(UUID.randomUUID().toString());
            mParentChildTaskData.setTask_title(newTaskTitle.getText().toString().trim());
            mParentChildTaskData.setTask_context(newTaskContext.getText().toString().trim());
            mParentChildTaskData.setCreateTime(simpleDateFormat.format(date));
            mParentChildTaskData.setTask_image("/data/data/com.example.semon.zhihuishu/image/task.png");
            mParentChildTaskDataManager.openDataBase();
            if(mParentChildTaskDataManager.addParentChildTask(mParentChildTaskData) > 0){
                Log.i("addDiscovery","发布亲子任务成功");
                Toast.makeText(AddTaskActivity.this,"发布亲子任务成功！",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };
}
