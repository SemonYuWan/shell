package com.example.semon.zhihuishu.HeadmasterActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Circle.CircleActivity;
import com.example.semon.zhihuishu.Constant.TeachPlanData;
import com.example.semon.zhihuishu.Database.TeachPlanDataManager;
import com.example.semon.zhihuishu.DiscoveryActivity.DiscoveryActivity;
import com.example.semon.zhihuishu.MessageActivity.MessageActivity;
import com.example.semon.zhihuishu.MineActivity.MineActivity;
import com.example.semon.zhihuishu.R;
import com.example.semon.zhihuishu.RecipeActivity.RecipeActivity;
import com.example.semon.zhihuishu.TeacherActivity.ViewTeachPlanActivity;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;


/**
 * 园长主页
 */
public class HeadmasterMainActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private LinearLayout recipeViewBtn;//每日食谱
    private LinearLayout classManageViewBtn;//班级管理
    private LinearLayout schoolAboutViewBtn;//园所风采
    private TeachPlanDataManager mTeachPlanDataManager;

    private ListView mTeachPlanListView;
    private List<TeachPlanData> mTeachPlanDataList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headmaster_main);
        context = HeadmasterMainActivity.this;

        initData();
        initMenu();




    }

    private void initData() {
        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        recipeViewBtn = findViewById(R.id.recipe_view_btn);
        classManageViewBtn = findViewById(R.id.class_manage_view_btn);
        schoolAboutViewBtn = findViewById(R.id.school_view_btn);

        recipeViewBtn.setOnClickListener(headmasterFunction);
        classManageViewBtn.setOnClickListener(headmasterFunction);
        schoolAboutViewBtn.setOnClickListener(headmasterFunction);

        mTeachPlanDataManager = new TeachPlanDataManager(this);
        mTeachPlanDataManager.openDataBase();
        mTeachPlanDataList = mTeachPlanDataManager.findAllTeachPlan();
        mTeachPlanListView = findViewById(R.id.teach_plan_list);
        MyBaseAdapter mAdapter = new MyBaseAdapter(mTeachPlanDataList);
        mTeachPlanListView.setAdapter(mAdapter);
        mTeachPlanListView.setOnItemClickListener(itemOnClick);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            TeachPlanData mTeachPlanData = (TeachPlanData)adapterView.getItemAtPosition(position);
            Log.i("ItemClick","点击了"+mTeachPlanData.getId());
            Intent itemDiscoveryIntent = new Intent(HeadmasterMainActivity.this,ViewTeachPlanActivity.class);
            itemDiscoveryIntent.putExtra("teachPlanId", mTeachPlanData.getId());
            startActivity(itemDiscoveryIntent);
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
            View view = View.inflate(HeadmasterMainActivity.this,R.layout.item_teach_plan,null);
            TextView teachPlanTitle = view.findViewById(R.id.discovery_title_textview);
            TextView teachPlanContext = view.findViewById(R.id.teach_plan_context_textview);
            ImageView teachPlansIcon = view.findViewById(R.id.discovery_image);
            teachPlanTitle.setText(teachPlans.get(position).getPlanTitle());
            teachPlanContext.setText(teachPlans.get(position).getPlanContext());
            return view;
        }
    }

    private void initMenu() {
        LinearLayout menuFirst;//主页面
        LinearLayout menuDiscovery;//发现
        LinearLayout menuCircle;//班级圈
        LinearLayout menuMessage;//消息
        LinearLayout menuMine;//我的

        menuFirst = findViewById(R.id.menu_first);
        menuDiscovery = findViewById(R.id.menu_discovery);
        menuCircle = findViewById(R.id.menu_cicle);
        menuMessage = findViewById(R.id.menu_message);
        menuMine = findViewById(R.id.menu_mine);

        TextView menu_textview;
        menu_textview = findViewById(R.id.menu_textview1);
        menu_textview.setTextColor(Color.parseColor("#FF00CF30"));
        menu_textview.setTextSize(16);

        menuFirst.setOnClickListener(menuListener);
        menuDiscovery.setOnClickListener(menuListener);
        menuCircle.setOnClickListener(menuListener);
        menuMessage.setOnClickListener(menuListener);
        menuMine.setOnClickListener(menuListener);

    }

    //教师功能监听事件
    OnClickListener headmasterFunction = new OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intentHeadmasterFunctionIntent;
            switch (view.getId()) {
                case R.id.recipe_view_btn://每日食谱
                    intentHeadmasterFunctionIntent = new Intent(context,RecipeActivity.class);
                    startActivity(intentHeadmasterFunctionIntent);
                    break;
                case R.id.class_manage_view_btn://班级管理
                    intentHeadmasterFunctionIntent = new Intent(context,ClassManageActivity.class);
                    startActivity(intentHeadmasterFunctionIntent);
                    break;
                case R.id.school_view_btn://园所风采
                    intentHeadmasterFunctionIntent = new Intent(context,SchoolActivity.class);
                    startActivity(intentHeadmasterFunctionIntent);
                    break;
            }
        }
    };

    //菜单栏
    OnClickListener menuListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent menuIntent = null;
            switch (view.getId()) {
                case R.id.menu_first:
                    onRestart();
                    break;
                case R.id.menu_discovery:
                    menuIntent = new Intent(HeadmasterMainActivity.this,DiscoveryActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_cicle:
                    menuIntent = new Intent(HeadmasterMainActivity.this,CircleActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_message:
                    menuIntent = new Intent(HeadmasterMainActivity.this,MessageActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_mine:
                    menuIntent = new Intent(HeadmasterMainActivity.this,MineActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
            }
        }
    };

    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(HeadmasterMainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
