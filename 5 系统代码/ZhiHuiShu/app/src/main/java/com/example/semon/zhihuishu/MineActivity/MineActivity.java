package com.example.semon.zhihuishu.MineActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Circle.CircleActivity;
import com.example.semon.zhihuishu.Constant.ChildData;
import com.example.semon.zhihuishu.Constant.HeadmasterData;
import com.example.semon.zhihuishu.Constant.ParentData;
import com.example.semon.zhihuishu.Constant.TeacherData;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.DiscoveryActivity.DiscoveryActivity;
import com.example.semon.zhihuishu.HeadmasterActivity.HeadmasterMainActivity;
import com.example.semon.zhihuishu.Login.HeadmasterRegister;
import com.example.semon.zhihuishu.Login.ParentRegister;
import com.example.semon.zhihuishu.Login.TeacherRegister;
import com.example.semon.zhihuishu.MessageActivity.MessageActivity;
import com.example.semon.zhihuishu.ParentActivity.ParentMainActivity;
import com.example.semon.zhihuishu.R;
import com.example.semon.zhihuishu.SignDate.SignDateMainActivity;
import com.example.semon.zhihuishu.TeacherActivity.TeacherMainActivity;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

public class MineActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private ConstraintLayout sign_data_view_btn,user_information_view_btn,about_us_view_btn;
    private TextView mine_name_textview,mine_information_textview;
    private ParentDataManager mParentDataManager;
    private TeacherDataManager mTeacherDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        context = MineActivity.this;



        initData();
        initMenu();
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

        user_information_view_btn = findViewById(R.id.user_information_view_btn);
        about_us_view_btn = findViewById(R.id.about_us_view_btn);
        sign_data_view_btn = findViewById(R.id.sign_data_view_btn);

        user_information_view_btn.setOnClickListener(functionListener);
        about_us_view_btn.setOnClickListener(functionListener);
        sign_data_view_btn.setOnClickListener(functionListener);


        if(!user_type.equals("parent")){
            sign_data_view_btn.setVisibility(View.GONE);
        }

        mine_name_textview = findViewById(R.id.mine_name);
        mine_information_textview = findViewById(R.id.mine_information);

        if (user_type.equals("parent")) {
            mParentDataManager = new ParentDataManager(this);
            mParentDataManager.openDataBase();
            ParentData mParentData = mParentDataManager.fetchParentDataById(user_id);
            mine_name_textview.setText(mParentData.getName());
            ChildData mChildData = mParentDataManager.findChildDataByParentId(user_id);
            mine_information_textview.setText(mChildData.getName()+" 家长");
            mParentDataManager.closeDataBase();
        } else if (user_type.equals("teacher")) {
            mTeacherDataManager = new TeacherDataManager(this);
            mTeacherDataManager.openDataBase();
            TeacherData mTeacherData = mTeacherDataManager.fetchTeacherDataById(user_id);
            mine_name_textview.setText(mTeacherData.getName());
            mine_information_textview.setText(mTeacherDataManager.findClassNameByTeacherId(user_id));
            mTeacherDataManager.closeDataBase();
        } else if (user_type.equals("headmaster")) {
            mHeadmasterDataManager = new HeadmasterDataManager(this);
            mHeadmasterDataManager.openDataBase();
            HeadmasterData mHeadmasterData = mHeadmasterDataManager.fetchHeadmasterDataById(user_id);
            mine_name_textview.setText(mHeadmasterData.getName());
            mine_information_textview.setText(mHeadmasterDataManager.fetchHeadmasterNameById(user_id));
            mHeadmasterDataManager.closeDataBase();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    View.OnClickListener functionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent functionIntent = null;
            switch (view.getId()) {
                case R.id.sign_data_view_btn:
                    functionIntent = new Intent(context,SignDateMainActivity.class);
                    startActivity(functionIntent);
                    break;
                case R.id.user_information_view_btn:
                    if (user_type.equals("parent")) {
                        functionIntent = new Intent(MineActivity.this, ParentRegister.class);
                        functionIntent.putExtra("parentId", user_id);
                    } else if (user_type.equals("teacher")) {
                        functionIntent = new Intent(MineActivity.this, TeacherRegister.class);
                        functionIntent.putExtra("teacherId", user_id);
                    } else if (user_type.equals("headmaster")) {
                        functionIntent = new Intent(MineActivity.this, HeadmasterRegister.class);
                        functionIntent.putExtra("headmasterId", user_id);
                    }
                    startActivity(functionIntent);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.about_us_view_btn:
                    functionIntent = new Intent(context,AboutUsActivity.class);
                    startActivity(functionIntent);
                    overridePendingTransition(0, 0);
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

        menuFirst = findViewById(R.id.menu_first);
        menuDiscovery = findViewById(R.id.menu_discovery);
        menuCircle = findViewById(R.id.menu_cicle);
        menuMessage = findViewById(R.id.menu_message);
        menuMine = findViewById(R.id.menu_mine);

        TextView menu_textview;
        menu_textview = findViewById(R.id.menu_textView5);
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
                        menuIntent = new Intent(MineActivity.this,ParentMainActivity.class);
                    }else if(user_type.equals("teacher")){
                        menuIntent = new Intent(MineActivity.this,TeacherMainActivity.class);
                    }else if(user_type.equals("headmaster")){
                        menuIntent = new Intent(MineActivity.this,HeadmasterMainActivity.class);
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
                    onRestart();
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
            Toast.makeText(MineActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
