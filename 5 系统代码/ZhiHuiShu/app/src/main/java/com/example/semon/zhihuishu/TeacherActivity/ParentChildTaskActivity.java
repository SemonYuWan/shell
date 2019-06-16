package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.ParentChildTaskData;
import com.example.semon.zhihuishu.Database.ParentChildTaskDataManager;
import com.example.semon.zhihuishu.R;

import java.io.File;
import java.util.List;

public class ParentChildTaskActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;

    private Context context;
    private FloatingActionButton addTaskBtn;
    private ListView taskListView;
    private List<ParentChildTaskData> taskDataList;
    private ParentChildTaskDataManager mParentChildTaskDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        context = ParentChildTaskActivity.this;

        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    //初始化数据
    private void initData() {
        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        mParentChildTaskDataManager = new ParentChildTaskDataManager(this);
        mParentChildTaskDataManager.openDataBase();
        taskDataList = mParentChildTaskDataManager.findAllParentChildTask();
        taskListView = findViewById(R.id.task_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(taskDataList);
        taskListView.setAdapter(mAdapter);

        taskListView.setOnItemClickListener(itemOnClick);
        taskListView.setOnItemLongClickListener(itemOnLongClick);

        addTaskBtn = findViewById(R.id.add_task_view_button);
        addTaskBtn.bringToFront();
        addTaskBtn.setOnClickListener(addTaskOnClick);

        if(user_type.equals("parent")){
            addTaskBtn.setVisibility(View.GONE);
        }
        else{
            taskListView.setOnItemLongClickListener(itemOnLongClick);
        }

    }

    //点击事件
    View.OnClickListener addTaskOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_task_view_button:
                    Log.i("TAG","添加亲子任务");
                    Intent addIntent = new Intent(ParentChildTaskActivity.this,AddTaskActivity.class);
                    startActivity(addIntent);
                    break;
            }
        }
    };

    private class MyBaseAdapter extends BaseAdapter {
        private List<ParentChildTaskData> mParentChildTaskDataList;
        public MyBaseAdapter(List<ParentChildTaskData> mParentChildTaskDataList) {
            this.mParentChildTaskDataList=mParentChildTaskDataList;
        }

        @Override
        public int getCount() {
            return mParentChildTaskDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mParentChildTaskDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(ParentChildTaskActivity.this,R.layout.item_task,null);
            TextView taskTitle = view.findViewById(R.id.task_title_textview);
            ImageView taskImage = view.findViewById(R.id.task_image);
            TextView taskCreateTime = view.findViewById(R.id.task_createtime_textview);
            TextView taskLike = view.findViewById(R.id.task_like_textview);
            taskTitle.setText(mParentChildTaskDataList.get(position).getTask_title());
            taskImage.setImageURI(Uri.fromFile(new File(mParentChildTaskDataList.get(position).getTask_image())));            taskCreateTime = view.findViewById(R.id.task_createtime_textview);
            taskLike = view.findViewById(R.id.task_like_textview);
            return view;
        }
    }

    //item点击事件
    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            ParentChildTaskData mTask = (ParentChildTaskData)adapterView.getItemAtPosition(position);
            Log.i("ItemClick","点击了"+mTask.getId());
            Intent itemDiscoveryIntent = new Intent(ParentChildTaskActivity.this,ViewTaskActivity.class);
            itemDiscoveryIntent.putExtra("taskId", mTask.getId());
            startActivity(itemDiscoveryIntent);
        }
    };


    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(ParentChildTaskActivity.this);
            //    设置Title的图标
            builder.setIcon(R.drawable.warn);
            //    设置Title的内容
            builder.setTitle("删除");
            //    设置Content来显示一个信息
            builder.setMessage("确定删除吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    ParentChildTaskData mParentChildTaskData = (ParentChildTaskData) adapterView.getItemAtPosition(position);
                    mParentChildTaskDataManager = new ParentChildTaskDataManager(ParentChildTaskActivity.this);
                    mParentChildTaskDataManager.openDataBase();
                    mParentChildTaskDataManager.deleteParentChildTaskById(mParentChildTaskData.getId());
                    mParentChildTaskDataManager.closeDataBase();
                    onRestart();
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    onRestart();
                }
            });
            builder.show();

            return true;
        }
    };
}
