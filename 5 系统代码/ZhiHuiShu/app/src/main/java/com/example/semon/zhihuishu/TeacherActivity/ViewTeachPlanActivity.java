package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.TeachPlanData;
import com.example.semon.zhihuishu.Database.TeachPlanDataManager;
import com.example.semon.zhihuishu.R;

public class ViewTeachPlanActivity extends AppCompatActivity {

    private TeachPlanDataManager mTeachPlanDataManager;
    private TextView planTitleView;//计划的标题
    private TextView planContextView;//计划的内容
    private TextView schoolView;//所属学校
    private TextView classView;//所属班级
    private TextView createrView;//创建者
    private TextView createTimeView;//创建时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teach_plan);

        initData();

    }

    private void initData() {
        planTitleView = findViewById(R.id.teachPlan_title_view);
        planContextView = findViewById(R.id.teachPlan_context_view);
        createTimeView = findViewById(R.id.teachPlan_createTime_view);

        mTeachPlanDataManager = new TeachPlanDataManager(this);
        mTeachPlanDataManager.openDataBase();
        Intent itemTeachPlanIntent = getIntent();
        String itemTeachPlanId = itemTeachPlanIntent.getStringExtra("teachPlanId");
        TeachPlanData itemTeachPlan = new TeachPlanData();
        itemTeachPlan = mTeachPlanDataManager.findTeachPlanById(itemTeachPlanId);
        planTitleView.setText(itemTeachPlan.getPlanTitle());
        planContextView.setText(itemTeachPlan.getPlanContext());
        createTimeView.setText(itemTeachPlan.getCreateTime());
    }
}
