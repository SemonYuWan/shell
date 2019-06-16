package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.TeacherData;

/**
 * 教师操作相关数据库操作
 */
public class TeacherDataManager {
    //一些宏定义和声明
    private static final String TAG = "TeacherDataManager";
    public static final String TABLE_NAME_TEACHER = "teacher";
    public static final String ID = "_id";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String TEL = "tel";
    public static final String SCHOOL_ID = "school_id";
    public static final String CLASS_ID = "class_id";
    public static final String REMARK = "remark";

    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;


    public TeacherDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "TeacherDataManager construction!");
    }

    //打开数据库
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new DataManagerHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    //关闭数据库
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }

    //添加新用户，即注册
    public long insertTeacherData(TeacherData teacherData) {
        ContentValues values = new ContentValues();
        values.put(ID, teacherData.getId());
        values.put(USER, teacherData.getUser());
        values.put(PASSWORD, teacherData.getPassword());
        values.put(NAME, teacherData.getName());
        values.put(TEL, teacherData.getTel());
        values.put(SCHOOL_ID, teacherData.getSchool_id());
        values.put(CLASS_ID, teacherData.getClass_id());
        values.put(REMARK, teacherData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_TEACHER, ID, values);
    }

    //更新修改教师信息
    public boolean updateTeacherData(TeacherData teacherData) {
        ContentValues values = new ContentValues();
        values.put(ID, teacherData.getId());
        values.put(USER, teacherData.getUser());
        values.put(PASSWORD, teacherData.getPassword());
        values.put(NAME, teacherData.getName());
        values.put(TEL, teacherData.getTel());
        values.put(SCHOOL_ID, teacherData.getSchool_id());
        values.put(CLASS_ID, teacherData.getClass_id());
        values.put(REMARK, teacherData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_TEACHER, values, null, null) > 0;
    }

    //根据ID查找教师信息
    public TeacherData fetchTeacherDataById(String teacherId) throws SQLException {
        TeacherData mTeacherData = new TeacherData();
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_TEACHER, null, ID
                + "=?", new String[]{teacherId}, null, null, null, null);
        if (mCursor.getCount() == 1) {
            mCursor.moveToNext();
            mTeacherData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mTeacherData.setUser(mCursor.getString(mCursor.getColumnIndex(USER)));
            mTeacherData.setName(mCursor.getString(mCursor.getColumnIndex(NAME)));
            mTeacherData.setTel(mCursor.getString(mCursor.getColumnIndex(TEL)));
            mTeacherData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mTeacherData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mTeacherData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mTeacherData;
    }

    //根据教师姓名查找教师信息
    public Cursor fetchTeacherData(String name) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_TEACHER, null, NAME
                + "=" + name, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //根据id删除用户
    public boolean deleteTeacherData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME_TEACHER, ID + "=" + id, null) > 0;
    }

    //根据用户名注销
    public boolean deleteTeacherDatabyname(String name) {
        return mSQLiteDatabase.delete(TABLE_NAME_TEACHER, NAME + "=" + name, null) > 0;
    }

    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findTeacherByName(String userName) {
        Log.i(TAG, "findUserByName , userName=" + userName);
        int result = 0;
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_TEACHER, null, NAME + "=" + userName,
                null, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
            Log.i(TAG, "findUserByName , result=" + result);
        }
        return result;
    }

    //根据用户名和密码找用户，用于登录
    public String findTeacherByUserNameAndPwd(String userName, String pwd) {
        Log.i(TAG, "findUserByNameAndPwd");
        int result = 0;
        String userId = null;
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_USER+"
        // where ?=? and ?=?",new String[]{USER_NAME,userName,USER_PWD,pwd});
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_TEACHER, null, USER + "= '" + userName
                        + "' and " + PASSWORD + "='" + pwd + "'",
                null, null, null, null);
        if (mCursor != null) {

            result = mCursor.getCount();
            if (result == 1) {
                mCursor.moveToNext();
                userId = mCursor.getString(mCursor.getColumnIndex(TeachPlanDataManager.ID));

            }
            mCursor.close();
            Log.i(TAG, "findUserByNameAndPwd , result=" + result);
        }
        return userId;
    }

    //根据教师ID查所在班级id
    public String findClassByTeacherId(String teacherId){
        Log.i(TAG, "findClassByTeacherId");
        String classId = null;
        String findClassByTeacherIdSql = "SELECT * FROM "+TABLE_NAME_TEACHER+" WHERE "+ID+" =?";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findClassByTeacherIdSql,new String[]{teacherId});
        if (mCursor.getCount() == 1){
            mCursor.moveToNext();
            classId = mCursor.getString(mCursor.getColumnIndex(TeacherDataManager.CLASS_ID));
        }
        return classId;
    }

    //根据教师ID查所在班级名称
    public String findClassNameByTeacherId(String teacherId){
        Log.i(TAG, "findClassByTeacherId");
        String className = null;
        String findClassByTeacherIdSql = "SELECT c." + ClassDataManager.CLASS_NAME + " className " +
                "FROM " + TABLE_NAME_TEACHER + " t," + ClassDataManager.TABLE_NAME_CLASS + " c " +
                "where t."+ID+"=? and t." + CLASS_ID + " = c." + ClassDataManager.ID + ";";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findClassByTeacherIdSql,new String[]{teacherId});
        if (mCursor.getCount() == 1){
            mCursor.moveToNext();
            className = mCursor.getString(mCursor.getColumnIndex("className"));
        }
        return className;
    }

    //根据教师ID查所在学校名称
    public String findSchoolNameByTeacherId(String teacherId){
        Log.i(TAG, "findSchoolNameByTeacherId");
        String schoolName = null;
        String findClassByTeacherIdSql = "SELECT s." + SchoolDataManager.SCHOOL_NAME + " schoolName " +
                "FROM " + TABLE_NAME_TEACHER + " t," + ClassDataManager.TABLE_NAME_CLASS + " c, " +SchoolDataManager.TABLE_NAME_SCHOOL +
                " s where t."+ID+"=? and t." + CLASS_ID + " = c." + ClassDataManager.ID + " and c."+ClassDataManager.SCHOOL_ID+" = s."+SchoolDataManager.ID+";";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findClassByTeacherIdSql,new String[]{teacherId});
        if (mCursor.getCount() == 1){
            mCursor.moveToNext();
            schoolName = mCursor.getString(mCursor.getColumnIndex("schoolName"));
        }
        return schoolName;
    }


}
