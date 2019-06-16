package com.example.semon.zhihuishu.Circle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.CircleData;
import com.example.semon.zhihuishu.Constant.HeadmasterData;
import com.example.semon.zhihuishu.Database.CircleDataManager;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.DiscoveryActivity.DiscoveryActivity;
import com.example.semon.zhihuishu.HeadmasterActivity.HeadmasterMainActivity;
import com.example.semon.zhihuishu.MessageActivity.MessageActivity;
import com.example.semon.zhihuishu.MineActivity.MineActivity;
import com.example.semon.zhihuishu.ParentActivity.ParentMainActivity;
import com.example.semon.zhihuishu.R;
import com.example.semon.zhihuishu.TeacherActivity.TeacherMainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

/**
 * 班级圈
 */
public class CircleActivity extends AppCompatActivity {
    private Context context;
    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private RecyclerView mRecyclerView;
    private FloatingActionButton addCircleBtn;
    List<CircleData> mCircleDataList = new ArrayList<>();
    private CircleDataManager mCircleDataManager;
    private ParentDataManager mParentDataManager;
    private TeacherDataManager mTeacherDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_activity);
        context = CircleActivity.this;


        initMenu();
        initData();

    }

    protected void onRestart() {
        super.onRestart();
        initData();


    }

    //点击添加事件
    View.OnClickListener addCircleOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.add_circle_view_button:
                    Log.i("TAG", "添加班级圈");
                    Intent addIntent = new Intent(CircleActivity.this, AddCircleActivity
                            .class);
                    startActivity(addIntent);
                    break;
            }
        }
    };


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
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

        addCircleBtn = findViewById(R.id.add_circle_view_button);
        addCircleBtn.bringToFront();
        addCircleBtn.setOnClickListener(addCircleOnClick);

        String classId = null;
        if (user_type.equals("parent")) {
            mParentDataManager = new ParentDataManager(this);
            mParentDataManager.openDataBase();
            classId = mParentDataManager.findChildClassIdByParentId(user_id);
            mParentDataManager.closeDataBase();

            mCircleDataManager = new CircleDataManager(this);
            mCircleDataManager.openDataBase();
            mCircleDataList = mCircleDataManager.findAllCircleByClassId(classId);
            mCircleDataManager.closeDataBase();
        } else if (user_type.equals("teacher")) {
            mTeacherDataManager = new TeacherDataManager(this);
            mTeacherDataManager.openDataBase();
            classId = mTeacherDataManager.findClassByTeacherId(user_id);
            mTeacherDataManager.closeDataBase();

            mCircleDataManager = new CircleDataManager(this);
            mCircleDataManager.openDataBase();
            mCircleDataList = mCircleDataManager.findAllCircleByClassId(classId);
            mCircleDataManager.closeDataBase();
        } else if (user_type.equals("headmaster")) {
            mHeadmasterDataManager = new HeadmasterDataManager(CircleActivity.this);
            mHeadmasterDataManager.openDataBase();
            HeadmasterData mHeadmasterData = mHeadmasterDataManager.fetchHeadmasterDataById(user_id);
            mHeadmasterDataManager.closeDataBase();

            mCircleDataManager = new CircleDataManager(this);
            mCircleDataManager.openDataBase();
            mCircleDataList = mCircleDataManager.findAllCircleBySchoolId(mHeadmasterData.getSchool_id());
            mCircleDataManager.closeDataBase();
        }



        CircleAdapter adapter = new CircleAdapter(mCircleDataList, this);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false));//列表垂直滚动
        mRecyclerView.setAdapter(adapter);

    }

    private void initMenu() {
        LinearLayout menuFirst;//学生考勤
        LinearLayout menuDiscovery;//家长管理
        LinearLayout menuCircle;//学生作品
        LinearLayout menuMessage;//学生点评
        LinearLayout menuMine;//亲子任务

        menuFirst = findViewById(R.id.menu_first);
        menuDiscovery = findViewById(R.id.menu_discovery);
        menuCircle = findViewById(R.id.menu_cicle);
        menuMessage = findViewById(R.id.menu_message);
        menuMine = findViewById(R.id.menu_mine);

        TextView menu_textview;
        menu_textview = findViewById(R.id.menu_textView3);
        menu_textview.setTextColor(Color.parseColor("#FF00CF30"));
        menu_textview.setTextSize(16);

        menuFirst.setOnClickListener(menuListener);
        menuDiscovery.setOnClickListener(menuListener);
        menuCircle.setOnClickListener(menuListener);
        menuMessage.setOnClickListener(menuListener);
        menuMine.setOnClickListener(menuListener);

    }

    View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent menuIntent = null;
            switch (view.getId()) {
                case R.id.menu_first:
                    if (user_type.equals("parent")){
                        menuIntent = new Intent(CircleActivity.this,ParentMainActivity.class);
                    }else if(user_type.equals("teacher")){
                        menuIntent = new Intent(CircleActivity.this,TeacherMainActivity.class);
                    }else if(user_type.equals("headmaster")){
                        menuIntent = new Intent(CircleActivity.this,HeadmasterMainActivity.class);
                    }
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_discovery:
                    menuIntent = new Intent(context,DiscoveryActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_cicle:
                    onRestart();
                    break;
                case R.id.menu_message:
                    menuIntent = new Intent(context,MessageActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_mine:
                    menuIntent = new Intent(context,MineActivity.class);
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
            Toast.makeText(CircleActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
