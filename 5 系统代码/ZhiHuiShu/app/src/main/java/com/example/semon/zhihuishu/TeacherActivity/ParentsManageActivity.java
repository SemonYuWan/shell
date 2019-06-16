package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.ChildData;
import com.example.semon.zhihuishu.Constant.ParentData;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.R;

import java.util.List;

public class ParentsManageActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private ListView parentsListView;
    private List<ParentData> parentsList;
    private ParentDataManager mParentDataManager;
    private TeacherDataManager mTeacherDataManager;
    private TextView sign_count;
    private String title="家长管理";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_manage);

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        Intent titleIntent = getIntent();
        title = titleIntent.getStringExtra("title");
        if (title.equals("学生考勤统计")){
            setTitle("学生考勤统计");
        }


        initData();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    //初始化数据
    private void initData() {
        mParentDataManager = new ParentDataManager(this);
        mTeacherDataManager = new TeacherDataManager(this);
        mTeacherDataManager.openDataBase();
        String classId = mTeacherDataManager.findClassByTeacherId(user_id);
        mTeacherDataManager.closeDataBase();

        mParentDataManager.openDataBase();
        parentsList = mParentDataManager.findAllParentsByClass(classId);
        parentsListView = findViewById(R.id.parents_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(parentsList);
        parentsListView.setAdapter(mAdapter);
        mParentDataManager.closeDataBase();

        parentsListView.setOnItemLongClickListener(itemOnLongClick);


    }


    private class MyBaseAdapter extends BaseAdapter {
        private List<ParentData> parentList;

        public MyBaseAdapter(List<ParentData> parentList) {
            this.parentList = parentList;
        }

        @Override
        public int getCount() {
            return parentList.size();
        }

        @Override
        public Object getItem(int position) {
            return parentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            View view = View.inflate(ParentsManageActivity.this, R.layout.item_parent_list, null);

            if (title.equals("家长管理")){
                sign_count = view.findViewById(R.id.sign_count);
                sign_count.setVisibility(View.GONE);
            }

            TextView parentName = view.findViewById(R.id.parentslist_parentName);
            TextView childName = view.findViewById(R.id.parentslist_childName);
            parentName.setText(parentList.get(position).getName());
            mParentDataManager = new ParentDataManager(ParentsManageActivity.this);
            mParentDataManager.openDataBase();
            ChildData mChildData = mParentDataManager.findChildDataByParentId(parentList.get(position).getId());
            childName.setText(mChildData.getName());
            return view;
        }
    }


    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(ParentsManageActivity.this);
            //    设置Title的图标
            builder.setIcon(R.drawable.warn);
            //    设置Title的内容
            builder.setTitle("确认删除该家长吗");
            //    设置Content来显示一个信息
            builder.setMessage("确定删除吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    ParentData mParent = (ParentData) adapterView.getItemAtPosition(position);
                    mParentDataManager = new ParentDataManager(ParentsManageActivity.this);
                    mParentDataManager.openDataBase();
                    mParentDataManager.deleteParentDataById(mParent.getId());
                    mParentDataManager.closeDataBase();
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

