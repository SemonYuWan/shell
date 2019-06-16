package com.example.semon.zhihuishu.HeadmasterActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.HeadmasterData;
import com.example.semon.zhihuishu.Constant.SchoolData;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.Database.SchoolDataManager;
import com.example.semon.zhihuishu.R;

/**
 * 园所相关展示界面
 */
public class SchoolActivity extends AppCompatActivity {


    private TextView school_name_tv,school_summary_tv,school_teacher_tv,school_awards_tv;
    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private SchoolDataManager mSchoolDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;
    private ParentDataManager mParentDataManager;
    private FloatingActionButton editSchoolAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);


        initData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {
        school_name_tv = findViewById(R.id.school_name);
        school_summary_tv = findViewById(R.id.school_summary);
        school_teacher_tv = findViewById(R.id.school_teacher);
        school_awards_tv = findViewById(R.id.school_awards);


        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        editSchoolAbout = findViewById(R.id.edit_school_about_view_button);
        editSchoolAbout.bringToFront();
        editSchoolAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","点击学校编辑按钮");
                Intent editIntent = new Intent(SchoolActivity.this,EditSchoolAboutActivity.class);
                startActivity(editIntent);
            }
        });


        String schoolId = null;
        if (user_type.equals("headmaster")){
            mHeadmasterDataManager = new HeadmasterDataManager(this);
            mHeadmasterDataManager.openDataBase();
            HeadmasterData mHeadmasterData = mHeadmasterDataManager.fetchHeadmasterDataById(user_id);
            mHeadmasterDataManager.closeDataBase();
            schoolId = mHeadmasterData.getSchool_id();
        }else if(user_type.equals("parent")){
            editSchoolAbout.setVisibility(View.GONE);
            mParentDataManager = new ParentDataManager(this);
            mParentDataManager.openDataBase();
            schoolId = mParentDataManager.findChildSchoolIdByParentId(user_id);
            mParentDataManager.closeDataBase();
        }


        mSchoolDataManager = new SchoolDataManager(this);
        mSchoolDataManager.openDataBase();
        if (schoolId != null){
            SchoolData mSchoolData = mSchoolDataManager.findSchoolBySchoolId(schoolId);
            mSchoolDataManager.closeDataBase();

            school_name_tv.setText(mSchoolData.getSchool_name());
            school_summary_tv.setText(mSchoolData.getSchool_summary());
            school_teacher_tv.setText(mSchoolData.getSchool_teacher());
            school_awards_tv.setText(mSchoolData.getSchool_awards());
        }else {
            Toast.makeText(SchoolActivity.this,"所属班级为空，请咨询管理员",Toast.LENGTH_SHORT).show();
        }


    }


}
