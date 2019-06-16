package com.example.semon.zhihuishu.ParentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Circle.CircleActivity;
import com.example.semon.zhihuishu.Constant.ChildData;
import com.example.semon.zhihuishu.Constant.NoticeBean;
import com.example.semon.zhihuishu.Database.ChildDataManager;
import com.example.semon.zhihuishu.Database.NoticeDataManager;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.DiscoveryActivity.DiscoveryActivity;
import com.example.semon.zhihuishu.HeadmasterActivity.SchoolActivity;
import com.example.semon.zhihuishu.MessageActivity.MessageActivity;
import com.example.semon.zhihuishu.MineActivity.MineActivity;
import com.example.semon.zhihuishu.Notice.ViewNoticeActivity;
import com.example.semon.zhihuishu.R;
import com.example.semon.zhihuishu.RecipeActivity.RecipeActivity;
import com.example.semon.zhihuishu.TeacherActivity.ChildCommentActivity;
import com.example.semon.zhihuishu.TeacherActivity.ChildOpusActivity;
import com.example.semon.zhihuishu.TeacherActivity.ParentChildTaskActivity;
import com.example.semon.zhihuishu.TeacherActivity.TeachPlanActivity;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

public class ParentMainActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private Context context;
    private TextView class_textView,daystar_textView;
    private ListView noticeListView;
    private List<NoticeBean> noticeBeans;
    private NoticeDataManager mNoticeDataManager;
    private ParentDataManager mParentDataManager;
    private ChildDataManager mChildDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_first);
        context = ParentMainActivity.this;

        initMenu();
        initParentFunction();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        class_textView = findViewById(R.id.class_textView);
        daystar_textView = findViewById(R.id.daystar_textView);

        mNoticeDataManager = new NoticeDataManager(this);
        mNoticeDataManager.openDataBase();
        noticeBeans = mNoticeDataManager.findAllNotice();
        noticeListView = findViewById(R.id.notice_listview_parent);
        MyBaseAdapter mAdapter = new MyBaseAdapter(noticeBeans);
        noticeListView.setAdapter(mAdapter);
        noticeListView.setOnItemClickListener(itemOnClick);
        mNoticeDataManager.closeDataBase();

        mParentDataManager = new ParentDataManager(this);
        mParentDataManager.openDataBase();
        class_textView.setText(mParentDataManager.findChildClassNameByParentId(user_id));

        ChildData mChildData = mParentDataManager.findChildDataByParentId(user_id);
        daystar_textView.setText(mChildData.getName());
        mParentDataManager.closeDataBase();

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    //item点击事件
    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            NoticeBean mNoticeBean = (NoticeBean) adapterView.getItemAtPosition(position);
            Log.i("ItemClick", "点击了" + mNoticeBean.getId());
            Intent itemNoticeIntent = new Intent(ParentMainActivity.this, ViewNoticeActivity.class);
            itemNoticeIntent.putExtra("noticeId", mNoticeBean.getId());
            startActivity(itemNoticeIntent);
        }
    };

    private class MyBaseAdapter extends BaseAdapter {
        private List<NoticeBean> noticeBeans;
        public MyBaseAdapter(List<NoticeBean> noticeBeans) {
            this.noticeBeans=noticeBeans;
        }

        @Override
        public int getCount() {
            return noticeBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return noticeBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(ParentMainActivity.this,R.layout.item_notice,null);
            TextView noticeTitle = view.findViewById(R.id.notice_title_textview);
            ImageView noticeIcon = view.findViewById(R.id.notice_type_icon);
            noticeTitle.setText(noticeBeans.get(position).getTitle());
            return view;
        }
    }

    private void initParentFunction() {
        LinearLayout recipeViewBtn;//学生考勤
        LinearLayout childOpusViewBtn;//学生作品
        LinearLayout childCommentViewBtn;//学生点评
        LinearLayout familyTaskViewBtn;//亲子任务
        LinearLayout teachPlanViewBtn;//教学计划
        LinearLayout SchoolViewBtn;//园所相关

        recipeViewBtn = findViewById(R.id.recipe_view_btn);
        childOpusViewBtn = findViewById(R.id.child_opus_view_btn);
        childCommentViewBtn = findViewById(R.id.child_comment_view_btn);
        familyTaskViewBtn = findViewById(R.id.family_task_view_btn);
        teachPlanViewBtn = findViewById(R.id.teach_plan_view_btn);
        SchoolViewBtn = findViewById(R.id.school_about_view_btn);

        recipeViewBtn.setOnClickListener(teacherFunction);
        childOpusViewBtn.setOnClickListener(teacherFunction);
        childCommentViewBtn.setOnClickListener(teacherFunction);
        familyTaskViewBtn.setOnClickListener(teacherFunction);
        teachPlanViewBtn.setOnClickListener(teacherFunction);
        SchoolViewBtn.setOnClickListener(teacherFunction);
    }

    //监听事件
    View.OnClickListener teacherFunction = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intentTeacherFunctionIntent;
            switch (view.getId()) {
                case R.id.recipe_view_btn://每日食谱
                    intentTeacherFunctionIntent = new Intent(context,RecipeActivity.class);
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
                case R.id.school_about_view_btn://园所相关
                    intentTeacherFunctionIntent = new Intent(context,SchoolActivity.class);
                    startActivity(intentTeacherFunctionIntent);
                    break;
            }
        }
    };

    private void initMenu() {
        LinearLayout menuFirst;//学生考勤
        LinearLayout menuDiscovery;//家长管理
        LinearLayout menuCircle;//学生作品
        LinearLayout menuMessage;//学生点评
        LinearLayout menuMine;//亲子任务

        TextView menu_textview;

        menuFirst = findViewById(R.id.menu_first);
        menuDiscovery = findViewById(R.id.menu_discovery);
        menuCircle = findViewById(R.id.menu_cicle);
        menuMessage = findViewById(R.id.menu_message);
        menuMine = findViewById(R.id.menu_mine);

        menu_textview = findViewById(R.id.menu_textview1);
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
            Intent menuIntent;
            switch (view.getId()) {
                case R.id.menu_first:
                    onRestart();
                    break;
                case R.id.menu_discovery:
                    menuIntent = new Intent(context,DiscoveryActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_cicle:
                    menuIntent = new Intent(context,CircleActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
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
            Toast.makeText(ParentMainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
