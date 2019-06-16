package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Circle.CircleActivity;
import com.example.semon.zhihuishu.DiscoveryActivity.DiscoveryActivity;
import com.example.semon.zhihuishu.MessageActivity.MessageActivity;
import com.example.semon.zhihuishu.MineActivity.MineActivity;
import com.example.semon.zhihuishu.Notice.NoticeActivity;
import com.example.semon.zhihuishu.R;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

public class TeacherMainActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private LinearLayout attendanceViewBtn;//学生考勤
    private LinearLayout parentManageViewBtn;//家长管理
    private LinearLayout childOpusViewBtn;//学生作品
    private LinearLayout childCommentViewBtn;//学生点评
    private LinearLayout familyTaskViewBtn;//亲子任务
    private LinearLayout teachPlanViewBtn;//教学计划
    private LinearLayout classNoticeViewBtn;//班级通知
    private LinearLayout classStarViewBtn;//班级之星

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_first);
        context = TeacherMainActivity.this;

        initMenu();

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        attendanceViewBtn = findViewById(R.id.attendance_view_btn);
        parentManageViewBtn = findViewById(R.id.parent_manage_view_btn);
        childOpusViewBtn = findViewById(R.id.child_opus_view_btn);
        childCommentViewBtn = findViewById(R.id.child_comment_view_btn);
        familyTaskViewBtn = findViewById(R.id.family_task_view_btn);
        teachPlanViewBtn = findViewById(R.id.teach_plan_view_btn);
        classNoticeViewBtn = findViewById(R.id.class_notice_view_btn);
        classStarViewBtn = findViewById(R.id.class_star_view_btn);

        attendanceViewBtn.setOnClickListener(teacherFunction);
        parentManageViewBtn.setOnClickListener(teacherFunction);
        childOpusViewBtn.setOnClickListener(teacherFunction);
        childCommentViewBtn.setOnClickListener(teacherFunction);
        familyTaskViewBtn.setOnClickListener(teacherFunction);
        teachPlanViewBtn.setOnClickListener(teacherFunction);
        classNoticeViewBtn.setOnClickListener(teacherFunction);
        classStarViewBtn.setOnClickListener(teacherFunction);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
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
    OnClickListener teacherFunction = new OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intentTeacherFunctionIntent;
            switch (view.getId()) {
                case R.id.attendance_view_btn://学生考勤
                    intentTeacherFunctionIntent = new Intent(context,ParentsManageActivity.class);
                    intentTeacherFunctionIntent.putExtra("title","学生考勤统计");
                    startActivity(intentTeacherFunctionIntent);
                    break;
                case R.id.parent_manage_view_btn://家长管理
                    intentTeacherFunctionIntent = new Intent(context,ParentsManageActivity.class);
                    intentTeacherFunctionIntent.putExtra("title","家长管理");
                    startActivity(intentTeacherFunctionIntent);
                    break;
                case R.id.child_opus_view_btn://学生作品
                    intentTeacherFunctionIntent = new Intent(context,ChildOpusActivity.class);
                    startActivity(intentTeacherFunctionIntent);
                    break;
                case R.id.child_comment_view_btn://学生点评
                    intentTeacherFunctionIntent = new Intent(context,ChildCommentActivity.class);
                    startActivity(intentTeacherFunctionIntent);
                    break;
                case R.id.family_task_view_btn://亲子任务
                    intentTeacherFunctionIntent = new Intent(context,ParentChildTaskActivity.class);
                    startActivity(intentTeacherFunctionIntent);
                    break;
                case R.id.teach_plan_view_btn://教学计划
                    intentTeacherFunctionIntent = new Intent(context,TeachPlanActivity.class);
                    startActivity(intentTeacherFunctionIntent);
                    break;
                case R.id.class_notice_view_btn://班级通知
                    intentTeacherFunctionIntent = new Intent(context,NoticeActivity.class);
                    startActivity(intentTeacherFunctionIntent);
                    break;
                case R.id.class_star_view_btn://班级之星
                    intentTeacherFunctionIntent = new Intent(context,SetClassStarActivity.class);
                    startActivity(intentTeacherFunctionIntent);
                    break;
            }
        }
    };

    //菜单栏
    View.OnClickListener menuListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent menuIntent = null;
            switch (view.getId()) {
                case R.id.menu_first:
                    onRestart();
                    break;
                case R.id.menu_discovery:
                    menuIntent = new Intent(TeacherMainActivity.this,DiscoveryActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_cicle:
                    menuIntent = new Intent(TeacherMainActivity.this,CircleActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_message:
                    menuIntent = new Intent(TeacherMainActivity.this,MessageActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_mine:
                    menuIntent = new Intent(TeacherMainActivity.this,MineActivity.class);
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
            Toast.makeText(TeacherMainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
