package com.example.semon.zhihuishu.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.TeacherData;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.R;

import java.util.UUID;

public class TeacherRegister extends AppCompatActivity {

    private TeacherDataManager mTeacherDataManager;         //用户数据管理类
    private String id;
    private EditText userEdit;
    private EditText passwordEdit;
    private EditText passwordEditCheck;
    private EditText nameEdit;
    private EditText telEdit;
    private EditText schoolEdit;
    private EditText classEdit;
    private Button registerTeacherBtn,registerCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        String itemTeacherId = null;
        Intent itemTeacherIntent = getIntent();
        itemTeacherId = itemTeacherIntent.getStringExtra("teacherId");

        userEdit = findViewById(R.id.teacher_register_edit_user);
        passwordEdit = findViewById(R.id.teacher_register_edit_pwd);
        passwordEditCheck = findViewById(R.id.teacher_register_edit_pwdcheck);
        nameEdit = findViewById(R.id.teacher_register_edit_name);
        telEdit = findViewById(R.id.teacher_register_edit_tel);
        schoolEdit = findViewById(R.id.teacher_register_edit_school);
        classEdit = findViewById(R.id.teacher_register_edit_class);

        registerTeacherBtn = findViewById(R.id.register_btn_sure_teacher);
        registerCancelBtn = findViewById(R.id.register_btn_cancel);

        registerTeacherBtn.setOnClickListener(m_register_Listener);
        registerCancelBtn.setOnClickListener(m_register_Listener);

        if (mTeacherDataManager == null) {
            mTeacherDataManager = new TeacherDataManager(this);
            mTeacherDataManager.openDataBase();                              //建立本地数据库
        }

        if (itemTeacherId != null){
            mTeacherDataManager.openDataBase();
            TeacherData mTeacherData = mTeacherDataManager.fetchTeacherDataById(itemTeacherId);
            userEdit.setText(mTeacherData.getUser());
            nameEdit.setText(mTeacherData.getName());
            telEdit.setText(mTeacherData.getTel());
            schoolEdit.setText(mTeacherDataManager.findSchoolNameByTeacherId(itemTeacherId));
            classEdit.setText(mTeacherDataManager.findClassNameByTeacherId(itemTeacherId));
            registerTeacherBtn.setText("修改");
            registerCancelBtn.setVisibility(View.GONE);
        }
    }

    //按钮点击监听事件
    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_sure_teacher:                       //确认按钮的监听事件
                    register_check();
                    break;
                case R.id.register_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Register_to_Login = new Intent(TeacherRegister.this,Login.class) ;    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
            }
        }
    };
    public void register_check() {                                //确认按钮的监听事件
        if (isUserNameAndPwdValid()) {
            String user = userEdit.getText().toString().trim();
            String userPwd = passwordEdit.getText().toString().trim();
            String userPwdCheck = passwordEditCheck.getText().toString().trim();
            String name = nameEdit.getText().toString().trim();
            String tel = telEdit.getText().toString().trim();
            String schoolName = schoolEdit.getText().toString().trim();
            String className = classEdit.getText().toString().trim();


            //检查用户是否存在
            int count=mTeacherDataManager.findTeacherByName(user);
            //用户已经存在时返回，给出提示文字
            if(count>0){
                Toast.makeText(this, getString(R.string.name_already_exist),Toast.LENGTH_SHORT).show();
                return ;
            }
            if(userPwd.equals(userPwdCheck)==false){     //两次密码输入不一样
                Toast.makeText(this, getString(R.string.pwd_not_the_same),Toast.LENGTH_SHORT).show();
                return ;
            } else {
                id = UUID.randomUUID().toString();
                TeacherData mTeacher = new TeacherData(id,user,userPwd,name,tel,schoolName,className);
                mTeacherDataManager.openDataBase();
                long flag = mTeacherDataManager.insertTeacherData(mTeacher); //新建用户信息
                if (flag == -1) {
                    Toast.makeText(this, getString(R.string.register_fail),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, getString(R.string.register_success),Toast.LENGTH_SHORT).show();
                    Intent intent_Register_to_Login = new Intent(TeacherRegister.this,Login.class) ;    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                }
            }
        }
    }
    public boolean isUserNameAndPwdValid() {
        if (userEdit.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordEdit.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(passwordEditCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
