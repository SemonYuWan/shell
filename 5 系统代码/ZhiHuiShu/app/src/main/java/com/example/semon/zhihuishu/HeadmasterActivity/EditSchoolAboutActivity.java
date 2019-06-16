package com.example.semon.zhihuishu.HeadmasterActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.HeadmasterData;
import com.example.semon.zhihuishu.Constant.SchoolData;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.SchoolDataManager;
import com.example.semon.zhihuishu.R;

/**
 * 编辑园所风采
 */

public class EditSchoolAboutActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private MultiAutoCompleteTextView editSchoolAboutSummary;
    private MultiAutoCompleteTextView editSchoolAboutTeacher;
    private MultiAutoCompleteTextView editSchoolAboutAwards;
    private Button editSchoolAboutBtn;
    private SchoolDataManager mSchoolDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_school_about);

        if (mSchoolDataManager == null){
            mSchoolDataManager = new SchoolDataManager(this);
        }

        initData();



    }

    private void initData() {


        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        editSchoolAboutSummary = findViewById(R.id.edit_school_about_summary);
        editSchoolAboutTeacher = findViewById(R.id.edit_school_about_teacher);
        editSchoolAboutAwards = findViewById(R.id.edit_school_about_awards);
        editSchoolAboutBtn = findViewById(R.id.edit_school_about_btn);
        editSchoolAboutBtn.bringToFront();
        editSchoolAboutBtn.setOnClickListener(editSchoolAboutListener);

        SchoolData mSchoolData = new SchoolData();
        mHeadmasterDataManager = new HeadmasterDataManager(EditSchoolAboutActivity.this);
        mHeadmasterDataManager.openDataBase();
        HeadmasterData mHeadmasterData = mHeadmasterDataManager.fetchHeadmasterDataById(user_id);
        mHeadmasterDataManager.closeDataBase();
        mSchoolDataManager = new SchoolDataManager(this);
        mSchoolDataManager.openDataBase();
        mSchoolData = mSchoolDataManager.findSchoolBySchoolId(mHeadmasterData.getSchool_id());
        mSchoolDataManager.closeDataBase();

        editSchoolAboutSummary.setText(mSchoolData.getSchool_summary());
        editSchoolAboutTeacher.setText(mSchoolData.getSchool_teacher());
        editSchoolAboutAwards.setText(mSchoolData.getSchool_awards());

    }

    View.OnClickListener editSchoolAboutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.edit_school_about_btn:
                    SchoolData mSchoolData = new SchoolData();
                    mHeadmasterDataManager = new HeadmasterDataManager(EditSchoolAboutActivity
                            .this);
                    mHeadmasterDataManager.openDataBase();
                    HeadmasterData mHeadmasterData = mHeadmasterDataManager
                            .fetchHeadmasterDataById(user_id);
                    mHeadmasterDataManager.closeDataBase();
                    mSchoolDataManager = new SchoolDataManager(EditSchoolAboutActivity.this);
                    mSchoolDataManager.openDataBase();
                    mSchoolData = mSchoolDataManager.findSchoolBySchoolId(mHeadmasterData
                            .getSchool_id());

                    mSchoolData.setSchool_summary(editSchoolAboutSummary.getText().toString());
                    mSchoolData.setSchool_teacher(editSchoolAboutTeacher.getText().toString());
                    mSchoolData.setSchool_awards(editSchoolAboutAwards.getText().toString());

                    if (mSchoolDataManager.updateSchoolData(mSchoolData) > 0) {
                        Log.i("editSchoolAbout", "编辑学校风采成功");
                        Toast.makeText(EditSchoolAboutActivity.this, "编辑学校风采成功！", Toast
                                .LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.i("editSchoolAbout", "编辑学校风采失败");
                        Toast.makeText(EditSchoolAboutActivity.this, "编辑学校风采失败！", Toast
                                .LENGTH_SHORT).show();
                    }
                    mSchoolDataManager.closeDataBase();

                    break;
            }
        }

    };
}
