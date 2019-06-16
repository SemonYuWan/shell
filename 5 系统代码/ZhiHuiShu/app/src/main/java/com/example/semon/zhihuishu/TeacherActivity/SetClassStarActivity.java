package com.example.semon.zhihuishu.TeacherActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.ChildData;
import com.example.semon.zhihuishu.Constant.ClassData;
import com.example.semon.zhihuishu.Constant.ClassStarData;
import com.example.semon.zhihuishu.Database.ChildDataManager;
import com.example.semon.zhihuishu.Database.ClassDataManager;
import com.example.semon.zhihuishu.Database.ClassStarDataManager;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.R;

import java.util.List;
import java.util.UUID;

/**
 * 班级之星设置界面
 */
public class SetClassStarActivity extends Activity {
    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private ClassStarDataManager mClassStarDataManager;
    private ChildDataManager mChildDataManager;
    private TeacherDataManager mTeacherDataManager;
    private ClassDataManager mClassDataManager;
    private TextView mclass;
    private TextView mClassStar;
    private ListView childListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_class_star);

        initData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        mclass = findViewById(R.id.class_textView);
        mClassStar = findViewById(R.id.daystar_textView);

        mTeacherDataManager = new TeacherDataManager(this);
        mTeacherDataManager.openDataBase();
        String classId = mTeacherDataManager.findClassByTeacherId(user_id);

        ClassStarData mClassStarData = new ClassStarData();
        mClassStarDataManager = new ClassStarDataManager(this);
        mClassStarDataManager.openDataBase();
        mClassStarData = mClassStarDataManager.findTodayClassStarByClassId(classId);
        if (mClassStarData.getId() != null){
            mClassDataManager = new ClassDataManager(this);
            mClassDataManager.openDataBase();
            ClassData mClassData = mClassDataManager.findClassById(classId);
            mclass.setText(mClassData.getClass_name());
            mClassDataManager.closeDataBase();

            mChildDataManager = new ChildDataManager(this);
            mChildDataManager.openDataBase();
            ChildData mChildData = mChildDataManager.findChildDataByChildId(mClassStarData.getChild_id());
            mClassStar.setText(mChildData.getName());
            mChildDataManager.closeDataBase();
        }else {
            mclass.setText("null");
            mClassStar.setText("今日未设置班级之星");
        }

        mClassStarDataManager.closeDataBase();

        List<ChildData> mChildDataList;
        mChildDataManager = new ChildDataManager(this);
        mChildDataManager.openDataBase();
        mChildDataList = mChildDataManager.findAllChildByClassId(classId);
        mChildDataManager.closeDataBase();
        childListView = findViewById(R.id.student_list);
        MyBaseAdapter mAdapter = new MyBaseAdapter(mChildDataList);
        childListView.setAdapter(mAdapter);

        childListView.setOnItemLongClickListener(itemOnLongClick);


    }


    private class MyBaseAdapter extends BaseAdapter {
        private List<ChildData> childDataList;

        public MyBaseAdapter(List<ChildData> childDataList) {
            this.childDataList = childDataList;
        }

        @Override
        public int getCount() {
            return childDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return childDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(SetClassStarActivity.this, R.layout.item_parent_list, null);
            TextView childName = view.findViewById(R.id.parentslist_parentName);
            TextView childName2 = view.findViewById(R.id.parentslist_childName);
            childName.setText(childDataList.get(position).getName());
            childName2.setVisibility(View.GONE);
            mChildDataManager = new ChildDataManager(SetClassStarActivity.this);
            mChildDataManager.openDataBase();
            ChildData mChildData = mChildDataManager.findChildDataByChildId(childDataList.get(position).getId());
            childName.setText(mChildData.getName());
            return view;
        }
    }

    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(SetClassStarActivity.this);
            //    设置Title的图标
            builder.setIcon(R.drawable.warn);
            //    设置Title的内容
            builder.setTitle("设置班级之星");
            //    设置Content来显示一个信息
            builder.setMessage("确定设置吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    ChildData mChildData = (ChildData) adapterView.getItemAtPosition(position);
                    ClassStarData mClassStarData = new ClassStarData();
                    mClassStarData.setId(UUID.randomUUID().toString());
                    mClassStarData.setChild_id(mChildData.getId());
                    mClassStarData.setClass_id(mChildData.getClass_id());
                    mClassStarData.setSchool_id(mChildData.getSchool_id());
                    mClassStarDataManager = new ClassStarDataManager(SetClassStarActivity.this);
                    mClassStarDataManager.openDataBase();
                    mClassStarDataManager.setTodayClassStar(mClassStarData);
                    mClassStarDataManager.closeDataBase();
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
