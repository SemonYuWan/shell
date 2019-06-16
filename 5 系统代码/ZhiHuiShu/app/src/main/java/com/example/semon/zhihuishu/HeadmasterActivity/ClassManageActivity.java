package com.example.semon.zhihuishu.HeadmasterActivity;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.semon.zhihuishu.Constant.ClassData;
import com.example.semon.zhihuishu.Constant.HeadmasterData;
import com.example.semon.zhihuishu.Constant.TeacherData;
import com.example.semon.zhihuishu.Database.ClassDataManager;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.R;

import java.util.List;

public class ClassManageActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private TextView sign_count;
    private ListView parentsListView;
    private List<ClassData> mClassDataList;
    private ClassDataManager mClassDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;
    private TeacherDataManager mTeacherDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_manage);

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);


        initData();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    //初始化数据
    private void initData() {
        mClassDataManager = new ClassDataManager(this);
        mHeadmasterDataManager = new HeadmasterDataManager(this);
        mHeadmasterDataManager.openDataBase();
        HeadmasterData mHeadmasterData = mHeadmasterDataManager.fetchHeadmasterDataById(user_id);
        mHeadmasterDataManager.closeDataBase();

        mClassDataManager.openDataBase();
        mClassDataList = mClassDataManager.findAllClassBySchoolId(mHeadmasterData.getSchool_id());
        parentsListView = findViewById(R.id.parents_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(mClassDataList);
        parentsListView.setAdapter(mAdapter);
        mClassDataManager.closeDataBase();

        parentsListView.setOnItemLongClickListener(itemOnLongClick);


    }


    private class MyBaseAdapter extends BaseAdapter {
        private List<ClassData> classList;

        public MyBaseAdapter(List<ClassData> classList) {
            this.classList = classList;
        }

        @Override
        public int getCount() {
            return classList.size();
        }

        @Override
        public Object getItem(int position) {
            return classList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(ClassManageActivity.this, R.layout.item_parent_list, null);

            sign_count = view.findViewById(R.id.sign_count);
            sign_count.setVisibility(View.GONE);

            TextView className = view.findViewById(R.id.parentslist_parentName);
            TextView teacherName = view.findViewById(R.id.parentslist_childName);
            className.setText(classList.get(position).getClass_name());
            mTeacherDataManager = new TeacherDataManager(ClassManageActivity.this);
            mTeacherDataManager.openDataBase();
            TeacherData mTeacherData = mTeacherDataManager.fetchTeacherDataById(classList.get(position).getClass_manageId());
            teacherName.setText(mTeacherData.getName());
            return view;
        }
    }


    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(ClassManageActivity.this);
            //    设置Title的图标
            builder.setIcon(R.drawable.warn);
            //    设置Title的内容
            builder.setTitle("删除班级");
            //    设置Content来显示一个信息
            builder.setMessage("确定删除吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    ClassData mClassData = (ClassData) adapterView.getItemAtPosition(position);
                    mClassDataManager = new ClassDataManager(ClassManageActivity.this);
                    mClassDataManager.openDataBase();
                    mClassDataManager.deleteClassDataById(mClassData.getId());
                    mClassDataManager.closeDataBase();
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

