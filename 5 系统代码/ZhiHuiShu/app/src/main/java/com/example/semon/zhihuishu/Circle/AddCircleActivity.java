package com.example.semon.zhihuishu.Circle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.CircleData;
import com.example.semon.zhihuishu.Constant.ParentData;
import com.example.semon.zhihuishu.Constant.TeacherData;
import com.example.semon.zhihuishu.Database.CircleDataManager;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 添加班级圈
 */

public class AddCircleActivity extends AppCompatActivity {

    private TextView newCircleTitle;
    private MultiAutoCompleteTextView newCircleContext;
    private Button addCircleBtn;
    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private CircleDataManager mCircleDataManager;
    private ParentDataManager mParentDataManager;
    private TeacherDataManager mTeacherDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);

        newCircleContext = findViewById(R.id.add_circle_context);
        addCircleBtn = findViewById(R.id.add_circle_btn);
        addCircleBtn.setOnClickListener(addCircleListener);

        if (mCircleDataManager == null){
            mCircleDataManager = new CircleDataManager(this);
        }

    }

    View.OnClickListener addCircleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
            user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
            user_name = userSession.getString("USER_NAME", null);
            password = userSession.getString("PASSWORD", null);
            user_type = userSession.getString("USER_TYPE", null);

            String classId = null;
            String creater = null;
            if (user_type.equals("parent")) {
                mParentDataManager = new ParentDataManager(AddCircleActivity.this);
                mParentDataManager.openDataBase();
                ParentData mParentData = mParentDataManager.findParentDataById(user_id);
                creater = mParentData.getName();
                classId = mParentDataManager.findChildClassIdByParentId(user_id);
                mParentDataManager.closeDataBase();
            } else if (user_type.equals("teacher")) {
                mTeacherDataManager = new TeacherDataManager(AddCircleActivity.this);
                mTeacherDataManager.openDataBase();
                TeacherData mTeacherData = mTeacherDataManager.fetchTeacherDataById(user_id);
                creater = mTeacherData.getName();
                classId = mTeacherDataManager.findClassByTeacherId(user_id);
                mTeacherDataManager.closeDataBase();
            } else if (user_type.equals("headmaster")) {
                mHeadmasterDataManager = new HeadmasterDataManager(AddCircleActivity.this);
                mHeadmasterDataManager.openDataBase();
                //            classId = mHeadmasterDataManager
                mHeadmasterDataManager.closeDataBase();
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            CircleData mCircleData = new CircleData();
            mCircleData.setId(UUID.randomUUID().toString());
            mCircleData.setContent(newCircleContext.getText().toString().trim());
            mCircleData.setCreateTime(simpleDateFormat.format(date));
            mCircleData.setCreater_id(user_id);
            mCircleData.setClass_id(classId);
            mCircleData.setCreater(creater);

            mCircleDataManager.openDataBase();
            if(mCircleDataManager.addCircle(mCircleData) > 0){
                Log.i("addCircle","发布班级圈成功");
                Toast.makeText(AddCircleActivity.this,"发布班级圈成功！",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };
}
