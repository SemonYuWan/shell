package com.example.semon.zhihuishu.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Database.DataManagerHelper;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.HeadmasterActivity.HeadmasterMainActivity;
import com.example.semon.zhihuishu.ParentActivity.ParentMainActivity;
import com.example.semon.zhihuishu.R;
import com.example.semon.zhihuishu.TeacherActivity.TeacherMainActivity;

public class Login extends Activity {                 //登录界面活动

    public int pwdresetFlag=0;
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private String user="parent";
    private RadioGroup userRadioGroup;
    private RadioButton parentRadio,teacherRadio,headmasterRadio;
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮
    private Button mCancleButton;                     //注销按钮
    private CheckBox mRememberCheck;

    private SharedPreferences userSession;
    private String userNameValue,passwordValue;

    private View loginView;                           //登录
    private View loginSuccessView;
    private TextView loginSuccessShow;
    private TextView mChangepwdText;
    private DataManagerHelper mDatabaseHelper = null;         //用户数据管理类
    private ParentDataManager mParentDataManager;
    private TeacherDataManager mTeacherDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;

    /**
     * Sharepreference使用的步骤
     * 1.获取sp的实例
     * Sharepreference sp=getSharepreference(name,model);
     * 2.获取编辑器
     * Editor editor=sp.edit();
     * 3.存数据
     * editor.putString(name,值)
     * 4.提交
     * editor.commit();
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //通过id找到相应的控件
        mAccount = (EditText) findViewById(R.id.login_edit_account);//用户名
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);//密码

        userRadioGroup = findViewById(R.id.user_radiogroup);
        parentRadio = findViewById(R.id.parent_radioButton);
        teacherRadio = findViewById(R.id.teacher_radioButton);
        headmasterRadio = findViewById(R.id.headmaster_radioButton);

        mRegisterButton = (Button) findViewById(R.id.login_btn_register);//注册
        mLoginButton = (Button) findViewById(R.id.login_btn_login);//登录

        mCancleButton = (Button) findViewById(R.id.login_btn_cancle);//注销
        loginView=findViewById(R.id.login_view);
        loginSuccessView=findViewById(R.id.login_success_view);
        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);

        mChangepwdText = (TextView) findViewById(R.id.login_text_change_pwd);//修改密码
        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);//记住密码

        userSession = getSharedPreferences("userSession", 0);
        String name=userSession.getString("USER_NAME", "");
        String pwd =userSession.getString("PASSWORD", "");
        String password = userSession.getString("PASSWORD", null);
        String user_type = userSession.getString("USER_TYPE", null);
        boolean choseRemember =userSession.getBoolean("mRememberCheck", false);
        boolean choseAutoLogin =userSession.getBoolean("mAutologinCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
            switch (user_type){
                case "parent":
                    parentRadio.setChecked(true);
                    break;
                case "teacher":
                    teacherRadio.setChecked(true);
                    break;
                case "headmaster":
                    headmasterRadio.setChecked(true);
                    break;
            }
        }

        userRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId){
                    case R.id.parent_radioButton:
                        user = "parent";
//                        Toast.makeText(Login.this,"parent",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.teacher_radioButton:
                        user = "teacher";
//                        Toast.makeText(Login.this,"teacher",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.headmaster_radioButton:
                        user = "headmaster";
//                        Toast.makeText(Login.this,"headmaster",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);
        mCancleButton.setOnClickListener(mListener);
        mChangepwdText.setOnClickListener(mListener);

        ImageView image = (ImageView) findViewById(R.id.logo);             //使用ImageView显示logo
        image.setImageResource(R.drawable.logo);

        if (mDatabaseHelper == null) {//建立本地数据库
            mParentDataManager = new ParentDataManager(this);
            mTeacherDataManager = new TeacherDataManager(this);
            mHeadmasterDataManager = new HeadmasterDataManager(this);

        }
    }




    OnClickListener mListener = new OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_login:                               //登录按钮

                    switch (user){
                        case "parent":
                            login("parent");
                            break;
                        case "teacher":
                            login("teacher");
                            break;
                        case "headmaster":
                            login("headmaster");
                            break;
                    }

                    break;
                case R.id.login_btn_register:                            //注册按钮
                    Intent intent_Login_to_Register;
                    switch (user){
                        case "parent":
                            intent_Login_to_Register = new Intent(Login.this,ParentRegister.class) ;    //切换Login Activity至User Activity
                            startActivity(intent_Login_to_Register);
                            break;
                        case "teacher":
                            intent_Login_to_Register = new Intent(Login.this,TeacherRegister.class) ;    //切换Login Activity至User Activity
                            startActivity(intent_Login_to_Register);
                            break;
                        case "headmaster":
                            intent_Login_to_Register = new Intent(Login.this,HeadmasterRegister.class) ;    //切换Login Activity至User Activity
                            startActivity(intent_Login_to_Register);
                            break;
                    }

//                    Intent intent_Login_to_Register = new Intent(Login.this,Register.class) ;    //切换Login Activity至User Activity
//                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.login_btn_cancle:                              //注销按钮
//                    cancel();
                    break;
                case R.id.login_text_change_pwd:                         //登录界面的修改密码按钮
                    Intent intent_Login_to_reset = new Intent(Login.this,Resetpwd.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_reset);
                    finish();
                    break;
            }
        }
    };

    public void login(String userType) {                                              //登录按钮监听事件
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = mPwd.getText().toString().trim();
            SharedPreferences.Editor editor =userSession.edit();
            int result = 0;
            String userId = null;
            Intent intent = null;
            switch (user){
                case "parent":
                    mParentDataManager.openDataBase();
                    userId = mParentDataManager.findParentByUserNameAndPwd(userName, userPwd);
                    intent = new Intent(Login.this,ParentMainActivity.class) ;    //跳转Activity
                    mParentDataManager.closeDataBase();
                    break;
                case "teacher":
                    mTeacherDataManager.openDataBase();
                    userId = mTeacherDataManager.findTeacherByUserNameAndPwd(userName, userPwd);
                    intent = new Intent(Login.this,TeacherMainActivity.class) ;    //跳转Activity
                    mTeacherDataManager.closeDataBase();
                    break;
                case "headmaster":
                    mHeadmasterDataManager.openDataBase();
                    userId = mHeadmasterDataManager.findHeadmasterByUserNameAndPwd(userName, userPwd);
                    intent = new Intent(Login.this,HeadmasterMainActivity.class) ;    //跳转Activity
                    mHeadmasterDataManager.closeDataBase();
                    break;
            }

            if(userId != null){                                             //返回1说明用户名和密码均正确
                //保存用户名和密码
                editor.putString("USER_NAME", userName);
                editor.putString("PASSWORD", userPwd);
                editor.putString("USER_ID",userId);
                editor.putString("USER_TYPE",user);


                //是否记住密码
                if(mRememberCheck.isChecked()){
                    editor.putBoolean("mRememberCheck", true);
                }else{
                    editor.putBoolean("mRememberCheck", false);
                }
                editor.commit();


                startActivity(intent);
                finish();
                Toast.makeText(this, getString(R.string.login_success),Toast.LENGTH_SHORT).show();//登录成功提示
            }else if(result==0){
                Toast.makeText(this, getString(R.string.login_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }
    }
//    public void cancel() {           //注销
//        if (isUserNameAndPwdValid()) {
//            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
//            String userPwd = mPwd.getText().toString().trim();
//            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
//            if(result==1){                                             //返回1说明用户名和密码均正确
////                Intent intent = new Intent(Login.this,User.class) ;    //切换Login Activity至User Activity
////                startActivity(intent);
//                Toast.makeText(this, getString(R.string.cancel_success),Toast.LENGTH_SHORT).show();//注销成功提示
//                mPwd.setText("");
//                mAccount.setText("");
//                mUserDataManager.deleteUserDatabyname(userName);
//            }else if(result==0){
//                Toast.makeText(this, getString(R.string.cancel_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
//            }
//        }
//
//    }

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    @Override
//    protected void onResume() {
//        if (mUserDataManager == null) {
//            mUserDataManager = new UserDataManager(this);
//            mUserDataManager.openDataBase();
//        }
//        super.onResume();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    @Override
//    protected void onPause() {
//        if (mUserDataManager != null) {
//            mUserDataManager.closeDataBase();
//            mUserDataManager = null;
//        }
//        super.onPause();
//    }



}
