package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.semon.zhihuishu.Constant.TeachPlanData;
import com.example.semon.zhihuishu.Database.TeachPlanDataManager;
import com.example.semon.zhihuishu.R;

import java.util.List;

public class TeachPlanActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private FloatingActionButton addTeachPlanBtn;
    private ListView teachPlanListView;
    private List<TeachPlanData> teachPlanList;
    private TeachPlanDataManager mTeachPlanDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_plan);

        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    //初始化数据
    private void initData() {
        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        mTeachPlanDataManager = new TeachPlanDataManager(this);
        mTeachPlanDataManager.openDataBase();
        teachPlanList = mTeachPlanDataManager.findAllTeachPlan();
        teachPlanListView = findViewById(R.id.teach_plan_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(teachPlanList);
        teachPlanListView.setAdapter(mAdapter);
        teachPlanListView.setOnItemClickListener(itemOnClick);
        teachPlanListView.setOnItemLongClickListener(itemOnLongClick);

        addTeachPlanBtn = findViewById(R.id.add_teach_plan_view_button);
        addTeachPlanBtn.bringToFront();
        addTeachPlanBtn.setOnClickListener(addTeachPlanOnClick);

        if(user_type.equals("parent")){
            addTeachPlanBtn.setVisibility(View.GONE);
        }
        else{
            teachPlanListView.setOnItemLongClickListener(itemOnLongClick);
        }

    }

    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            TeachPlanData mTeachPlanData = (TeachPlanData)adapterView.getItemAtPosition(position);
            Log.i("ItemClick","点击了"+mTeachPlanData.getId());
            Intent itemDiscoveryIntent = new Intent(TeachPlanActivity.this,ViewTeachPlanActivity.class);
            itemDiscoveryIntent.putExtra("teachPlanId", mTeachPlanData.getId());
            startActivity(itemDiscoveryIntent);
        }
    };

    //点击事件
    View.OnClickListener addTeachPlanOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_teach_plan_view_button:
                    Log.i("TAG","添加教学计划");
                    Intent addIntent = new Intent(TeachPlanActivity.this,AddTeachPlanActivity.class);
                    startActivity(addIntent);
                    break;
            }
        }
    };

    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(TeachPlanActivity.this);
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
                    TeachPlanData mTeachPlanData = (TeachPlanData) adapterView.getItemAtPosition(position);
                    mTeachPlanDataManager = new TeachPlanDataManager(TeachPlanActivity.this);
                    mTeachPlanDataManager.openDataBase();
                    mTeachPlanDataManager.deleteTeachPlanDataById(mTeachPlanData.getId());
                    mTeachPlanDataManager.closeDataBase();
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

    private class MyBaseAdapter extends BaseAdapter {
        private List<TeachPlanData> teachPlans;
        public MyBaseAdapter(List<TeachPlanData> teachPlans) {
            this.teachPlans=teachPlans;
        }

        @Override
        public int getCount() {
            return teachPlans.size();
        }

        @Override
        public Object getItem(int position) {
            return teachPlans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(TeachPlanActivity.this,R.layout.item_teach_plan,null);
            TextView teachPlanTitle = view.findViewById(R.id.discovery_title_textview);
            TextView teachPlanContext = view.findViewById(R.id.teach_plan_context_textview);
            ImageView teachPlansIcon = view.findViewById(R.id.discovery_image);
            teachPlanTitle.setText(teachPlans.get(position).getPlanTitle());
            teachPlanContext.setText(teachPlans.get(position).getPlanContext());
            return view;
        }
    }
}
