package com.example.semon.zhihuishu.TeacherActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.TeachPlanData;
import com.example.semon.zhihuishu.Database.TeachPlanDataManager;
import com.example.semon.zhihuishu.R;

import java.util.UUID;

/**
 * 添加教学计划
 */

public class AddTeachPlanActivity extends AppCompatActivity {

    private TeachPlanDataManager mTeachPlanDataManager;
    private EditText teachPlanTitleEditText;
    private EditText teachPlanContextEditText;
    private EditText teachPlanRemarkEditText;
    private Button addTeachPlanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teach_plan);

        initData();
    }

    private void initData() {
        teachPlanTitleEditText = findViewById(R.id.teach_plan_title_editText);
        teachPlanContextEditText = findViewById(R.id.teach_plan_context_editText);
        teachPlanRemarkEditText = findViewById(R.id.teach_plan_remark_editText);
        addTeachPlanBtn = findViewById(R.id.add_teach_plan_button);

        if (mTeachPlanDataManager == null) {
            mTeachPlanDataManager = new TeachPlanDataManager(this);
            mTeachPlanDataManager.openDataBase();                              //建立本地数据库
        }

        addTeachPlanBtn.setOnClickListener(addTeachPlan_Listener);

    }

    View.OnClickListener addTeachPlan_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_teach_plan_button:
                    TeachPlanData mTeachPlan = new TeachPlanData();
                    mTeachPlan.setId(UUID.randomUUID().toString());
                    mTeachPlan.setPlanTitle(teachPlanTitleEditText.getText().toString().trim());
                    mTeachPlan.setPlanContext(teachPlanContextEditText.getText().toString().trim());
                    mTeachPlan.setSchoolId("1");
                    mTeachPlan.setClassId("1");
                    mTeachPlan.setCreater("1");
//                    mTeachPlan.setCreateTime();
                    mTeachPlan.setRemark(teachPlanRemarkEditText.getText().toString().trim());
                    if (mTeachPlanDataManager.addTeachPlan(mTeachPlan)>0){
                        Toast.makeText(AddTeachPlanActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;

            }

        }
    };
}
